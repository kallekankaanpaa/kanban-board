import scala.reflect.macros.Attachments
import scala.util.control
import scala.collection.mutable.Buffer

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
}

/** A Card that contains details of a task */
@SerialVersionUID(1L)
class Card extends Serializable {

  /** Card with no header is considered empty */
  private var _header: Option[String] = None
  private var _description: Option[String] = None
  private var _assignee: Option[String] = None

  /** Time used in seconds */
  private var _timeUsed: Option[Long] = None

  /** Estimated time for the completion of the task in seconds */
  private var _timeEstimate: Option[Long] = None
  private var _attachments: Set[AnyVal] = Set()
  private var _tags: Set[Tag] = Set()

  // Values for time conversations
  private val Week = 604800
  private val Day = 86400
  private val Hour = 3600
  private val Minute = 60

  /** Adds provided tag to card if it doesn't exist yet */
  def tags_+(tag: Tag): Unit = _tags = _tags + tag

  /** Removes tag from card if it exists */
  def tags_-(tag: Tag): Unit = _tags = _tags - tag

  /** Returns all tags of the card */
  def tags: Set[Tag] = _tags

  /** Returns boolean indicating whether card has been tagged with the provided tag */
  def hasTag(tag: Tag): Boolean = _tags.contains(tag)

  /** Set time estimation for the card
    *
    * The time estimation should be as string which contains the amouts of months, weeks, days, hours and minutes as separate blocks.
    *
    * Each block should be in `[number][identifier]` format with whitespaces separating each block.
    * Order of the blocks doesn't matter.
    *
    * Possible identifiers for time blocks are: `w`(Week), `d`(Days), `h`(Hours), `m`(minutes)
    *
    * Throws an exception if format is malformed
    *
    * @param estimate The time estimation in format specified above
    */
  def timeEstimate_=(estimate: String): Unit = this._timeEstimate = Some(parseTime(estimate))

  /** Add to time estimation of the card
    *
    * The time estimation should be as string which contains the amouts of months, weeks, days, hours and minutes as separate blocks.
    *
    * Each block should be in `[number][identifier]` format with whitespaces separating each block.
    * Order of the blocks doesn't matter.
    *
    * Possible identifiers for time blocks are: `w`(Week), `d`(Days), `h`(Hours), `m`(minutes)
    *
    * Throws an exception if format is malformed
    *
    * @param estimate The time to add in format specified above
    */
  def timeEstimate_+(estimate: String): Unit = this._timeEstimate = this._timeEstimate match {
    case Some(time) => Some(time + parseTime(estimate))
    case None       => Some(parseTime(estimate))
  }

  /** Adds time to the used time counter
    *
    * The time estimation should be as string which contains the amouts of months, weeks, days, hours and minutes as separate blocks.
    *
    * Each block should be in `[number][identifier]` format with whitespaces separating each block.
    * Order of the blocks doesn't matter.
    *
    * Possible identifiers for time blocks are: `w`(Week), `d`(Days), `h`(Hours), `m`(minutes)
    *
    * Throws an exception if format is malformed
    *
    * @param time The time to add in format specified above
    */
  def timeUsed_+(usedTime: String): Unit = this._timeUsed = this._timeUsed match {
    case Some(time) => Some(time + parseTime(usedTime))
    case None       => Some(parseTime(usedTime))
  }

  /** Helper method to parse the time
    *
    * Parses time from a string which contains blocks of months, weeks, days, hours and minutes.
    *
    * For example, `"6h 30m"` would return `16200`
    *
    * Throws an exception if the string is malformed
    *
    * @param time String in the format specified above
    * @return Time given in seconds
    */
  @throws[FormatException]
  private def parseTime(time: String): Long = {
    var seconds = 0

    def blockToData(block: String): (Int, Char) = {
      val identifier = block.last
      val amount = block.dropRight(1).toIntOption
      amount match {
        case Some(amount) if amount >= 0 => (amount, identifier)
        case _                           => throw new FormatException(time, "Invalid time amount")
      }
    }

    for ((amount, identifier) <- time.split(' ').map(blockToData)) {
      seconds += amount * (identifier match {
        case 'w' => Week
        case 'd' => Day
        case 'h' => Hour
        case 'm' => Minute
        case _   => throw new FormatException(time, "Invalid identifier for time")
      })
    }
    seconds
  }

  /** @return String representation of the time estimate
    */
  def timeEstimate: String = this._timeEstimate.map(formatTime).getOrElse("No estimate set")

  /** @return String representation of the used time
    */
  def timeUsed: String = this._timeUsed.map(formatTime).getOrElse("No time used")

  /** @return String representation of the time remaining from the estimation
    */
  def timeRemaining: String = (this._timeEstimate, this._timeUsed) match {
    case (Some(estimate), Some(used)) => formatTime(estimate - used)
    case (Some(_), None)              => "No estimation"
    case (None, Some(_))              => "No time used"
    case (None, None)                 => "No estimation or time used"
  }

  /** Formats long value containing seconds to human readable string
    * @param seconds Amount of seconds to format
    * @return String representation of the time
    */
  private def formatTime(seconds: Long): String = {
    var blocks = Buffer[(Long, String)]()
    blocks += seconds / Week -> "Week"
    blocks += seconds % Week / Day -> "Day"
    blocks += seconds % Week % Day / Hour -> "Hour"
    blocks += seconds % Week % Day % Hour / Minute -> "Minute"

    blocks
      .filter(_._1 != 0)
      .map(data => if (data._1 != 1) s"${data._1} ${data._2}s" else s"${data._1} ${data._2}")
      .mkString(" ")
  }

  /** Card with empty header is considered empty */
  def isEmpty = _header.isEmpty

  def header: String = _header.getOrElse("Empty card")

  def header_=(header: String): Unit = this._header = Some(header)

  def description: String = _description.getOrElse("No description")

  def description_=(description: String) = this._description = Some(description)

  def assignee: String = _assignee.getOrElse("This card hasn't been assigned yet.")

  def assignee_=(assignee: String) = this._assignee = Some(assignee)

  override def toString(): String = s"${this.header}\n\n${this.description}"
}

/** Exception for handling malformed time strings
  *
  * @constructor Creates a new FormatException with original string and message
  * @param original The original string in invalid format
  * @param message Message about the failure
  */
class FormatException(original: String, message: String) extends Exception(original)
