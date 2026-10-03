package info.gianlucacosta.graphsj.scenarios.spp

import java.util.UUID

import info.gianlucacosta.eighthbridge.fx.canvas.basic.BasicVertex
import info.gianlucacosta.eighthbridge.graphs.point2point.specific.Named
import info.gianlucacosta.helios.mathutils.Numbers

import scalafx.geometry.{Dimension2D, Point2D}


object SppVertex {
  val FontDimension = new Dimension2D(12, 19)
}

case class SppVertex(
                       center: Point2D,
                       styleClasses: List[String] = List(),
                       name: String = "",
                       previousVertex: Option[SppVertex] = None,
                       pathLength: Option[Double] = None,
                       @transient selected: Boolean = false,
                       id: UUID = UUID.randomUUID()
                     ) extends BasicVertex[SppVertex] with Named[SppVertex] {
  override val text: String =
    if (previousVertex.nonEmpty)
      s"${name} {${previousVertex.get.name}, ${Numbers.smartString(pathLength.get)}}"
    else
      name


  override def visualCopy(center: Point2D, selected: Boolean): SppVertex =
    copy(
      center = center,
      selected = selected
    )


  override def nameCopy(name: String): SppVertex =
    copy(name = name)
}
