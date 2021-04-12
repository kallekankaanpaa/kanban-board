package ui

import scalafxml.core.DependenciesByType
import scalafx.scene.Parent

trait Component {
  val fxmlPath: String
  def toUIComponent: Parent
}
