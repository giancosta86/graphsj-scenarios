package info.gianlucacosta.graphsj.scenarios.fordfulkerson

import info.gianlucacosta.eighthbridge.fx.VisualVertex

import java.util.UUID
import info.gianlucacosta.eighthbridge.graphs.features.Named
import info.gianlucacosta.helios.mathutils.Numbers
import scalafx.geometry.Point2D


case class FordFulkersonVertex(
                       explored: Option[Boolean] = None,
                       tag: Option[FordFulkersonTag] = None,
                       center: Point2D,
                       styleClasses: Set[String] = Set(),
                       name: String = "",
                       @transient selected: Boolean = false,
                       id: UUID = UUID.randomUUID()
                     ) extends VisualVertex with Named {

  override val text: String =
    tag.map { tag =>
      val sign = if (tag.plusVk) "+" else "-"

      val deltaString = Numbers.smartString(tag.delta)

      val exploredSuffix = if (explored.get) " @" else ""
      //TODO! Revise the logic for this label!
      s"${name} [${sign}${tag.vk.map(_.name).getOrElse("ø")}, ${deltaString}]${exploredSuffix}"
    }
      .getOrElse(name)


  override def setName(name: String): this.type =
    copy(name = name).asInstanceOf[this.type]

  override def visualCopy(
                           text: String,
                           center: Point2D,
                           selected: Boolean,
                           styleClasses: Set[String]
                         ): FordFulkersonVertex.this.type =
    copy(
      center = center,
      selected = selected,
      styleClasses = styleClasses
    ).asInstanceOf[this.type]
}
