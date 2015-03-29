Array.concat(
  recursiveListFiles(new File(s"../res/poms_2.10")),
  recursiveListFiles(new File(s"../res/poms_2.11"))
).map{ p =>
  import scala.collection.JavaConverters._
  val source = Files.readAllLines(Paths.get(p.toURI)).asScala.mkString(System.lineSeparator)
  val xml = scala.xml.XML.loadString(source)
  (xml \\ "project" \\ "licenses" \\ "name").text
}.groupBy(identity).mapValues(_.size).toList.sortBy(_._2).mkString(System.lineSeparator)

