package com.scalakata.metadoc
package model
  
import java.net.{URL => Url}

trait License { def url: Url }
case object MIT extends License { def url = new Url("http://opensource.org/licenses/MIT") }

trait Version
case class SemVer(epic: Int, major: Int, minor: Int)

object Version {
  def parse(in: String): Version = ???
}

case class Organization(
  name: String,
  url: Url
)

trait SourceControlManagement
case class Git(url: Url) extends SourceControlManagement

case class Developer(
  id: String,
  name: String,
  url: Url
)

case class Dependency(
  groupId: String,
  artifactId: String,
  description: String,
  url: Url,
  version: Version
)

case class Project(
  groupId: String,
  artifactId: String,
  description: String,
  url: Url,
  version: Version,
  licenses: Set[License],
  name: String,
  organization: Organization,
  scm: SourceControlManagement,
  developers: List[Developer],
  dependencies: List[Dependency],
  packages: List[Package]
)

case class Package(name: String)