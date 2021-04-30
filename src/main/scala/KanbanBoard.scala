import scalafx.application.JFXApp
import scalafx.scene.Scene

import ui.Utils
import data.{Card, Column, Board}

object KanbanBoard extends JFXApp {

  val board = Utils.load("testi_board.kbb")

  stage = new JFXApp.PrimaryStage {
    title.value = "Kanban board"
    width = 650
    height = 400
    scene = new Scene(board.toUIComponent(board))
  }
}
