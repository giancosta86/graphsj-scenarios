package info.gianlucacosta.graphsj.scenarios.prim

import java.util.UUID
import info.gianlucacosta.eighthbridge.fx.canvas.basic.BasicLink
import info.gianlucacosta.eighthbridge.graphs.features.Weighted
import info.gianlucacosta.helios.mathutils.Numbers
import scalafx.geometry.Point2D


case class PrimLink(weight: Double,
                    styleClasses: List[String] = List(),
                    internalPoints: List[Point2D] = Nil,
                    @transient selected: Boolean = false,
                    labelCenter: Option[Point2D] = None,
                    id: UUID = UUID.randomUUID()
                   ) extends BasicLink with Weighted {

  val minWeight = 0.0
  val maxWeight = Double.MaxValue

  checkWeight()


  override val text: String = Numbers.smartString(weight)


  override def visualCopy(internalPoints: List[Point2D], selected: Boolean, labelCenter: Option[Point2D]): this.type = {
    copy(
      internalPoints = internalPoints,
      selected = selected,
      labelCenter = labelCenter
    ).asInstanceOf[this.type]
  }


  override def weightCopy(weight: Double): this.type =
    copy(weight = weight).asInstanceOf[this.type]
}