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

// import java.io.File
// import java.nio.file._
// import model._
// def recursiveListFiles(f: File): Array[File] = {
//   val these = f.listFiles
//   if(these == null) Array()
//   else these.filter(_.isFile) ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
// }

// type Xml = scala.xml.Node
// def parseProject(px: Xml): Project = {
//   def t(n: String) = (px \ n).text

//   val version = 
//     px.child.find(_.label == "version").getOrElse(
//       px.child.find(_.label == "parent").get.child.find(_.label == "version").get
//     ).text

//   Project(
//     groupId = t("groupId"),
//     artifactId = t("artifactId"),
//     version = Version(version)
//   )
// }


// recursiveListFiles(new File(s"../res/poms_2.11")).drop(1303).take(1).map{ p =>
//   import scala.collection.JavaConverters._
//   val source = Files.readAllLines(Paths.get(p.toURI)).asScala.mkString(System.lineSeparator)
//   val xml = scala.xml.XML.loadString(source)
//   parseProject((xml \\ "project").head)
// }


//     description: Option[String] = None,
//     url: Option[Url] = None,
//     licenses: Set[License] = Set(),
//     mailingLists: Set[MailingList] = Set(),
//     contributors: Set[Person] = Set(),
//     distributionManagement: Set[Repository] = Set(),
//     organization: Option[Organization] = None,
//     ciManagement: Set[ContiniousIntegration] = Set(),
//     issueManagement: Set[IssueTracker] = Set(),
//     inceptionYear: Option[Year] = None,
//     scm: Option[SourceControlManagement] = None,
//     developers: Set[Person] = Set(),
//     dependencies: Set[Dependency] = Set(),
//     resolvers: Set[Repository] = Set(), // repositories
//     packaging: Set[ArtifactPackaging] = Set(),
//     sourceControlManagement: Option[SourceControlManagement] = None,
//     packages: Set[Package] = Set()