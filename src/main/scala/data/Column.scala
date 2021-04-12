package data

import ui.{Component, Utils}
import scalafx.scene.Parent
import scala.reflect.runtime.universe.typeOf
import scalafxml.core.DependenciesByType

class Column(val name: String, val cards: Set[Card]) extends Component {
  val fxmlPath: String = "/fxml/Column.fxml"
  def toUIComponent: Parent =
    Utils.readFXML(fxmlPath, new DependenciesByType(Map(typeOf[Set[Card]] -> cards, typeOf[String] -> name)))
}
