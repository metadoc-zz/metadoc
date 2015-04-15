import model._

val version = SemanticVersion(2, 11, 5)

val res =
Extract.res.filter(_.dependencies.exists{
  case JvmDependency(gid, aid, v, _) => gid == "org.scala-lang" && aid == "scala-compiler"// && v == version
  case _ => false
}).map(d => (d.groupId, d.artifactId)).sortBy(_._1).distinct.map{ case (gid, aid) =>
  s"<tr><td>$gid</td><td>$aid</td></tr>"
}.mkString(System.lineSeparator)

html2"""
<style>
  table { 
    color: white;
  }
  th { 
    position: relative;
    text-align: left;
    text-decoration: underline;
    padding-bottom: 10px;
  }
  th:first-child, td:first-child {
    text-align: right;
    padding-right: 20px;
  }
</style>
<table>
  <tr><th>groupId</th><th>artifactId</th></tr>
  $res
</table>
"""





