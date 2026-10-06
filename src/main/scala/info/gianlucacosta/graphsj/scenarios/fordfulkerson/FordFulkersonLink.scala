package info.gianlucacosta.graphsj.scenarios.fordfulkerson

import info.gianlucacosta.eighthbridge.fx.{LinkArrow, LinkHandleRadius, VisualLink}

import java.util.UUID
import info.gianlucacosta.eighthbridge.graphs.features.Weighted
import info.gianlucacosta.helios.mathutils.Numbers
import scalafx.geometry.Point2D

//TODO! These links with non-negative weights could be factored out!
case class FordFulkersonLink(weight: Double,
                    styleClasses: Set[String] = Set(),
                    internalPoints: List[Point2D] = Nil,
                    @transient selected: Boolean = false,
                    labelCenter: Option[Point2D] = None,
                    id: UUID = UUID.randomUUID()
                   ) extends VisualLink with Weighted {

  val minWeight = 0.0
  val maxWeight = Double.MaxValue

  checkWeight()


  override val text: String = Numbers.smartString(weight)


  override def weightCopy(weight: Double): this.type =
    copy(weight = weight).asInstanceOf[this.type]

  override def visualCopy(
                           text: String,
                           internalPoints: List[Point2D],
                           selected: Boolean,
                           labelCenter: Option[Point2D],
                           arrow: LinkArrow,
                           handleRadius: LinkHandleRadius,
                           styleClasses: Set[String]
                         ): FordFulkersonLink.this.type =
    copy(
      internalPoints = internalPoints,
      selected = selected,
      labelCenter = labelCenter,
      styleClasses = styleClasses
    ).asInstanceOf[this.type]
}