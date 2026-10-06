package info.gianlucacosta.graphsj.scenarios.basicdecisiontree

import java.util.UUID

import scalafx.geometry.Point2D

case object InstructionsVertex extends DecisionTreeVertex {
  override def text: String =
    "This graph cannot be edited.\n\nPress the Run button\nto see the intermediate results\nand the decision tree."


  override def center: Point2D =
    new Point2D(420, 200)

  override def styleClasses: Set[String] =
    Set(
      "instructionsVertex"
    )

  override def selected: Boolean =
    false

  override val id: UUID =
    UUID.randomUUID()

  override def visualCopy(text: String, center: Point2D, selected: Boolean, styleClasses: Set[String]): InstructionsVertex.this.type =
    InstructionsVertex
}
