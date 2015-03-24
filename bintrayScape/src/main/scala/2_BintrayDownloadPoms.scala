
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

object _2_BintrayDownloadPoms {
  def main(args: Array[String]): Unit = {
    val scalaVersion = Scala.version
    val config = ConfigFactory.load()
    implicit val system = ActorSystem("scalakata-playground", config)
    import system.dispatcher // execution context for futures

    implicit val timeout = Timeout(6000 seconds)

    val pipeline: HttpRequest => Future[HttpResponse] = sendReceive

    val artifacts = for {
      file <- new java.io.File(s"res/scala_${scalaVersion}").listFiles
      content = scala.io.Source.fromFile(file).mkString
      JsArray(repos) = Json.parse(content)
      r <- repos if r != JsNull
    } yield ((r \ "repo").as[String], (r \ "owner").as[String], (r \ "path").as[String], (r \ "created").as[String])

    val reqs = artifacts.map{ case info @ (repo, owner, path, _) =>
      val url =
        if(repo == "jcenter" && owner == "bintray")
          Uri(s"http://jcenter.bintray.com/$path")
        else
          Uri(s"http://dl.bintray.com/$owner/$repo/$path")
      pipeline(Get(url)).zip{Future(info)}
    }.toList

    val bunch = 50
    val fgroups = reqs.sliding(bunch, bunch).map(v => Future.sequence(v))

    val printer = new scala.xml.PrettyPrinter(80, 2)

    fgroups.foreach { g =>
    	Await.result(g, timeout.duration).foreach {
        case (HttpResponse(StatusCodes.OK, entity: HttpEntity.NonEmpty, headers,_), (_, _, path, created)) =>
        	val xml = scala.xml.XML.loadString(entity.data.asString)
          val mpath = path.dropRight(".pom".length) + "_" + created + ".pom"
          val path2 = s"res/poms_${scalaVersion}/$mpath"

          val file = new java.io.File(path2)
          if(!file.exists) file.getParentFile.mkdirs()

          println(path2)

          val pw = new java.io.PrintWriter(path2)
          pw.println(printer.format(xml))
          pw.close

        case e =>
          println(e)
      }
    }
    system.shutdown
  }
}
