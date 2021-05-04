package ui

import java.io.{IOException, ObjectOutputStream, ObjectInputStream, FileOutputStream, FileInputStream}

import scalafx.scene.Parent
import scalafxml.core.{ControllerDependencyResolver, FXMLView, FXMLLoader, NoDependencyResolver}

import data.Board

object Utils {
  def readFXML(path: String, controller: ControllerDependencyResolver): FXMLLoader = {
    val component = getClass.getResource(path)
    if (component == null) throw new IOException(s"""Could not read "${path}"""")
    else new FXMLLoader(component, controller)
  }

  def readFXML(path: String): FXMLLoader = readFXML(path, NoDependencyResolver)

  def save(board: Board): Unit = {
    val stream = new ObjectOutputStream(new FileOutputStream(board.path))
    stream.writeObject(board)
    stream.close
  }

  def load(path: String): Board = {
    val stream = new ObjectInputStream(new FileInputStream(path))
    val data = stream.readObject
    stream.close
    data.asInstanceOf[Board]
  }
}
