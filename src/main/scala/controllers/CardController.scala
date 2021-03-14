package controllers

import scalafxml.core.macros.sfxml
import scalafx.event.ActionEvent
import scalafx.event.Event
import scalafx.scene.control._
import data.Card
import javafx.fxml.FXML

@sfxml
class CardController(private val header: Label, private val description: Label, private val card: Card) {

  header.text = card.header
  description.text = card.description

  def handleOpen(event: Event): Unit = ???
}
