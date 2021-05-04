package controllers

import scalafx.event.Event
import scalafx.scene.layout.AnchorPane
import scalafx.scene.control.{TextArea, TextField}
import scalafxml.core.macros.sfxml

import data.{Card, Time}
import events.CloseModalEvent

@sfxml
class ModalController(
    private val modal: AnchorPane,
    private val card: Card,
    private val header: TextField,
    private val description: TextArea,
    private val assignee: TextField,
    private val estimate: TextField,
    private val used: TextField,
    private val _new: Boolean
) {

  header.text = card.header
  description.text = card.description
  assignee.text = card.assignee
  estimate.text = card.timeEstimate.raw
  used.text = card.timeUsed.raw

  def cancel(event: Event): Unit = modal.fireEvent(new CloseModalEvent())

  def save(event: Event): Unit = {
    card.header = header.text()
    card.description = description.text()
    card.assignee = assignee.text()
    card.timeEstimate = Time(estimate.text())
    card.timeUsed = Time(used.text())
    modal.fireEvent(new CloseModalEvent(_new))
  }
}
