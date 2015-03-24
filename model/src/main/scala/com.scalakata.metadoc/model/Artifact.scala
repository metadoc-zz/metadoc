package com.scalakata.metadoc
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
case class Git(url: Url) extends SourceControlManagement

case class Developer(
  id: String,
  name: String,
  url: Url
)

case class Dependency(
  groupId: String,
  artifactId: String,
  version: Version,
  scalaVersion: Version
)

case class Project(
  groupId: String,
  artifactId: String,
  version: Version,
  scalaVersion: Version
  description: Option[String],
  url: Option[Url],
  licenses: Set[License],
  // name: String, << ??
  organization: Option[Organization],
  scm: Option[SourceControlManagement],
  developers: List[Developer],
  dependencies: List[Dependency],
  packages: List[Package]
)
