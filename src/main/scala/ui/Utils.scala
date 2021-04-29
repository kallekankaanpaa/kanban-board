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
    val stream = new ObjectOutputStream(new FileOutputStream(filename(board.name)))
    stream.writeObject(board)
    stream.close
  }

  def load(path: String): Board = {
    val stream = new ObjectInputStream(new FileInputStream(path))
    val data = stream.readObject
    stream.close
    data.asInstanceOf[Board]
  }

  /** Generates filename from parameter.
    *
    * Changes all characters to lowercase first since some filesystems are case-sensitive.
    * Replaces all whitespace characters with underscores and append .kbb (KanBan Board)
    * suffix to the filename
    *
    * @param name Original name to turn into filename
    * @return Filesystem friendly name for the file
    */
  private def filename(name: String): String = name.toLowerCase.replaceAll("\\s", "_") + ".kbb"
}
