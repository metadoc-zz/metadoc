package model

object PomToProject {
  type Xml = scala.xml.Node
  def parse(px: Xml): Project = {
    import java.net.{URL => Url}
    def url(v: String) = {
      if(v.startsWith("http") || v.startsWith("https")) new Url(v)
      else new Url("http://" + v)
    }

    def r2(n: String, p: Xml): String = (p \ n).text
    def r(n: String): String = r2(n, px)
    
    def o[T](n: String, f: String => T = (a: String) => a): Option[T] = {
      o2(n, f, px)
    }
    def o2[T](n: String, f: String => T = (a: String) => a, p: Xml): Option[T] = {
      o3(n, xml => f(xml.text))
    }

    def o3[T](n: String, f: Seq[Xml] => T): Option[T] = {
      val res = (px \ n)
      if(res.isEmpty) None
      else Some(f(res.toSeq))
    }

    def ss[T](n: String, v: String, f: Xml => T): Set[T] = {
      val res = (px \\ n)
      if(res.isEmpty) Set()
      else {
        res.head.child.flatMap{ r =>
          val rv = (r \\ v)
          if(rv.isEmpty) List()
          else List(f(rv.head))
        }.toSet
      }
    }

    val version = 
      px.child.find(_.label == "version").getOrElse(
        px.child.find(_.label == "parent").get.child.find(_.label == "version").get
      ).text


    val pomArtifactId = r("artifactId") // scala-parser-combinators_2.11.0-M5

    def pDependencies: Set[Dependency] = {
      ss("dependencies", "dependency", x => {
        JvmDependency(r2("groupId", x), r2("artifactId", x), Version(r2("version", x)))
      })
    }

    val dependencies = pDependencies

    // _2.11 binary
    // _2.11.0-M5 full


    val scalaVersion = 
      dependencies.collect{
        case JvmDependency(g, a, v, _) => (g, a, v)
      }.find{ case (g, a, _)=>
        g == "org.scala-lang" && a == "scala-library"
      }.map(_._3)


    val (artifactId, scalaVersionInArtifact) = {
      val t = pomArtifactId.split("_")
      (t.init.mkString(""), Version(t.last))
    }

    val ScalaCompatibility =
      scalaVersion.flatMap { sv =>
        if(sv.full(scalaVersionInArtifact)) Some(Full)
        else if(sv.binary(scalaVersionInArtifact)) Some(Binary)
        else None
      }

    def pUrl = {
      scala.util.Try(o("url", url)).getOrElse(None)
    }
    def pLicenses = {
      ss("licenses", "license", x => {
        License(r2("name", x), url(r2("url", x)), r2("type", x))
      })
    }
    def pMailingLists = {
      ss("mailingLists", "mailingList", x => {
        MailingList(
          r2("name", x), 
          url(r2("post", x)),
          url(r2("subscribe", x)),
          url(r2("unsubscribe", x))
        )
      })
    }
    def pContributors = {
      ss("contributors", "contributor", x => {
        Person(
          r2("id", x),
          r2("name", x),
          // o2("url", url, x)
          None
        )
      })
    }
    def pDistributionManagement = {
      ss("distributionManagement", "repository", x => {
        Repository(
          r2("id", x),
          r2("name", x),
          url(r2("url", x)),
          r2("layout", x)
        )
      })
    }
    def pCiManagement = {
      o3("ciManagement", xml => {
        val name = (xml \ "system").text
        val ciUrl = url((xml \ "url").text)

        if(ciUrl.getHost.contains("travis.com")) Travis(ciUrl)
        else OtherCI(name, ciUrl)
      })
    }
    def pOrganization = {
      o3("organization", xml => 
        Organization((xml \ "name").text, url("http://example.org"))
      )
    }
    
    def pIssueManagement = {
      o3("issueManagement", xml => {
        val name = (xml \ "system").text
        val iUrl = url((xml \ "url").text)

        if(iUrl.getHost.contains("github.com")) GithubIssues(iUrl)
        else OtherIssues(name, iUrl)
      })
    }
    def pInceptionYear = {
      o("inceptionYear").map(v => Year(v.toInt))
    }
    def pDevelopers = {
      ss("developers", "developer", x => {
        Person(
          r2("id", x),
          r2("name", x)
          // o2("url", url, x)
        )
      })
    }
    
    def pResolvers = {
      ss("repositories", "repository", x => {
        Repository(
          r2("id", x),
          r2("name", x),
          url((x \ "url").text),
          r2("layout", x)
        )
      })
    }
    def pPackaging = {
      Set[ArtifactPackaging]()
    }
    def pSourceControlManagement = {
      Some(Github("bob", "bob"))
    }

    Project(
      r("groupId"),
      artifactId,//r("artifactId")
      Version(version),
      scalaVersion,
      Some(Full), // :ScalaCompatibility
      o("description"),
      pUrl,
      pLicenses,
      pMailingLists,
      pContributors,
      pDistributionManagement,
      pOrganization,
      pCiManagement,
      pIssueManagement,
      pInceptionYear,
      pDevelopers,
      pDependencies,
      pResolvers,
      pPackaging,
      pSourceControlManagement,
      packages = Set()    
    )
  }
}