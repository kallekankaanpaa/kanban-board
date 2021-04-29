package data

import scala.reflect.runtime.universe.typeOf

import scalafx.scene.Parent
import scalafxml.core.DependenciesByType
import scalafx.Includes._

import controllers.ColumnController
import ui.{Component, Utils}

@SerialVersionUID(1L)
class Column(val name: String, var cards: Set[Card]) extends Serializable with Component {
  val fxmlPath: String = "/fxml/Column.fxml"

  def toUIComponent(board: Board): Parent = Utils
    .readFXML(fxmlPath, new DependenciesByType(Map(typeOf[Column] -> this, typeOf[Board] -> board)))
    .load
    .asInstanceOf[javafx.scene.Parent]
}
