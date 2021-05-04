package data

import scala.reflect.runtime.universe.typeOf

import scalafx.Includes._
import scalafx.scene.Parent
import scalafxml.core.DependenciesByType

import controllers.BoardController
import ui.{Component, Utils}

@SerialVersionUID(1L)
class Board(var name: String, var columns: Set[Column]) extends Serializable with Component {
  val fxmlPath: String = "/fxml/Board.fxml"
  def toUIComponent(board: Board): Parent =
    Utils.readFXML(fxmlPath, new DependenciesByType(Map(typeOf[Board] -> this))).load.asInstanceOf[javafx.scene.Parent]

  def allTags: Seq[Tag] = columns.flatMap(_.cards).flatMap(_.tags).toSeq.sortBy(_.identifier)
}
