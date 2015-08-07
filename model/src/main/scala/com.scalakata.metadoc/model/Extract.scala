package model

object Extract {
  import java.io.File

  import java.nio.file._
  import java.nio.charset._

  
  import org.joda.time.format.ISODateTimeFormat

  def recursiveListFiles(f: File): Array[File] = {
    val these = f.listFiles
    if(these == null) Array()
    else these.filter(_.isFile) ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
  }

  val poms =
    recursiveListFiles(new File("/home/gui/metadoc/poms")).filter(_.getName != ".git")

  val fmt = ISODateTimeFormat.dateTime

  val res =
    poms.flatMap{ p =>
        import scala.collection.JavaConverters._
        val source = Files.readAllLines(Paths.get(p.toURI), Charset.defaultCharset()).asScala.mkString(System.lineSeparator)
        
        scala.util.Try(scala.xml.XML.loadString(source)).map{ xml =>
          val filename = p.getName
          val dateTimeStr = filename.substring(filename.lastIndexOf("_") + 1, filename.length - ".pom".length)
          val dateTime = fmt.parseDateTime(dateTimeStr)
          Array(model.PomToProject.parse((xml \\ "project").head, dateTime))
        }.getOrElse{
          println(s"failed to load ${p.getName}")
          Array.empty[model.Project]
        }
      }
}