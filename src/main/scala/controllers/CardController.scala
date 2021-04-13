package controllers

import scalafx.event.Event
import scalafx.scene.control.Label
import scalafxml.core.macros.sfxml

import data.Card

@sfxml
class CardController(private val header: Label, private val description: Label, private val card: Card) {

  header.text = card.header
  description.text = card.description

  def handleOpen(event: Event): Unit = ???
}
