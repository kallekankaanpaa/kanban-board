package controllers

import scalafx.event.Event
import scalafx.scene.input.{DragEvent, TransferMode}
import scalafx.scene.layout.VBox
import scalafxml.core.macros.sfxml

import data.Card
import scalafx.scene.input.DataFormat

@sfxml
class ColumnController(private val cards: VBox, private val name: String, private val cardData: Set[Card]) {
  cards.children = cardData.map(_.toUIComponent)

  def addCard(event: DragEvent) = {
    val db = event.dragboard
    event.dropCompleted = if (db.hasContent(Card.DataFormat)) {
      cardData + db.content(Card.DataFormat).asInstanceOf[Card]
      true
    } else {
      false
    }

    event.consume()
  }

  def onOver(event: DragEvent) = {
    event.acceptTransferModes(TransferMode.Move)
    event.consume()
  }
  def onEntered(event: DragEvent) = {
    event.consume()
  }
  def onExited(event: DragEvent) = {
    event.consume()
  }
}
