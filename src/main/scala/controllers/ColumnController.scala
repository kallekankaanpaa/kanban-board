package controllers

import scalafx.Includes._
import scalafx.event.{ActionEvent, Event}
import scalafx.scene.control.TextField
import scalafx.scene.input.{ContextMenuEvent, DragEvent, TransferMode}
import scalafx.scene.layout.VBox
import scalafxml.core.macros.sfxml

import data.{Card, Column, Board}
import events.{RefreshEvent, RemoveColumnEvent}
import scalafx.scene.control.ContextMenu
import scalafx.scene.control.MenuItem

@sfxml
class ColumnController(private val cards: VBox, private val column: Column, private val name: TextField) {

  name.text = column.name

  val contextMenu = new ContextMenu()
  val delete = new MenuItem("Delete column")
  delete.onAction = (event: ActionEvent) => cards.parent().fireEvent(new RemoveColumnEvent(column))
  contextMenu.items += delete
  contextMenu.hideOnEscape = true

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

  def onAction(event: ActionEvent) = {
    column.name = name.text()
    cards.requestFocus()
  }

  def hideContextMenu(event: Event): Unit = contextMenu.hide()

  def contextMenu(event: ContextMenuEvent): Unit = contextMenu.show(cards, event.screenX, event.screenY)
}
