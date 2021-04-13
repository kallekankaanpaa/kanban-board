package data

import scala.reflect.runtime.universe.typeOf

import scalafx.scene.Parent
import scalafxml.core.DependenciesByType

import ui.{Component, Utils}

@SerialVersionUID(1L)
class Column(val name: String, val cards: Set[Card]) extends Serializable with Component {
  val fxmlPath: String = "/fxml/Column.fxml"
  def toUIComponent: Parent =
    Utils.readFXML(fxmlPath, new DependenciesByType(Map(typeOf[Set[Card]] -> cards, typeOf[String] -> name)))
}
