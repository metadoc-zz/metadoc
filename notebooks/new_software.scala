import com.scalakata._; @instrument class Playground {
  import model._
import com.github.nscala_time.time.Imports._

def scalaV(v: Int)(p: Project): Boolean = {
  p.scalaVersion match {
    case Some(SemanticVersion(ma, mi, pa, _, _)) => mi == v
    case _ => false
  }
}

("version,date,count" ::
Extract.res.
  groupBy(p => (p.artifactId, p.groupId)).
  map{case (k, ps) =>
      ps.minBy(_.released)
  }.
  groupBy(p => (p.released.getYear, p.released.getMonthOfYear)).
  map{case (k, ps) =>
      val sample = ps.head.released
      (k, ps.count(scalaV(9)), ps.count(scalaV(10)), ps.count(scalaV(11)), sample.monthOfYear.asShortText, sample.getYear)
  }.
  toList.sortBy(_._1).
  flatMap{case (k, sv9, sv10, sv11, m, y) => 
    List(
      s"2.9,$m $y,$sv9",
      s"2.10,$m $y,$sv10",
      s"2.11,$m $y,$sv11"
    )
  }
).mkString(System.lineSeparator)



// https://jsfiddle.net/uvk7mfvm/7/embedded/result/
}