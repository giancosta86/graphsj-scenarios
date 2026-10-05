package info.gianlucacosta.graphsj.scenarios.prim

import java.util.UUID

import info.gianlucacosta.eighthbridge.fx.canvas.basic.BasicVertex
import info.gianlucacosta.eighthbridge.graphs.point2point.specific.Named
import info.gianlucacosta.helios.mathutils.Numbers

import scalafx.geometry.{Dimension2D, Point2D}


case class PrimVertex(
                       center: Point2D,
                       styleClasses: List[String] = List(),
                       name: String = "",
                       bestVertex: Option[PrimVertex] = None,
                       distanceFromBestVertex: Option[Double] = None,
                       @transient selected: Boolean = false,
                       id: UUID = UUID.randomUUID()
                     ) extends BasicVertex with Named {
  override val text: String =
    if (bestVertex.nonEmpty)
      s"${name} {${bestVertex.get.name}, ${Numbers.smartString(distanceFromBestVertex.get)}}"
    else
      name

  override def visualCopy(center: Point2D, selected: Boolean): this.type =
    copy(
      center = center,
      selected = selected
    ).asInstanceOf[this.type]


  override def nameCopy(name: String): this.type =
    copy(name = name).asInstanceOf[this.type]
}
