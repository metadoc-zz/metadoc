# What is Metadoc ?

Scaladoc is my attempt at improving documentation of open source Scala projects.

* Catalog of open source Scala project extracted directly from Repositories such as Maven Central
* Executable documentation via [ScalaKata](http://www.scalakata.com)
  * Tailored from official websites (such as [play doc](https://www.playframework.com/documentation/2.0/ScalaHome))
  * Extracted from StackOverflow Questions/Answers
* Powerful Search à la [Hoogle](http://haskell.org/hoogle/)

# Current state

* Collected a list of all Scala related poms on Bintray (and maven because of [jCenter](https://bintray.com/bintray/jcenter)) ([scraper](https://github.com/metadoc/metadoc/tree/develop/bintrayScape), [poms](https://github.com/metadoc/poms))
* Created a [ScalaMeta compiler plugin](https://github.com/metadoc/metadoc/blob/develop/compilerPlugin/src/main/scala/com.scalakata.metadoc.plugin/Metadoc.scala) to show all packages and underlying classes
* Started a [community build](https://github.com/metadoc/dbuild) (currently only [cats](https://travis-ci.org/metadoc/cats/builds/51763930#L2846))
* Rewriting [ScalaKata](https://github.com/MasseGuillaume/ScalaKata2) from scratch
