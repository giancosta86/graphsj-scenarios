package info.gianlucacosta.graphsj.scenarios.basicdecisiontree

import java.util.UUID

import info.gianlucacosta.eighthbridge.fx.canvas.basic.BasicLink
import info.gianlucacosta.helios.mathutils.Numbers
import info.gianlucacosta.lambdaprism.classification.basic.DecisionTreeLeaf

import scalafx.geometry.Point2D

case class DecisionTreeLink(
                             targetTreeLeaf: DecisionTreeLeaf,
                             selected: Boolean = false,
                             internalPoints: List[Point2D] = List(),
                             labelCenter: Option[Point2D] = None,
                             id: UUID = UUID.randomUUID()
                           ) extends BasicLink[DecisionTreeLink] {
  override def text: String =
    s"Value: ${targetTreeLeaf.attributeValue}\nWeight: ${Numbers.smartString(targetTreeLeaf.weight)}"


  override def visualCopy(internalPoints: List[Point2D], selected: Boolean, labelCenter: Option[Point2D]): DecisionTreeLink =
    copy(
      internalPoints = internalPoints,
      selected = selected,
      labelCenter = labelCenter
    )


  override def styleClasses: List[String] =
    List()
}
