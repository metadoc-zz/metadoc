import me.tongfei.progressbar._
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

    // val artifacts = for {
    //   file <- new java.io.File(s"res/scala_${scalaVersion}").listFiles
    //   content = scala.io.Source.fromFile(file).mkString
    //   JsArray(repos) = Json.parse(content)
    //   r <- repos if r != JsNull
    // } yield (
    //   (r \ "repo").as[String],
    //   (r \ "owner").as[String],
    //   (r \ "path").as[String].replace(" ", "%20"),
    //   (r \ "created").as[String]
    // )

    import java.nio.file.Paths
    val nl = System.lineSeparator
    val bintrayCheckpointFile = Paths.get("/home/gui/ScalaDuck/index/bintray/bintray.json").toFile
    val searchFile = scala.io.Source.fromFile(bintrayCheckpointFile)
    val search = searchFile.mkString.split(nl).toList.map(Json.parse)
    searchFile.close()

    val artifacts = search.map{ s =>
      (
        (s \ "repo").as[String],
        (s \ "owner").as[String],
        (s \ "path").as[String].replace(" ", "%20"),
        (s \ "sha1").as[String],
        (s \ "created").as[String]
      )      
    }

    val progress = new ProgressBar("Download POMs", 0)   
    progress.start()
    progress.maxHint(artifacts.size)
    

    val reqs = artifacts.map{ case info @ (repo, owner, path, sha1, _) =>
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
        case (HttpResponse(StatusCodes.OK, entity: HttpEntity.NonEmpty, headers,_), (_, _, _, sha1, _)) =>

          progress.step()

        	val xml = scala.xml.XML.loadString(entity.data.asString)
          // val mpath = path.dropRight(".pom".length) + "_" + created + ".pom"
          // val path2 = s"res/poms_${scalaVersion}/$mpath"

          // val file = new java.io.File(path2)
          // if(!file.exists) file.getParentFile.mkdirs()

          // println(path2)

          val pw = new java.io.PrintWriter(Paths.get(s"/home/gui/ScalaDuck/index/bintray/poms/$sha1.pom").toFile)
          pw.println(printer.format(xml))
          pw.close

        case e =>
          println(e)
      }
    }
    system.shutdown
  }
}
