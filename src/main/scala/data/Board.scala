package data

import scala.reflect.runtime.universe.typeOf

import scalafx.scene.Parent
import scalafxml.core.DependenciesByType

import ui.{Component, Utils}

@SerialVersionUID(1L)
class Board(var name: String, var columns: Set[Column]) extends Serializable with Component {
  val fxmlPath: String = "/fxml/Board.fxml"
  def toUIComponent: Parent = Utils.readFXML(fxmlPath, new DependenciesByType(Map(typeOf[Board] -> this)))
}
