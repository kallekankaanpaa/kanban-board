package controllers

import scala.reflect.runtime.universe.typeOf

import scalafx.Includes._
import scalafx.event.Event
import scalafx.scene.Scene
import scalafx.scene.input.{MouseEvent, DragEvent, ClipboardContent, TransferMode}
import scalafx.scene.control.Label
import scalafx.scene.layout.AnchorPane
import scalafx.stage.{Stage, StageStyle, Modality}
import scalafxml.core.macros.sfxml
import scalafxml.core.DependenciesByType
import javafx.scene.input.{TransferMode => jfxtm}

import data.{Card, Column}
import events.{RefreshEvent, CloseModalEvent}
import utils.Utils

@sfxml
class CardController(
    private val source: AnchorPane,
    private val header: Label,
    private val description: Label,
    private val assignee: Label,
    private val timeLeft: Label,
    private val tags: Label,
    private val card: Card,
    private val column: Column
) {

  header.text = card.header
  description.text = card.description
  assignee.text = card.assignee
  timeLeft.text = card.timeRemaining.toString()
  tags.text = card.tags.mkString(", ")

  val stage = new Stage(StageStyle.Unified)
  stage.scene = new Scene(
    Utils
      .readFXML("/fxml/Modal.fxml", new DependenciesByType(Map(typeOf[Card] -> card)))
      .load
      .asInstanceOf[javafx.scene.Parent]
  )
  stage.initModality(Modality.ApplicationModal)
  stage.addEventHandler(
    CloseModalEvent.CLOSE_MODAL,
    (event: CloseModalEvent) => {
      source.parent().fireEvent(new RefreshEvent())
      stage.close()
    }
  )

  def handleOpen(event: Event): Unit = stage.show()

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
