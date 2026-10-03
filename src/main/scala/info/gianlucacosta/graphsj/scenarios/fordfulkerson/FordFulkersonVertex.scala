package info.gianlucacosta.graphsj.scenarios.fordfulkerson

import java.util.UUID

import info.gianlucacosta.eighthbridge.fx.canvas.basic.BasicVertex
import info.gianlucacosta.eighthbridge.graphs.point2point.specific.Named
import info.gianlucacosta.helios.mathutils.Numbers

import scalafx.geometry.{Dimension2D, Point2D}


case class FordFulkersonVertex(
                       center: Point2D,
                       styleClasses: List[String] = List(),
                       name: String = "",
                       explored: Option[Boolean] = None,
                       tag: Option[FordFulkersonTag] = None,
                       @transient selected: Boolean = false,
                       id: UUID = UUID.randomUUID()
                     ) extends BasicVertex[FordFulkersonVertex] with Named[FordFulkersonVertex] {

  override val text: String =
    tag.map { tag =>
      val sign = if (tag.plusVk) "+" else "-"

      val deltaString = Numbers.smartString(tag.delta)

      val exploredSuffix = if (explored.get) " @" else ""

      s"${name} [${sign}${tag.vk.name}, ${deltaString}]${exploredSuffix}"
    }
      .getOrElse(name)


  override def visualCopy(center: Point2D, selected: Boolean): FordFulkersonVertex =
    copy(
      center = center,
      selected = selected
    )


  override def nameCopy(name: String): FordFulkersonVertex =
    copy(name = name)
}
