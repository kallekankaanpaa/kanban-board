package controllers

import scalafxml.core.macros.sfxml
import data.Card

@sfxml
class ModalController(private val card: Card) {
  println("modal")
}
