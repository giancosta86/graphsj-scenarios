package info.gianlucacosta.graphsj.scenarios.basicdecisiontree

import java.util.UUID

import scalafx.geometry.Point2D

case object InstructionsVertex extends DecisionTreeVertex {
  override def text: String =
    "This graph cannot be edited.\n\nPress the Run button\nto see the intermediate results\nand the decision tree."


  override def center: Point2D =
    new Point2D(420, 200)

  override def visualCopy(center: Point2D, selected: Boolean): DecisionTreeVertex =
    InstructionsVertex

  override def styleClasses: List[String] =
    List(
      "instructionsVertex"
    )

  override def selected: Boolean =
    false

  override val id: UUID =
    UUID.randomUUID()
}
