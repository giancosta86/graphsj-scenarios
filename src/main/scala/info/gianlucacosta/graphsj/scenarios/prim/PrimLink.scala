package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.{LinkArrow, LinkHandleRadius, VisualLink}

import java.util.UUID
import info.gianlucacosta.eighthbridge.graphs.features.Weighted
import info.gianlucacosta.helios.mathutils.Numbers
import scalafx.geometry.Point2D


case class PrimLink(weight: Double,
                    internalPoints: List[Point2D] = Nil,
                    selected: Boolean = false,
                    labelCenter: Option[Point2D] = None,
                    styleClasses: Set[String] = Set(),
                    id: UUID = UUID.randomUUID()
                   ) extends VisualLink with Weighted {

  val minWeight = 0.0
  val maxWeight = Double.MaxValue

  override val text: String = Numbers.smartString(weight)

  override def setWeight(weight: Double): this.type =
    copy(weight = weight).asInstanceOf[this.type]

  override def visualCopy(text: String, internalPoints: List[Point2D], selected: Boolean, labelCenter: Option[Point2D], arrow: LinkArrow, handleRadius: LinkHandleRadius, styleClasses: Set[String]): PrimLink.this.type =
    copy(
      internalPoints = internalPoints,
      selected = selected,
      labelCenter = labelCenter,
      styleClasses = styleClasses
    ).asInstanceOf[this.type]
}