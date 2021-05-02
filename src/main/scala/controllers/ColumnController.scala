package controllers

import scalafx.Includes._
import scalafx.event.Event
import scalafx.scene.control.Label
import scalafx.scene.input.{DragEvent, TransferMode}
import scalafx.scene.layout.VBox
import scalafxml.core.macros.sfxml

import data.{Card, Column, Board}
import events.RefreshEvent

@sfxml
class ColumnController(private val cards: VBox, private val column: Column, private val name: Label) {

  name.text = column.name

  refresh()

  def addCard(event: DragEvent): Unit = {
    val db = event.dragboard
    if (db.hasContent(Card.DataFormat)) {
      val card = db.content(Card.DataFormat).asInstanceOf[Card]
      if (!column.cards.contains(card)) {
        column.cards += card
      }
      db.clear()
      event.dropCompleted = true
    } else {
      event.dropCompleted = false
    }

    event.consume()
    refresh()
  }

  def refresh(): Unit = {
    cards.children = column.cards.map(_.toUIComponent(column))
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

  cards.addEventHandler(
    RefreshEvent.REFRESH,
    new javafx.event.EventHandler[RefreshEvent] {
      override def handle(event: RefreshEvent) = refresh()
    }
  )
}
