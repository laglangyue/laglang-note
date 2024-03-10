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

lazy val coding = project.in(file("coding"))

lazy val mdoc = project
  .in(file("mdoc"))
  .enablePlugins(MdocPlugin, DocusaurusPlugin)
  .settings(
    publish / skip := true,
    name           := "laglangyue-docs",
    mdocIn         := (ThisBuild / baseDirectory).value / "docs",
    run / fork     := true,
    scalacOptions -= "-Xfatal-warnings",
    scalacOptions += "-Wunused:imports"
  )
