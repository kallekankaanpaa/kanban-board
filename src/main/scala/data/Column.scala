package data

import scala.reflect.runtime.universe.typeOf

import scalafx.Includes._
import scalafx.scene.Parent
import scalafxml.core.DependenciesByType

import controllers.ColumnController
import utils.Utils

@SerialVersionUID(1L)
class Column(var name: String, var cards: Set[Card]) extends Serializable {
  val fxmlPath: String = "/fxml/Column.fxml"

  def toUIComponent(filters: Set[Tag]): Parent = Utils
    .readFXML(fxmlPath, new DependenciesByType(Map(typeOf[Column] -> this, typeOf[Set[Tag]] -> filters)))
    .load
    .asInstanceOf[javafx.scene.Parent]
}

object Column {
  def Empty: Column = new Column("", Set())
}
