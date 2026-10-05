package info.gianlucacosta.graphsj.scenarios.planbricks

import java.util.UUID

import info.gianlucacosta.eighthbridge.fx.canvas.basic.BasicVertex
import info.gianlucacosta.lambdaprism.logic.basic.formulas.Literal
import info.gianlucacosta.lambdaprism.planning.problem.Step

import scalafx.geometry.Point2D

case class StepVertex(
                       step: Step,
                       center: Point2D,
                       satisfiedPreconditions: Set[Literal] = Set(),
                       isThreat: Boolean = false,
                       selected: Boolean = false,
                       id: UUID = UUID.randomUUID()
                     ) extends BasicVertex {


  @transient
  lazy val unsatisfiedPreconditions: Set[Literal] =
    step.preconditions.toSet.diff(satisfiedPreconditions)


  override def text: String =
    step.signature


  override def visualCopy(center: Point2D, selected: Boolean): this.type =
    copy(
      center = center,
      selected = selected
    ).asInstanceOf[this.type]

  override def styleClasses: List[String] =
    if (isThreat)
      List("threat")
    else if (unsatisfiedPreconditions.isEmpty)
      List("satisfiedStep")
    else
      List()
}
