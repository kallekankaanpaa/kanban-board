import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.Assertions._

class CardTest extends AnyFlatSpec {
  "Empty card" should "be empty" in {
    val card = Card()
    assert(card.isEmpty)
  }

  it should "have an empty estimate" in {
    val card = Card()
    assert(card.timeEstimate === "No estimate set")
  }

  it should "have a correct estimate after setting an estimate" in {
    val card = Card()
    card.timeEstimate = "5h 10m"
    assert(card.timeEstimate === "5 Hours 10 Minutes")
    card.timeEstimate = "5w 10d 1h 7m"
    assert(card.timeEstimate === "6 Weeks 3 Days 1 Hour 7 Minutes")
    card.timeEstimate + "2d 10m"
    assert(card.timeEstimate === "6 Weeks 5 Days 1 Hour 17 Minutes")
  }

  it should "throw FormatException when time format is invalid" in {
    val card = Card()
    assertThrows[FormatException] {
      card.timeEstimate = "5h10m"
    }

    assertThrows[FormatException] {
      card.timeEstimate = "5h 10mm"
    }

    assertThrows[FormatException] {
      card.timeEstimate = "5h 10 "
    }
  }

  "Card with tags" should "return correct tags" in {
    val card = Card()
    card.tags + Tag("Test", "Desc")
    assert(card.tags.contains(Tag("Test", "Desc")))
  }

  "Removing tag" should "remove the tag" in {
    val card = Card()
    card.tags + Tag("Test", "Desc")
    assume(card.tags.contains(Tag("Test", "Desc")))
    card.tags - Tag("Test", "Desc")
    assert(!card.tags.contains(Tag("Test", "Desc")))
  }

  "Card with header" should "have header" in {
    val card = Card("Card's header")
    assert(card.header === "Card's header")
  }
}
