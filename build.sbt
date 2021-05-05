name := "Kanban Board"

version := "0.1"

scalaVersion := "2.13.5"

scalacOptions += "-Ymacro-annotations"
fork := true

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.0"

libraryDependencies += "org.scalafx" %% "scalafx" % "15.0.1-R21"
libraryDependencies += "org.scalafx" %% "scalafxml-core-sfx8" % "0.5"

lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux")   => "linux"
  case n if n.startsWith("Mac")     => "mac"
  case n if n.startsWith("Windows") => "win"
  case _                            => throw new Exception("Unknown platform!")
}

lazy val javaFXModules =
  Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map(m => "org.openjfx" % s"javafx-$m" % "15.0.1" classifier osName)
