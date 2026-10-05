package info.gianlucacosta.graphsj.scenarios.adgraphplan

import java.util.UUID

import info.gianlucacosta.lambdaprism.planning.problem.Step

import scalafx.geometry.{Dimension2D, Point2D}


object StepVertex {
  def formatStep(step: Step, mutexes: Set[Step]): String = {
    val mutexString: String =
      if (mutexes.nonEmpty)
        s"\n\n${ConstructionVertex.MutexString}\n\n${mutexes.mkString("\n")}"
      else
        ""
    s"${step}" + mutexString
  }
}


case class StepVertex(
                       step: Step,

                       mutexes: Set[Step],

                       dimension: Dimension2D,

                       center: Point2D,

                       selected: Boolean = false,

                       id: UUID = UUID.randomUUID()
                     ) extends ConstructionVertex {


  override def text: String =
    StepVertex.formatStep(step, mutexes)


  override val styleClasses: List[String] =
    List("stepVertex")


  override def visualCopy(center: Point2D, selected: Boolean): this.type =
    copy(
      center = center,
      selected = selected
    ).asInstanceOf[this.type]
}
