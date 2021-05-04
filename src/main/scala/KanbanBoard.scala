import scalafx.application.JFXApp
import scalafx.scene.Scene

import utils.Utils
import data.{Card, Column, Board}

object KanbanBoard extends JFXApp {

  val board = new Board("first_board.kbb", Set())

  stage = new JFXApp.PrimaryStage {
    title.value = "Kanban board"
    width = 1100
    height = 600
    scene = new Scene(board.toUIComponent(board))
  }

  stage.scene().getRoot().requestFocus()
}
