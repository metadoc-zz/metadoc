import java.io.File

object _3_PomToModel extends App {
  override def main(args: Array[String]): Unit = {
    val scalaVersion = Scala.version
    def recursiveListFiles(f: File): Array[File] = {
      val these = f.listFiles
      these.filter(_.isFile) ++ these.filter(_.isDirectory).flatMap(recursiveListFiles)
    }
    val res =
    recursiveListFiles(new File(s"res/poms_${scalaVersion}")).map{ f =>
    	val date = f.getName.dropWhile(_ != '_').drop(1).dropWhile(_ != '_').drop(1).dropRight(".pom".length)

      val xml =
        scala.xml.XML.loadString(
          scala.io.Source.fromFile(f).mkString
        )

      val p = xml \\ "project"

     	// val deps = (p \ "dependencies").map{ v =>
      //   val res = (v \\ "dependency").map{ one =>
      //     toJson(Map(
      //       "groupId" -> (one \ "groupId").text,
      //       "artifactId" -> (one \ "artifactId").text,
      //       "version" -> (one \ "version").text
      //     ))
      //   }
      //   JsArray(res)
      // }.headOption.getOrElse(JsArray(Seq.empty[JsValue]))


      // val licenses = (p \ "licenses").map{ v =>
      //   val res = (v \\ "license").map{ one =>
      //     toJson(Map(
      //       "name" -> (one \ "name").text,
      //       "url" -> (one \ "url").text
      //     ))
      //    }
      //   JsArray(res)
      // }.headOption.getOrElse(JsArray(Seq.empty[JsValue]))

      // val scm = (p \ "scm").map(v => JsString((v \ "url").text)).headOption.getOrElse(JsNull)

      // val org = (p \ "organization").map(v =>
      // 	toJson(Map(
      //       "name" -> (v \ "name").text,
      //       "url" -> (v \ "url").text
      //   ))
      // ).headOption.getOrElse(JsNull)

      // toJson(Map(
      // 	"date" -> JsString(date),
      //   "description" -> JsString((p \ "description").text),
      //   "url" -> JsString((p \ "url").text),
    		// "groupId" -> JsString((p \ "groupId").text),
      //   "artifactId" -> JsString((p \ "artifactId").text),
    		// "version" -> JsString((p \ "version").text),
    		// "description" -> JsString((p \ "description").text),
      //   "organization" -> toJson(org),
      //   "dependencies" -> toJson(deps),
      //   "licenses" -> toJson(licenses),
      //   "scm" -> scm
      // ))
    }
    // val path = s"web/web/data/scala_${scalaVersion}.json"
    // val file = new java.io.File(path)
    // if(!file.exists) file.getParentFile.mkdirs()
  }
}
