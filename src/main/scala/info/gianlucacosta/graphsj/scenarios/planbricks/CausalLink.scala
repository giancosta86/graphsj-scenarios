package info.gianlucacosta.graphsj.scenarios.planbricks

import java.util.UUID

import info.gianlucacosta.lambdaprism.logic.basic.formulas.Literal
import info.gianlucacosta.lambdaprism.logic.basic.matching.Environment

import scalafx.geometry.Point2D


case class CausalLink(
                       effect: Literal,
                       precondition: Literal,
                       matchEnvironment: Environment,
                       threats: Set[StepVertex] = Set(),
                       selected: Boolean = false,
                       internalPoints: List[Point2D] = List(),
                       labelCenter: Option[Point2D] = None,
                       id: UUID = UUID.randomUUID()
                     ) extends PopLink {
  override def text: String = {
    val threatsString =
      if (threats.nonEmpty)
        s"\n\nTHREATS:\n${threats.mkString("\n")}"
      else
        ""

    s"${effect} --> ${precondition}${threatsString}"
  }


  override def visualCopy(internalPoints: List[Point2D], selected: Boolean, labelCenter: Option[Point2D]): CausalLink =
    copy(
      internalPoints = internalPoints,
      selected = selected,
      labelCenter = labelCenter
    )


  override def styleClasses: List[String] =
    if (threats.isEmpty)
      List("causalLink")
    else
      List(
        "causalLink",
        "threatened"
      )
}
