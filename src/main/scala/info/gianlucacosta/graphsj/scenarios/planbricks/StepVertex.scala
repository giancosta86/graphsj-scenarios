package info.gianlucacosta.graphsj.scenarios.planbricks

import info.gianlucacosta.eighthbridge.fx.VisualVertex

import java.util.UUID
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
                     ) extends VisualVertex {


  @transient
  lazy val unsatisfiedPreconditions: Set[Literal] =
    step.preconditions.toSet.diff(satisfiedPreconditions)


  override def text: String =
    step.signature


  override def styleClasses: Set[String] =
    if (isThreat)
      Set("threat")
    else if (unsatisfiedPreconditions.isEmpty)
      Set("satisfiedStep")
    else
      Set()

  override def visualCopy(
                           text: String,
                           center: Point2D,
                           selected: Boolean,
                           styleClasses: Set[String]): StepVertex.this.type =
    copy(
      center = center,
      selected = selected
    ).asInstanceOf[this.type]
}
