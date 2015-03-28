
package model

import java.net.{URL => Url}


case class License(name: String, url: Url, tpe: String)
// trait License { def url: Url }
// case object Apache_1_1          extends License { def url = new Url("...")}
// case object Apache_2_0          extends License { def url = new Url("...")}
// case object BSD4Clauses         extends License { def url = new Url("...")}
// case object BSD3Clauses         extends License { def url = new Url("...")}
// case object BSD2Clauses         extends License { def url = new Url("...")}
// case object AfferoGPL_3         extends License { def url = new Url("...")}
// case object GPL_2               extends License { def url = new Url("...")}
// case object GPL_3               extends License { def url = new Url("...")}
// case object LGPL_2_1            extends License { def url = new Url("...")}
// case object LGPL_3_0            extends License { def url = new Url("...")}
// case object MIT                 extends License { def url = new Url("...")}
// case object MozilaPublicLicense extends License { def url = new Url("...")}
// case object W3C                 extends License { def url = new Url("...")}
// case object Scala               extends License { def url = new Url("...")}
// case object Public              extends License { def url = new Url("...")}
// case object CreativeCommon_4_0  extends License { def url = new Url("...")}
// case object Eclipse             extends License { def url = new Url("...")}
// case object WTFPL               extends License { def url = new Url("...")}
// case object ISC                 extends License { def url = new Url("...")}
// case object W3C                 extends License { def url = new Url("...")}

case class ScalaArtifactVersion(version: Version, scala: Version)

object Version {
  def apply(v: String): Version = {
    semverfi.Version(v) match {
      case semverfi.NormalVersion(a, b, c) => SemanticVersion(a, b, c)
      case semverfi.PreReleaseVersion(a, b, c, d) => SemanticVersion(a, b, c, Some(d.mkString("")))
      case semverfi.BuildVersion(a, b, c, d, e) => SemanticVersion(a, b, c, Some(d.mkString("")), Some(e.mkString("")))
      case _ :semverfi.Invalid => { RawVersion(v) }
    }
  }
}
// http://semver.org/
trait Version
case class RawVersion(a: String) extends Version
case class SemanticVersion(
  major: Int,
  minor: Int,
  patch: Int,
  preRelease: Option[String] = None,
  metaData: Option[String] = None
) extends Version

case class Organization(
  name: String,
  url: Url
)

trait SourceControlManagement
case class Github(user: String, repo: String) extends SourceControlManagement
case class Git(url: Url) extends SourceControlManagement

sealed trait Scope
case object TestScope

sealed trait Dependency
case class JvmDependency(
  groupId: String,
  artifactId: String,
  version: Version,
  scope: Option[Scope] = None
) extends Dependency
case class ScalaDependencyVar(
  groupId: String,
  artifactId: String,
  scalaVersion: Version,
  version: Version
) extends Dependency
case class ScalaDependencyVal(p: Project) extends Dependency

case class MailingList(
  name: String,
  post: Url, // dev@spark.apache.org
  subscribe: Url, // dev-subscribe@spark.apache.org
  unsubscribe: Url // dev-unsubscribe@spark.apache.org
)

case class Person(
  id: String,
  name: String,
  url: Option[Url]
)

sealed trait Repository
case class Release(id: String, name: String, url: Url)
case class Snapshot(id: String, name: String, url: Url)

sealed trait ContiniousIntegration
case class Travis(url: Url) extends ContiniousIntegration
case class OtherCI(name: String, url: Url) extends ContiniousIntegration

sealed trait IssueTracker
case class GithubIssues(url: Url) extends IssueTracker

case class Year(v: Int)

sealed trait ArtifactPackaging
case object War extends ArtifactPackaging
case object Pom extends ArtifactPackaging
case object MavenArchetype extends ArtifactPackaging
case object Aar extends ArtifactPackaging
case object Maven extends ArtifactPackaging
case object Bundle extends ArtifactPackaging
case object Jar extends ArtifactPackaging

sealed trait Licenses


case class Project(
  groupId: String,
  artifactId: String,
  version: Version,
  description: Option[String] = None,
  url: Option[Url] = None,
  licenses: Set[License] = Set(),
  mailingLists: Set[MailingList] = Set(),
  contributors: Set[Person] = Set(),
  distributionManagement: Set[Repository] = Set(),
  organization: Option[Organization] = None,
  ciManagement: Set[ContiniousIntegration] = Set(),
  issueManagement: Set[IssueTracker] = Set(),
  inceptionYear: Option[Year] = None,
  developers: Set[Person] = Set(),
  dependencies: Set[Dependency] = Set(),
  resolvers: Set[Repository] = Set(), // repositories
  packaging: Set[ArtifactPackaging] = Set(),
  sourceControlManagement: Option[SourceControlManagement] = None,
  packages: Set[Package] = Set()
)