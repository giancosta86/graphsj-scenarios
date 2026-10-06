package info.gianlucacosta.graphsj.scenarios.planbricks

import info.gianlucacosta.eighthbridge.fx.{LinkArrow, LinkHandleRadius}

import java.util.UUID
import scalafx.geometry.Point2D

case class TemporalLink(
                         selected: Boolean = false,
                         internalPoints: List[Point2D] = List(),
                         labelCenter: Option[Point2D] = None,
                         id: UUID = UUID.randomUUID()
                       ) extends PopLink {
  override def text: String =
    ""

  override def styleClasses: Set[String] =
    Set("temporalLink")

  override def visualCopy(
                           text: String,
                           internalPoints: List[Point2D],
                           selected: Boolean,
                           labelCenter: Option[Point2D],
                           arrow: LinkArrow,
                           handleRadius: LinkHandleRadius,
                           styleClasses: Set[String]
                         ): TemporalLink.this.type =
    copy(
      internalPoints = internalPoints,
      selected = selected,
      labelCenter = labelCenter
    ).asInstanceOf[this.type]
}
