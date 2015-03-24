import java.io.File

object _3_PomToModel {
  def main(args: Array[String]): Unit = {
    val scalaVersion = Scala.version
    import java.io.File

    import java.nio.file._
    import java.nio.charset._

    def recursiveListFiles(f: File): Array[File] = {
      val these = f.listFiles
      if(these == null) Array()
      else these.filter(_.isFile) ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
    }

    recursiveListFiles(new File(s"../res/poms_{scalaVersion}")).map{ p =>
      import scala.collection.JavaConverters._
      val source = Files.readAllLines(Paths.get(p.toURI)).asScala.mkString(System.lineSeparator)
      val xml = scala.xml.XML.loadString(source)
      val ds = (xml \\ "project" \ "mailingLists")
      ds
    }.toSet
  }
}