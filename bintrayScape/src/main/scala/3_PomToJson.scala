// import java.io.File

// object _3_PomToModel {
//   def main(args: Array[String]): Unit = {
//     val scalaVersion = Scala.version
//     import java.io.File

//     import java.nio.file._
//     import java.nio.charset._

//     def recursiveListFiles(f: File): Array[File] = {
//       val these = f.listFiles
//       if(these == null) Array()
//       else these.filter(_.isFile) ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
//     }

//     val res =
//     Array.concat(
//     //  recursiveListFiles(new File(s"./res/poms_2.10")),
//       recursiveListFiles(new File(s"./res/poms_2.11"))
//     ).take(10).map{ p =>
//       import scala.collection.JavaConverters._
//       val source = Files.readAllLines(Paths.get(p.toURI)).asScala.mkString(System.lineSeparator)
//       val xml = scala.xml.XML.loadString(source)
//       // println((xml \\ "project").head)
//       model.PomToProject.parse((xml \\ "project").head)
//     }.filter(!_.dependencies.isEmpty).take(1)

//     println(res.toList)//.map(_.artifactId).mkString(System.lineSeparator))

//   }
// }
