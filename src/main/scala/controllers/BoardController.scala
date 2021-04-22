package controllers

import java.io.File

import scalafx.scene.layout.HBox
import scalafx.stage.{FileChooser, Window}
import scalafx.stage.FileChooser.ExtensionFilter
import scalafx.scene.Scene
import scalafxml.core.macros.sfxml

import ui.Utils
import data.Board

@sfxml
class BoardController(private val columns: HBox, private var board: Board) {

  refresh()

  def refresh(): Unit = columns.children = board.columns.map(_.toUIComponent)

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
