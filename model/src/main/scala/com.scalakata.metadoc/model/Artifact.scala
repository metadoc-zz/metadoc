
package model

import java.net.{URL => Url}

trait License { def url: Url }
case object MIT extends License { def url = new Url("http://opensource.org/licenses/MIT") }

case class ScalaArtifactVersion(version: Version, scala: Version)

object Version {
  def apply(v: String): Version = {
    semverfi.Version(v) match {
      case semverfi.NormalVersion(a, b, c) => Version(a, b, c)
      case semverfi.PreReleaseVersion(a, b, c, d) => Version(a, b, c, Some(d.mkString("")))
      case semverfi.BuildVersion(a, b, c, d, e) => Version(a, b, c, Some(d.mkString("")), Some(e.mkString("")))
      case semverfi.Invalid(e) => sys.error(e)
    }
  }
}
// http://semver.org/
case class Version(
  major: Int,
  minor: Int,
  patch: Int,
  preRelease: Option[String] = None,
  metaData: Option[String] = None
)

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
  post: String, // dev@spark.apache.org
  subscribe: String, // dev-subscribe@spark.apache.org
  unsubscribe: String // dev-unsubscribe@spark.apache.org
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
