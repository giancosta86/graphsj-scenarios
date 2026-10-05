package info.gianlucacosta.graphsj.scenarios.planbricks

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

  override def visualCopy(internalPoints: List[Point2D], selected: Boolean, labelCenter: Option[Point2D]): this.type =
    copy(
      internalPoints = internalPoints,
      selected = selected,
      labelCenter = labelCenter
    ).asInstanceOf[this.type]

  override def styleClasses: List[String] =
    List("temporalLink")
}
