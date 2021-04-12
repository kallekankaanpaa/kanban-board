package controllers

import scalafxml.core.macros.sfxml
import scalafx.scene.layout.HBox
import scalafx.stage.FileChooser
import scalafx.stage.FileChooser.ExtensionFilter

import ui.Utils
import data.Board
import scalafx.scene.Scene
import scalafx.stage.Window
import java.io.File

@sfxml
class BoardController(private val columns: HBox, private var board: Board) {

  refresh

  def refresh: Unit = columns.children = board.columns.map(_.toUIComponent)

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
      refresh
    }
  }
}
