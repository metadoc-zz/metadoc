
package model

import java.net.{URL => Url}

trait License { def url: Url }
case object MIT extends License { def url = new Url("http://opensource.org/licenses/MIT") }

case class ScalaArtifactVersion(version: Version, scala: Version)

trait Version
case class SemVer(major: Int, minor: Int, patch: Int)

object Version {
  def parse(in: String): Version = ???
}

case class Organization(
  name: String,
  url: Url
)

trait SourceControlManagement
case class Github(user: String, repo: String) extends SourceControlManagement
case class Git(url: Url) extends SourceControlManagement

sealed trait Scope
case object TestScope

case class Dependency(
  groupId: String,
  artifactId: String,
  version: Version,
  scalaVersion: Option[Version], // java: None
  scope: Option[Scope]
)

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
  scalaVersion: Version,
  description: Option[String],
  url: Option[Url],
  licenses: Set[License],
  // name: String, << ??
  mailingLists: Set[MailingList],
  contributors: Set[Person],
  distributionManagement: Set[Repository],
  organization: Option[Organization],
  ciManagement: Set[ContiniousIntegration],
  issueManagement: Set[IssueTracker],
  inceptionYear: Option[Year],
  scm: Option[SourceControlManagement],
  developers: Set[Person],
  dependencies: Set[Dependency],
  resolvers: Set[Repository], // repositories
  packaging: Set[ArtifactPackaging],
  sourceControlManagement: Option[SourceControlManagement],
  packages: Set[Package]
)
