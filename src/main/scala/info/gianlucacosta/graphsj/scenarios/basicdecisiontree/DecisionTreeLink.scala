package info.gianlucacosta.graphsj.scenarios.basicdecisiontree

import info.gianlucacosta.eighthbridge.fx.{LinkArrow, LinkHandleRadius, VisualLink}

import java.util.UUID
import info.gianlucacosta.helios.mathutils.Numbers
import info.gianlucacosta.lambdaprism.classification.basic.DecisionTreeLeaf
import scalafx.geometry.Point2D

case class DecisionTreeLink(
                             targetTreeLeaf: DecisionTreeLeaf,
                             selected: Boolean = false,
                             internalPoints: List[Point2D] = List(),
                             labelCenter: Option[Point2D] = None,
                             id: UUID = UUID.randomUUID()
                           ) extends VisualLink {
  override def text: String =
    s"Value: ${targetTreeLeaf.attributeValue}\nWeight: ${Numbers.smartString(targetTreeLeaf.weight)}"

  override def styleClasses: Set[String] =
    Set()

  override def visualCopy(
                           text: String,
                           internalPoints: List[Point2D],
                           selected: Boolean,
                           labelCenter: Option[Point2D],
                           arrow: LinkArrow,
                           handleRadius: LinkHandleRadius,
                           styleClasses: Set[String]
                         ): DecisionTreeLink.this.type =
    copy(
      internalPoints = internalPoints,
      selected = selected,
      labelCenter = labelCenter
    ).asInstanceOf[this.type]
}
