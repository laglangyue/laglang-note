import Dependencies.Versions._

ThisBuild / resolvers ++= Seq(
  Resolver.mavenLocal,
  "Sonatype OSS Snapshots" at "https://s01.oss.sonatype.org/content/repositories/snapshots/",
  "Sonatype OSS Releases" at "https://s01.oss.sonatype.org/content/repositories/releases"
)

inThisBuild(
  List(
    scalaVersion           := scala3_Version,
    sonatypeCredentialHost := "oss.sonatype.org",
    sonatypeRepository     := "https://oss.sonatype.org/service/local",
    homepage               := Some(url("https://github.com/laglang-note")),
    licenses               := List("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))
  )
)

lazy val laglang = (project in file("."))
  .aggregate(
    coding
  )
  .settings(
    publish / skip := true,
    commands ++= Commands.value
  )

lazy val coding = project.in(file("coding"))

lazy val modules = project.in(file("modules"))

lazy val docs = project
  .in(file("mdoc"))
  .enablePlugins(MdocPlugin, DocusaurusPlugin)
  .settings(
    publish / skip := true,
    name           := "laglang-docs",
    mdocIn         := (ThisBuild / baseDirectory).value / "docs",
    run / fork     := true,
    scalacOptions -= "-Xfatal-warnings",
    scalacOptions += "-Wunused:imports"
  )
  .dependsOn(coding)
