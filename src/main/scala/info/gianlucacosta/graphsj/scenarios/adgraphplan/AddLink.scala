package info.gianlucacosta.graphsj.scenarios.adgraphplan

import java.util.UUID

import scalafx.geometry.Point2D

case class AddLink(
                    internalPoints: List[Point2D] = List(),
                    selected: Boolean = false,
                    labelCenter: Option[Point2D] = None,
                    id: UUID = UUID.randomUUID()
                  ) extends ConstructionLink {


  override val styleClasses: List[String] =
    List("addLink")

  override def visualCopy(internalPoints: List[Point2D], selected: Boolean, labelCenter: Option[Point2D]): AddLink =
    copy(
      internalPoints = internalPoints,
      selected = selected,
      labelCenter = labelCenter)
}
