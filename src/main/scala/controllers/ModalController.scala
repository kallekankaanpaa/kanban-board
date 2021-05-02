package controllers

import scalafxml.core.macros.sfxml
import data.Card
import scalafx.event.Event
import scalafx.scene.layout.GridPane
import events.CloseModalEvent
import scalafx.scene.control.{TextArea, TextField}

@sfxml
class ModalController(
    private val modal: GridPane,
    private val card: Card,
    private val header: TextField,
    private val description: TextArea,
    private val _new: Boolean
) {

  header.text = card.header
  description.text = card.description

  def cancel(event: Event): Unit = modal.fireEvent(new CloseModalEvent())

  def save(event: Event): Unit = {
    card.header = header.text()
    card.description = description.text()
    modal.fireEvent(new CloseModalEvent(_new))
  }
}
