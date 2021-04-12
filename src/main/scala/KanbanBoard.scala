import scalafx.application.JFXApp
import scalafx.Includes._
import scalafx.scene.Scene
import scalafx.scene.layout._
import scalafx.scene.control._
import scalafx.scene.shape.Rectangle
import scalafx.scene.shape.Circle
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader, ControllerDependencyResolver}
import java.io.IOException
import scalafx.scene.Parent
import scalafxml.core.DependenciesByType
import scala.reflect.runtime.universe.typeOf

import ui.Utils
import data.{Card, Column, Board}

object KanbanBoard extends JFXApp {

  val testCard = Card("With header")
  val testColumn = new Column("testi column", Set(testCard, Card("Another one")))
  val anotherColumn = new Column("toka", Set(Card("Asdf")))
  val board = new Board("testi board", Set(testColumn, anotherColumn))

  stage = new JFXApp.PrimaryStage {
    title.value = "Kanban board"
    width = 650
    height = 400
    scene = new Scene(board.toUIComponent)
  }
}
