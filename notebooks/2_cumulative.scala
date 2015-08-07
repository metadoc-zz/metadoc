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
  groupBy(p => (p.released.getYear, p.released.getMonthOfYear)).
  map{case (k, ps) =>
      val sample = ps.head.released
      (k, 
       ps.count(scalaV(9)),
       ps.count(scalaV(10)),
       ps.count(scalaV(11)),
       sample.monthOfYear.asShortText, 
       sample.getYear
      )
  }.
  toList.sortBy(_._1).
  foldLeft(((0, 0, 0), List.empty[(Int, Int, Int, String, Int)])){ 
    case (((csv9, csv10, csv11), cs), (_, sv9, sv10, sv11, m, y)) =>
    val ac9 = csv9 + sv9
    val ac10 = csv10 + sv10
    val ac11 = csv11 + sv11
    ((ac9, ac10, ac11), (ac9, ac10, ac11, m, y) :: cs)
  }._2.reverse.
  flatMap{case (sv9, sv10, sv11, m, y) => 
    List(
      s"2.9,$m $y,$sv9",
      s"2.10,$m $y,$sv10",
      s"2.11,$m $y,$sv11"
    )
  }
).mkString(System.lineSeparator)