package info.gianlucacosta.graphsj.scenarios.adgraphplan

import java.util.UUID

import scalafx.geometry.Point2D

case class DeleteLink(
                       internalPoints: List[Point2D] = List(),
                       selected: Boolean = false,
                       labelCenter: Option[Point2D] = None,
                       id: UUID = UUID.randomUUID()
                     ) extends ConstructionLink {


  override val styleClasses: List[String] =
    List("deleteLink")


  override def visualCopy(internalPoints: List[Point2D], selected: Boolean, labelCenter: Option[Point2D]): DeleteLink =
    copy(
      internalPoints = internalPoints,
      selected = selected,
      labelCenter = labelCenter)
}