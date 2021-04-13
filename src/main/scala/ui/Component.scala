package ui

import scalafx.scene.Parent

trait Component {
  val fxmlPath: String
  def toUIComponent: Parent
}
