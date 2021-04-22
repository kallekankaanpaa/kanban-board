package controllers

import scalafx.event.Event
import scalafx.scene.input.{MouseEvent, DragEvent, Dragboard, ClipboardContent, DataFormat, TransferMode}
import scalafx.scene.control.Label
import scalafx.scene.layout.GridPane
import scalafxml.core.macros.sfxml
import javafx.scene.input.{TransferMode => jfxtm}

import data.{Card, Column}

@sfxml
class CardController(
    private val source: GridPane,
    private val header: Label,
    private val description: Label,
    private val card: Card
) {

  header.text = card.header
  description.text = card.description

  def handleOpen(event: Event): Unit = ???

  def move(event: MouseEvent): Unit = {
    val db = source.startDragAndDrop(jfxtm.ANY: _*)
    db.content = ClipboardContent(Card.DataFormat -> card)

    event.consume()
  }

  def clean(event: DragEvent): Unit = {
    if (event.transferMode == TransferMode.Move) {
      // remove from current column
      source.parent()
    }
  }
}
