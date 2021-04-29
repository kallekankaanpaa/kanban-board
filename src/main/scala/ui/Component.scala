package ui

import scalafx.scene.Parent

import data.Board

trait Component {
  val fxmlPath: String
  def toUIComponent(board: Board): Parent
}
