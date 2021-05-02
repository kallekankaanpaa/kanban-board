package controllers

import scala.reflect.runtime.universe.typeOf

import scalafx.Includes._
import scalafx.scene.layout.HBox
import scalafx.scene.Scene
import scalafx.stage.{FileChooser, Window, Stage, StageStyle, Modality}
import scalafx.stage.FileChooser.ExtensionFilter
import scalafxml.core.macros.sfxml
import scalafxml.core.DependenciesByType
import java.io.File

import ui.Utils
import data.{Board, Card, Column}
import events.{CloseModalEvent, RemoveColumnEvent}

@sfxml
class BoardController(private val columns: HBox, private var board: Board) {

  refresh()

  var card = Card()

  val stage = new Stage(StageStyle.Unified)
  stage.initModality(Modality.ApplicationModal)
  stage.addEventHandler(
    CloseModalEvent.CLOSE_MODAL,
    (event: CloseModalEvent) => {
      if (event.isNew()) {
        board.columns.head.cards += card.clone()
      }
      refresh()
      stage.close()
    }
  )

  columns.addEventHandler(
    RemoveColumnEvent.REMOVE,
    (event: RemoveColumnEvent) => {
      board.columns -= event.getColumn()
      refresh()
    }
  )

  def refresh(): Unit = columns.children = board.columns.map(_.toUIComponent(board))

  def newCard: Unit = {
    card = Card()
    stage.scene = new Scene(
      Utils
        .readFXML("/fxml/Modal.fxml", new DependenciesByType(Map(typeOf[Card] -> card, typeOf[Boolean] -> true)))
        .load
        .asInstanceOf[javafx.scene.Parent]
    )
    stage.show()
  }

  def addColumn: Unit = {
    board.columns += Column.Empty
    refresh()
  }

  def saveBoard: Unit = Utils.save(board)

  def loadBoard: Unit = {
    val fileChooser = new FileChooser {
      title = "Select board"
      initialDirectory = new File(".")
    }
    fileChooser.getExtensionFilters().add(new ExtensionFilter("Kanban Boards", "*.kbb"))
    val file = fileChooser.showOpenDialog(columns.scene().getWindow())
    if (file != null) {
      board = Utils.load(file.getName())
      refresh()
    }
  }
}
