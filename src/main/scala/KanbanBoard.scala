import scalafx.application.JFXApp
import scalafx.Includes._
import scalafx.scene.Scene
import scalafx.scene.layout._
import scalafx.scene.control._
import scalafx.scene.shape.Rectangle
import scalafx.scene.shape.Circle
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader, ControllerDependencyResolver}
import java.io.IOException
import scalafx.scene.Parent
import scalafxml.core.DependenciesByType
import scala.reflect.runtime.universe.typeOf

import data.Card

object KanbanBoard extends JFXApp {

  val root = readFXML("fxml/KanbanBoard.fxml")

  stage = new JFXApp.PrimaryStage {
    title.value = "Kanban board"
    width = 650
    height = 400
    scene = new Scene(card(Card("With Header")))
  }

  private def readFXML(path: String, controller: ControllerDependencyResolver): Parent = {
    val component = getClass.getResource(path)
    if (component == null) throw new IOException(s"""Could not read "${path}"""")
    else FXMLView(component, controller)
  }

  private def readFXML(path: String): Parent = readFXML(path, NoDependencyResolver)

  private def card(card: Card): Parent = readFXML("fxml/Card.fxml", new DependenciesByType(Map(typeOf[Card] -> card)))
}
