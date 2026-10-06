package info.gianlucacosta.graphsj.scenarios.spp

import info.gianlucacosta.eighthbridge.fx.VisualVertex

import java.util.UUID
import info.gianlucacosta.eighthbridge.graphs.features.Named
import info.gianlucacosta.helios.mathutils.Numbers
import scalafx.geometry.{Dimension2D, Point2D}


case class SppVertex(
                       @transient previousVertex: Option[SppVertex] = None,
                       @transient pathLength: Option[Double] = None,
                       center: Point2D,
                       name: String = "",
                       selected: Boolean = false,
                       styleClasses: Set[String] = Set(),
                       id: UUID = UUID.randomUUID()
                     ) extends VisualVertex with Named {
  override val text: String =
    if (previousVertex.nonEmpty)
      s"${name} {${previousVertex.get.name}, ${Numbers.smartString(pathLength.get)}}"
    else
      name

  override def setName(name: String): this.type =
    copy(name = name).asInstanceOf[this.type]

  override def visualCopy(
                           text: String,
                           center: Point2D,
                           selected: Boolean,
                           styleClasses: Set[String]
                         ): SppVertex.this.type =
    copy(
      center = center,
      selected = selected,
      styleClasses = styleClasses
    ).asInstanceOf[this.type]
}
