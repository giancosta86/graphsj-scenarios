package info.gianlucacosta.graphsj.scenarios.fordfulkerson

import java.util.UUID

import info.gianlucacosta.eighthbridge.fx.canvas.basic.BasicLink
import info.gianlucacosta.eighthbridge.graphs.point2point.specific.Weighted
import info.gianlucacosta.helios.mathutils.Numbers

import scalafx.geometry.Point2D

//TODO! These links with non-negative weights could be factored out!
case class FordFulkersonLink(weight: Double,
                    styleClasses: List[String] = List(),
                    internalPoints: List[Point2D] = Nil,
                    @transient selected: Boolean = false,
                    labelCenter: Option[Point2D] = None,
                    id: UUID = UUID.randomUUID()
                   ) extends BasicLink[FordFulkersonLink] with Weighted[FordFulkersonLink] {

  val minWeight = 0.0
  val maxWeight = Double.MaxValue

  checkWeight()


  override val text: String = Numbers.smartString(weight)


  override def visualCopy(internalPoints: List[Point2D], selected: Boolean, labelCenter: Option[Point2D]): FordFulkersonLink = {
    copy(
      internalPoints = internalPoints,
      selected = selected,
      labelCenter = labelCenter
    )
  }


  override def weightCopy(weight: Double): FordFulkersonLink =
    copy(weight = weight)
}