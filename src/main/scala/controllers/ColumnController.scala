package controllers

import scalafx.scene.layout.VBox
import scalafxml.core.macros.sfxml

import data.Card

@sfxml
class ColumnController(private val cards: VBox, private val name: String, private val cardData: Set[Card]) {
  cards.children = cardData.map(_.toUIComponent)
}
