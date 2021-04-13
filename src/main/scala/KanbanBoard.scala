import scalafx.application.JFXApp
import scalafx.scene.Scene

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
