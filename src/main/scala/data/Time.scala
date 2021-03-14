package data

import scala.collection.mutable.Buffer

object Time {
  def apply(): Time = new Time

  def apply(raw: Long): Time = {
    var time = new Time
    time.seconds = Some(raw)
    time
  }

  def apply(input: String): Time = {
    var time = new Time
    time.seconds = Some(parseTime(input))
    time
  }

  /** Week in seconds */
  private val Week = 604800

  /** Day in seconds */
  private val Day = 86400

  /** Hour in seconds */
  private val Hour = 3600

  /** Minute in seconds */
  private val Minute = 60

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
  @throws[FormatException]("if provided time string is malformed")
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
}

@SerialVersionUID(1L)
class Time extends Serializable {
  private var seconds: Option[Long] = None

  /** Add two times together
    *
    * @param time The `Time` to add to this `Time`
    * @return a new `Time` which is the sum
    */
  def +(time: Time): Time = (this.seconds, time.seconds) match {
    case (Some(a), Some(b)) => Time(a + b)
    case (Some(a), None)    => Time(a)
    case (None, Some(b))    => Time(b)
    case (None, None)       => Time()
  }

  /** Subtract times
    *
    * @param time The `Time` to subtract from this `Time`
    * @return a new `Time` which is the result
    */
  def -(time: Time): Time = (this.seconds, time.seconds) match {
    case (Some(a), Some(b)) => Time(a - b)
    case (Some(a), None)    => Time(a)
    case (None, Some(b))    => Time(0 - b)
    case (None, None)       => Time()
  }

  /** Turns the Long time to human readable time string */
  override def toString(): String = seconds match {
    case Some(time) =>
      var blocks = Buffer[(Long, String)]()
      blocks += time / Time.Week -> "Week"
      blocks += time % Time.Week / Time.Day -> "Day"
      blocks += time % Time.Week % Time.Day / Time.Hour -> "Hour"
      blocks += time % Time.Week % Time.Day % Time.Hour / Time.Minute -> "Minute"

      blocks
        .filter(_._1 != 0)
        .map(data => if (data._1 != 1) s"${data._1} ${data._2}s" else s"${data._1} ${data._2}")
        .mkString(" ")
    case None =>
      "No time set"
  }
}

/** Exception for handling malformed time strings
  *
  * @constructor Creates a new FormatException with original string and message
  * @param original The original string in invalid format
  * @param message Message about the failure
  */
class FormatException(original: String, message: String) extends Exception(original)
