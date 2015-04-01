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
    recursiveListFiles(new File(s"../poms")).filter(_.getName != ".git").map{ p =>
      import scala.collection.JavaConverters._
      val source = Files.readAllLines(Paths.get(p.toURI)).asScala.mkString(System.lineSeparator)
      val xml = scala.xml.XML.loadString(source)
      model.PomToProject.parse((xml \\ "project").head)
    }
}