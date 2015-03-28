object Pom {
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
      val res = (px \ n).text
      if(res.isEmpty) None
      else Some(f(res))
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

      def pUrl = {
        o("url", url)
      }
      def pLicenses = {
        ss("licenses", "license", x => {
          License(r2("name", x), url(r2("url", x)), r2("type", x))
        })
      }
      def pMailingLists = {
        ss("mailingLists", "mailingList", x => {
          License(r2("name", x), url(r2("url", x)), r2("type", x))
        })
      }
      def pContributors = {
        Set[Person]()
      }
      def pDistributionManagement = {
        Set[Repository]()
      }
      def pOrganization = {
        Some(Organization("bob", url("http://example.org")))
      }
      def pCiManagement = {
        Set[ContiniousIntegration]()
      }
      def pIssueManagement = {
        Set[IssueTracker]()
      }
      def pInceptionYear = {
        Some(Year(2014))
      }
      def pDevelopers = {
        Set[Person]()
      }
      def pDependencies = {
        Set[Dependency]()
      }
      def pResolvers = {
        Set[Repository]()
      }
      def pPackaging = {
        Set[ArtifactPackaging]()
      }
      def pSourceControlManagement = {
        Some(Github("bob", "bob"))
      }

    Project(
      r("groupId"),
      r("artifactId"),
      Version(version),
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