package controllers

import scalafx.Includes._
import scalafx.event.Event
import scalafx.scene.Scene
import scalafx.scene.input.{MouseEvent, DragEvent, ClipboardContent, TransferMode}
import scalafx.scene.control.Label
import scalafx.scene.layout.GridPane
import scalafx.stage.{Stage, StageStyle, Modality}
import scalafxml.core.macros.sfxml
import javafx.scene.input.{TransferMode => jfxtm}

import data.{Card, Column}
import events.RefreshEvent
import ui.Utils

@sfxml
class CardController(
    private val source: GridPane,
    private val header: Label,
    private val description: Label,
    private val card: Card,
    private val column: Column
) {

  header.text = card.header
  description.text = card.description

  def handleOpen(event: Event): Unit = {
    val stage = new Stage(StageStyle.Undecorated)
    stage.scene = new Scene(Utils.readFXML("/fxml/Modal.fxml").load.asInstanceOf[javafx.scene.Parent])
    stage.initModality(Modality.ApplicationModal)
    stage.show()
  }

  def move(event: MouseEvent): Unit = {
    val db = source.startDragAndDrop(jfxtm.ANY: _*)
    db.content = ClipboardContent(Card.DataFormat -> card)

    event.consume()
  }

  def clean(event: DragEvent): Unit = {
    val parent = source.parent()
    if (event.transferMode == TransferMode.Move && event.dragboard.content.isEmpty && parent != null) {
      column.cards -= card
      source.parent().fireEvent(new RefreshEvent())
    }
  }
}
