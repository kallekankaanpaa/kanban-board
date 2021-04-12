package ui

import scalafxml.core.{ControllerDependencyResolver, FXMLView, NoDependencyResolver}
import scalafx.Includes._
import java.io.IOException
import scalafx.scene.Parent

object Utils {
  def readFXML(path: String, controller: ControllerDependencyResolver): Parent = {
    val component = getClass.getResource(path)
    if (component == null) throw new IOException(s"""Could not read "${path}"""")
    else FXMLView(component, controller)
  }

  def readFXML(path: String): Parent = readFXML(path, NoDependencyResolver)
}
