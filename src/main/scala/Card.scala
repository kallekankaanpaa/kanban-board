import scala.reflect.macros.Attachments
import scala.util.control
import scala.collection.mutable.Buffer

object Card {

  /** Creates an empty card */
  def apply(): Card = new Card

  /** Create a card with header */
  def apply(header: String): Card = {
    val card = new Card
    card.header = Some(header)
    card
  }
}

class Card {

  /** Card with no header is considered empty */
  var header: Option[String] = None
  var description: Option[String] = None
  var assignee: Option[String] = None

  /** Time used in seconds */
  private var timeUsed: Option[Long] = None

  /** Estimated time for the completion of the task in seconds */
  private var timeEstimate: Option[Long] = None
  var attachments: Set[AnyVal] = Set()
  var tags: Set[Tag] = Set()

  // Values for time conversations
  private val week = 604800
  private val day = 86400
  private val hour = 3600
  private val minute = 60

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
  def setTimeEstimate(estimate: String): Unit = this.timeEstimate = Some(parseTime(estimate))

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
  def addToEstimate(estimate: String): Unit = this.timeEstimate = this.timeEstimate match {
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
  def addUsedTime(usedTime: String): Unit = this.timeUsed = this.timeUsed match {
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
  private def parseTime(time: String): Long = {
    var seconds = 0

    def blockToData(block: String): (Int, Char) = {
      val identifier = block.last
      val amount = block.dropRight(1).toIntOption
      amount match {
        case Some(amount) if amount >= 0 => (amount, identifier)
        case _                           => throw new Exception("Invalid time amount")
      }
    }

    for ((amount, identifier) <- time.split(' ').map(blockToData)) {
      seconds += amount * (identifier match {
        case 'w' => week
        case 'd' => day
        case 'h' => hour
        case 'm' => minute
        case _   => throw new Exception("Invalid identifier for time")
      })
    }
    seconds
  }

  /** @return String representation of the time estimate
    */
  def estimate: String = this.timeEstimate match {
    case Some(estimate) => formatTime(estimate)
    case None           => "No estimate set"
  }

  /** @return String representation of the used time
    */
  def usedTime: String = this.timeUsed match {
    case Some(used) => formatTime(used)
    case None       => "No time used"
  }

  /** @return String representation of the time remaining from the estimation
    */
  def timeRemaining: String = (this.timeEstimate, this.timeUsed) match {
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
    blocks += seconds / week -> "Week"
    blocks += seconds % week / day -> "Day"
    blocks += seconds % week % day / hour -> "Hour"
    blocks += seconds % week % day % hour / minute -> "Minute"

    blocks
      .filter(_._1 != 0)
      .map(data => if (data._1 != 1) s"${data._1} ${data._2}s" else s"${data._1} ${data._2}")
      .mkString(" ")
  }

  /** Card with empty header is considered empty */
  def isEmpty = header.isEmpty

  override def toString(): String = header match {
    case Some(header) => header
    case None         => "Empty card"
  }
}
