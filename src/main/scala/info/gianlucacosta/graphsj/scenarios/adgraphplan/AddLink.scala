package info.gianlucacosta.graphsj.scenarios.adgraphplan

import info.gianlucacosta.eighthbridge.fx.{LinkArrow, LinkHandleRadius}

import java.util.UUID
import scalafx.geometry.Point2D

case class AddLink(
                    internalPoints: List[Point2D] = List(),
                    selected: Boolean = false,
                    labelCenter: Option[Point2D] = None,
                    id: UUID = UUID.randomUUID()
                  ) extends ConstructionLink {


  override val styleClasses: Set[String] =
    Set("addLink")

  override def visualCopy(
                           text: String,
                           internalPoints: List[Point2D],
                           selected: Boolean,
                           labelCenter: Option[Point2D],
                           arrow: LinkArrow,
                           handleRadius: LinkHandleRadius,
                           styleClasses: Set[String]
                         ): AddLink.this.type =
    copy(
      internalPoints = internalPoints,
      selected = selected,
      labelCenter = labelCenter).asInstanceOf[this.type]
}
