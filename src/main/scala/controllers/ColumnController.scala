package controllers

import scalafx.event.Event
import scalafx.scene.input.{DragEvent, TransferMode}
import scalafx.scene.layout.VBox
import scalafxml.core.macros.sfxml

import data.{Card, Column, Board}
import scalafx.scene.input.DataFormat

@sfxml
class ColumnController(private val cards: VBox, private val column: Column, private val board: Board) {

  cards.children = column.cards.map(_.toUIComponent(board, column))

  def addCard(event: DragEvent): Unit = {
    val db = event.dragboard
    event.dropCompleted = if (db.hasContent(Card.DataFormat)) {
      column.cards += db.content(Card.DataFormat).asInstanceOf[Card]
      db.clear()
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

  def removeCard(card: Card): Unit = {
    column.cards -= card
  }
}
