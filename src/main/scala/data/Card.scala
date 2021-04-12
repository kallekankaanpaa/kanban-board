package data

import scala.util.control
import scala.collection.mutable.Buffer
import scalafx.scene.Parent
import scalafxml.core.DependenciesByType
import scala.reflect.runtime.universe.typeOf

import ui.{Component, Utils}

/** Factory for [[Card]] instances */
object Card {

  /** Creates an empty card */
  def apply(): Card = new Card

  /** Create a card with header */
  def apply(header: String): Card = {
    val card = new Card
    card.header = header
    card
  }

  def apply(
      header: Option[String],
      description: Option[String],
      assignee: Option[String],
      timeUsed: Time,
      timeEstimate: Time,
      attachments: Set[Attachment],
      tags: Set[Tag]
  ): Card = {
    val card = new Card
    card._header = header
    card._description = description
    card._assignee = assignee
    card._timeUsed = timeUsed
    card._timeEstimate = timeEstimate
    card._attachments = attachments
    card._tags = tags
    card
  }
}

/** A Card that contains details of a task */
@SerialVersionUID(1L)
class Card extends Serializable with Component {

  val fxmlPath: String = "/fxml/Card.fxml"

  /** Card with no header is considered empty */
  private var _header: Option[String] = None
  private var _description: Option[String] = None
  private var _assignee: Option[String] = None

  /** Time used in seconds */
  private var _timeUsed: Time = Time()

  /** Estimated time for the completion of the task in seconds */
  private var _timeEstimate: Time = Time()
  private var _attachments: Set[Attachment] = Set()
  private var _tags: Set[Tag] = Set()

  def header: String = _header.getOrElse("Empty card")
  def header_=(header: String): Unit = this._header = Some(header)

  def description: String = _description.getOrElse("No description")
  def description_=(description: String): Unit = this._description = Some(description)

  def assignee: String = _assignee.getOrElse("This card hasn't been assigned yet.")
  def assignee_=(assignee: String): Unit = this._assignee = Some(assignee)

  def timeEstimate_+(time: Time): Time = this._timeEstimate + time

  def timeEstimate_-(time: Time): Time = this._timeEstimate - time

  def timeEstimate_=(time: Time): Unit = this._timeEstimate = time

  def timeEstimate: Time = this._timeEstimate

  def timeUsed_+(time: Time): Time = this._timeUsed + time

  def timeUsed_=(time: Time): Unit = this._timeUsed = time

  def timeUsed: Time = this._timeUsed

  /** Returns `Time` object describing the difference of estimated and used times */
  def timeRemaining: Time = this._timeEstimate - this._timeUsed

  /** Returns a new `Card` with given attachment appended to the `Card` */
  def +(attachment: Attachment): Card = {
    val another = this.clone()
    another._attachments = this._attachments + attachment
    another
  }

  /** Returns a new `Card` with given attachment removed to the `Card` */
  def -(attachment: Attachment): Card = {
    val another = this.clone()
    another._attachments = this._attachments + attachment
    another
  }

  /** All attachments of this `Card` */
  def attachments: Set[Attachment] = _attachments

  /** Returns a new `Card` with given `Tag` added */
  def +(tag: Tag): Card = {
    val another = this.clone()
    another._tags = this._tags + tag
    another
  }

  /** Returns a new `Card` with given `Tag` removed */
  def -(tag: Tag): Card = {
    val another = this.clone()
    another._tags = this._tags - tag
    another
  }

  /** Returns all tags of the card */
  def tags: Set[Tag] = _tags

  /** Returns boolean indicating whether card has been tagged with the provided tag */
  def hasTag(tag: Tag): Boolean = _tags.contains(tag)

  /** Card with empty header is considered empty */
  def isEmpty = _header.isEmpty

  def toUIComponent: Parent = Utils.readFXML(fxmlPath, new DependenciesByType(Map(typeOf[Card] -> this)))

  override def toString(): String = s"${this.header}\n\n${this.description}"

  override protected[Card] def clone(): Card =
    Card(
      this._header,
      this._description,
      this._assignee,
      this._timeUsed,
      this._timeEstimate,
      this._attachments,
      this._tags
    )
}
