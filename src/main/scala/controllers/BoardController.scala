package controllers

import scalafxml.core.macros.sfxml
import data.Column
import scalafx.scene.layout.HBox

@sfxml
class BoardController(private val columns: HBox, private val columnData: Set[Column]) {
  columns.children = columnData.map(_.toUIComponent)
}
