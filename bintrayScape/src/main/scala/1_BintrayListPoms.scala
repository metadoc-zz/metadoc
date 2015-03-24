import scala.concurrent.{Future, Await}

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.io.IO
import akka.util.Timeout
import scala.concurrent.duration._

import spray.http._
import spray.client.pipelining._
import spray.can.Http
import spray.can.Http.ConnectionAttemptFailedException
import spray.http.Uri
import spray.http.Uri._

import com.typesafe.config.{ ConfigValueFactory, ConfigFactory, Config }

import scala.language.postfixOps

import play.api.libs.json._

// https://bintray.com/docs/api/
object _1_BintrayListPoms {
  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    implicit val system = ActorSystem("scalakata-playground", config)
    import system.dispatcher // execution context for futures

    implicit val timeout = Timeout(6000 seconds)

    val pipeline: HttpRequest => Future[HttpResponse] = sendReceive

    val bintray = {
      val home = System.getProperty("user.home")
      val path = home + "/.bintray/.credentials"
      val nl = System.lineSeparator
      io.Source.fromFile(path).mkString.split(nl).map{ v =>
        val (l, r) = v.span(_ != '=' )
        (l.trim, r.drop(2).trim)
      }.toMap
    }


    def get(scalaVersion: String, startpos: Int): Unit = {
      val file = new java.io.File(s"res/scala_${scalaVersion}/results_${scalaVersion}_${startpos}.json")
      if(!file.exists) file.getParentFile.mkdirs()
      val pw = new java.io.PrintWriter(file)

      val uri = Uri("https://bintray.com/api/v1/search/file").copy(
        query = Query("name" -> s"*_$scalaVersion*.pom", "start_pos" -> startpos.toString)
      )

      val request =
        if(startpos != 0) Get(uri) ~> addCredentials(BasicHttpCredentials(bintray("user"), bintray("password")))
        else Get(uri)

      println(request)

      val response = pipeline(request)

      val Some((remaining, limit, end, start, total, json)) = Await.result(response, timeout.duration) match {
        case HttpResponse(StatusCodes.OK, entity: HttpEntity.NonEmpty, headers,_) =>
          println(headers)
          def limits(name: String): Int = {
            headers.find(_.name == name).map(_.value.toInt).getOrElse(0)
          }
          Some((
            limits("X-RateLimit-Remaining"),
            limits("X-RateLimit-Limit"),
            limits("X-RangeLimit-EndPos"),
            limits("X-RangeLimit-StartPos"),
            limits("X-RangeLimit-Total"),
            entity.data.asString
          ))
        case e =>
          println(e)
          None
      }
      //Markdown(s"""|```json
  		//				 |${Json.prettyPrint(Json.parse(json))}
  		//				 |```""".stripMargin)
      pw.println(Json.prettyPrint(Json.parse(json)))
      pw.close()

      println(remaining)
      println(start)
      println(end)
      println(total)

      if((remaining > 0 || start == 0) && end < total) get(scalaVersion, end)
    }
    get(Scala.version,  14100)
    system.shutdown
  }
}
