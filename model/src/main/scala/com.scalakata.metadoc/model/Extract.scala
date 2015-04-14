package model

object Extract {
  import java.io.File

  import java.nio.file._
  import java.nio.charset._

  def recursiveListFiles(f: File): Array[File] = {
    val these = f.listFiles
    if(these == null) Array()
    else these.filter(_.isFile) ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
  }

  val res =
    recursiveListFiles(new File("/home/masgui/FOOS/Doc/metadoc/poms/")).filter(_.getName != ".git").flatMap{ p =>
      import scala.collection.JavaConverters._
      val source = Files.readAllLines(Paths.get(p.toURI), Charset.defaultCharset()).asScala.mkString(System.lineSeparator)
      
      scala.util.Try(scala.xml.XML.loadString(source)).map{ xml =>
        Array(model.PomToProject.parse((xml \\ "project").head))
      }.getOrElse{
        println(s"failed to load ${p.getName}")
        Array.empty[model.Project]
      }
    }
}