package data

import ui.{Component, Utils}

import scalafx.scene.Parent
import scalafxml.core.DependenciesByType
import scala.reflect.runtime.universe.typeOf

@SerialVersionUID(1L)
class Board(var name: String, var columns: Set[Column]) extends Serializable with Component {
  val fxmlPath: String = "/fxml/Board.fxml"
  def toUIComponent: Parent = Utils.readFXML(fxmlPath, new DependenciesByType(Map(typeOf[Set[Column]] -> columns)))
}
