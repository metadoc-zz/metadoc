package com.scalakata.metadoc.plugin

import org.specs2._

class UtilsSpec extends Specification { def is = s2"""
 UtilsSpecs
   print tree $printTree
"""

  def printTree = {
    import scalaz.std.anyVal._

    print("a", List(1, 2)) ====
      """|a
         |├── 1
         |└── 2""".stripMargin
  }
}