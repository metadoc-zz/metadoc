
move bintray to >
https://www.jfrog.com/confluence/pages/viewpage.action?pageId=26083425

```
pom extraction
// mailingLists 13
<mailingLists>
  <mailingList>
    <name>Dev Mailing List</name>
    <post>dev@spark.apache.org</post>
    <subscribe>dev-subscribe@spark.apache.org</subscribe>
    <unsubscribe>dev-unsubscribe@spark.apache.org</unsubscribe>
  </mailingList>
</mailingLists>
// contributors 23
<contributors>
  <contributor>
  <name>Andrew Ash</name>
  <url>http://github.com/ash211</url>
  </contributor>
<contributors>
// distributionManagement 45
<distributionManagement>
  <repository>
    <id>dev-iesl.cs.umass.edu</id>
    <name>dev-iesl.cs.umass.edu</name>
    <url>https://dev-iesl.cs.umass.edu/nexus/content/repositories/releases</url>
  </repository>
  <snapshotRepository>
    <id>dev-iesl.cs.umass.edu</id>
    <name>dev-iesl.cs.umass.edu</name>
    <url>https://dev-iesl.cs.umass.edu/nexus/content/repositories/snapshots</url>
    <uniqueVersion>false</uniqueVersion>
  </snapshotRepository>
  <site>
    <id>ieslwww</id>
    <name>IESL www repository</name>
    <url>scp://iesl.cs.umass.edu/m/iesl/data2/www/iesl/maven/factorie</url>
  </site>
</distributionManagement>
// profiles 47 << _
// ciManagement 61
<ciManagement>
  <system>Travis CI</system>
  <url>https://travis-ci.org/applicius/play-dok</url>
</ciManagement>
// build 146 << _
// parent 241 << _
// issueManagement 287
<issueManagement>
  <system>GitHub</system>
  <url>https://github.com/applicius/play-dok/issues</url>
</issueManagement>
// properties 326 << _
// inceptionYear 1954
<inceptionYear>
  2015
</inceptionYear>
// repositories 2505
<repositories>
  <repository>
    <id>TypesafeReleasesRepository</id>
    <name>Typesafe Releases Repository</name>
    <url>http://repo.typesafe.com/typesafe/releases/</url>
    <layout>default</layout>
  </repository>
  <repository>
    <id>BintrayResolvevmunierscalajs</id>
    <name>Bintray-Resolve-vmunier-scalajs</name>
    <url>http://dl.bintray.com/content/vmunier/scalajs/</url>
    <layout>default</layout>
  </repository>
</repositories>
// developers 12030
<developers>
  <developer>
    <id>vmunier</id>
    <name>Vincent Munier</name>
    <url>https://github.com/vmunier</url>
  </developer>
</developers>
// scm 12084
<scm>
  <url>git@github.com:rtyley/bfg-repo-cleaner.git</url>
  <connection>scm:git:git@github.com:rtyley/bfg-repo-cleaner.git</connection>
</scm>
// url 12125
<url>
  http://www.monifu.org/
</url>
// organization 13302
<organization>
  <name>com.twitter</name>
</organization>
// packaging 13336
<packaging>war</packaging>
<packaging>pom</packaging>
<packaging>maven-archetype</packaging>
<packaging>aar</packaging>
<packaging>maven-plugin</packaging>
<packaging>bundle</packaging>
<packaging>jar</packaging>
// licenses 13558
GNU Gpl v3 1
WTFPL, Version 2 1
Apache License Version 2.0The New BSD License 1
Atlassian 3.0 End User License Agreement 1
AThe MIT License (MIT) 1
GNU LESSER GENERAL PUBLIC LICENSE, Version 3 1
Eclipse Public License - v 1.0 1
MIT-style license 1
The MIT LicenseGNU General Public License, version 2 (GPL-2.0) 1
GNU GENERAL PUBLIC LICENSE, Version 3.0 1
lgpl 1
Apache-2.0MIT 1
AGPL v3 1
LGPLv2.1 1
AGPL-3.0 2
Apache-2.0Apache-2.0 2
Apache License, Version 2 2
Apache 2 license 2
ISC License 2
New BSD License 2
ISC 2
GNU Lesser General Public License, Version 2.1Eclipse Public License, Version 1.0 3
GNU General Public License (GPL) 3
BSD-Style 3
Public domainBSD-like 3
The BSD 2-Clause License 4
Apache License Version 2.0 4
LGPL-2.1 4
Mozilla Public License Version 2.0 4
BSD 3-Clause License 5
AGPL 5
Apache Public License 2.0 5
GNU General Public License v2 5
Apache 2 License 5
Creative Commons Attribution-ShareAlike 4.0 International 6
LGPL 3.0 license 6
GNU Lesser General Public Licence 6
The Apache License, ASL Version 2.0 6
GPL-2.0 7
Figaro License 7
MPL-2.0MPL-2.0 7
3-clause BSD 7
LGPLv3 8
DO WHAT YOU WANT TO PUBLIC LICENSE, Version 1 8
New BSD 8
Apache 2.0 8
Apache 2.0Academic License (for original lex files)Apache 2.0 (for supplemental code) 8
Public domain 9
mit 9
The Apache Software Licence, Version 2.0 10
MIT licencse 10
Scala license 10
CC BY 4.0. 11
MITBSD New 11
MIT Licence 11
The BSD 3-Clause License 11
GNU Library or Lesser General Public License (LGPL) 11
Scala License 11
GNU Lesser General Public License v3.0 12
APACHE-2.0 13
GPL-3.0 13
Apache v2 13
The MIT License (MIT) 13
MPL-2.0W3C 16
APSL-2.0 16
LGPL3 17
BSD Software License, 2-clause version 18
LGPL v3+ 19
LGPL 23
BSD-2-Clause 24
BSD Simplified 24
Apache License, ASL Version 2.0 24
GPL version 3 or any later version 27
ASL 28
Apache license 32
Two-clause BSD-style license 34
LGPL v3 38
Apache 2.0 License 42
GNU Lesser General Public License, Version 2.1 44
Apache License, Verison 2.0 44
GPLv3 45
LGPL-3.0 45
BSD-3-Clause 48
BSD 3-clause 52
Apache License 55
Three-clause BSD-style 55
BSD 3-Clause 63
Apache 71
Apache-style 71
Apache License 2.0 74
the Apache License, ASL Version 2.0 76
W3C 91
GPL v3+ 92
Apache2 92
BSD-like 97
Apache V2 100
The Apache License, Version 2.0 109
Apache License, Version 2.0Apache License, Version 2.0 114
The MIT License 131
BSD 132
BSD New 135
LGPL v2.1+ 165
MPL-2.0 187
GPL v2+ 191
Apache 2.0 246
The Apache Software License, Version 2.0 268
MIT license 275
Affero GPLv3 430
MIT License 893
MIT-style 920
BSD-style 1041
Apache-2.0 1123
MIT 1286
Apache License, Version 2.0 1809
Apache 2 2155
// description 13601
<description>
  text
</description>
// version 13633
<version>
  2.4.5
</version>
// name 13673
<name>
  scala-parser-combinators
</name>
// groupId 13675
<groupId>
  org.scala-lang.modules
</groupId>
// dependencies 13709
<dependencies>
  <dependency>
    <groupId>org.scala-lang</groupId>
    <artifactId>scala-library</artifactId>
    <version>2.11.0-M5</version>
  </dependency>
  <dependency>
    <groupId>org.scala-lang.modules</groupId>
    <artifactId>scala-partest-interface_2.11.0-M5</artifactId>
    <version>0.2</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.scala-lang.modules</groupId>
    <artifactId>scala-partest_2.11.0-M5</artifactId>
    <version>1.0-RC5</version>
    <scope>test</scope>
  </dependency>
</dependencies>
// artifactId 13731
<artifactId>
  scala-parser-combinators_2.11.0-M5
</artifactId>
```