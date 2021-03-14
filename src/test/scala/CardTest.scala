import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.Assertions._

import data.{Card, Tag, FormatException, Time}

class CardTest extends AnyFlatSpec {
  "Empty card" should "be empty" in {
    val card = Card()
    assert(card.isEmpty)
  }

  it should "have an empty estimate" in {
    val card = Card()
    assert(card.timeEstimate.toString() === "No time set")
  }

  it should "have a correct estimate after setting an estimate" in {
    val card = Card()
    card.timeEstimate = Time("2d 10m")
    assert(card.timeEstimate.toString() === "2 Days 10 Minutes")
    card.timeEstimate = Time("5h 10m")
    assert(card.timeEstimate.toString() === "5 Hours 10 Minutes")
    card.timeEstimate = Time("5w 10d 1h 7m")
    assert(card.timeEstimate.toString() === "6 Weeks 3 Days 1 Hour 7 Minutes")
    card.timeEstimate += Time("2d 10m")
    assert(card.timeEstimate.toString() === "6 Weeks 5 Days 1 Hour 17 Minutes")
    card.timeEstimate -= Time("7d")
    assert(card.timeEstimate.toString() === "5 Weeks 5 Days 1 Hour 17 Minutes")

  }

  it should "throw FormatException when time format is invalid" in {
    val card = Card()
    assertThrows[FormatException] {
      Time("5h10m")
    }

    assertThrows[FormatException] {
      Time("5h 10mm")
    }

    assertThrows[FormatException] {
      Time("5h 10 ")
    }
  }

  "Card with tags" should "return correct tags" in {
    var card = Card()
    card += Tag("Test", "Desc")
    assert(card.tags.contains(Tag("Test", "Desc")))
  }

  "Removing tag" should "remove the tag" in {
    var card = Card()
    card += Tag("Test", "Desc")
    assume(card.tags.contains(Tag("Test", "Desc")))
    card -= Tag("Test", "Desc")
    assert(!card.tags.contains(Tag("Test", "Desc")))
  }

  "Card with header" should "have header" in {
    val card = Card("Card's header")
    assert(card.header === "Card's header")
  }
}
