package info.gianlucacosta.graphsj.scenarios.adgraphplan

import info.gianlucacosta.lambdaprism.planning.problem.Step

import scala.annotation.tailrec
import scalafx.geometry.{Dimension2D, Point2D}

case class StepLevel(
                      steps: List[Step],
                      previousPropositionLevel: PropositionLevel
                    ) extends ConstructionLevel[Step, StepVertex] {

  require(
    steps.distinct.length == steps.length,
    "The steps in a level must be unique for the mutex algorithm to work correctly"
  )


  override def items: List[Step] =
    steps


  override def left: Double =
    previousPropositionLevel.left + previousPropositionLevel.width + horizontalSpacing


  override protected def createMutexMap(): Map[Step, Set[Step]] =
    createMutexPairs(Set(), steps)
      .groupBy(_._1)
      .mapValues(_.map(_._2))


  @tailrec
  private def createMutexPairs(cumulatedResult: Set[(Step, Step)], currentSteps: List[Step]): Set[(Step, Step)] =
    currentSteps match {
      case List() =>
        cumulatedResult


      case List(singleStep) =>
        cumulatedResult


      case firstStep :: tailSteps =>
        val firstStepPreconditions =
          firstStep.preconditions.toSet

        val firstStepAddedEffects =
          firstStep.addedEffects.toSet

        val firstStepDeletedEffects =
          firstStep.deletedEffects.toSet


        val tailStepsIncompatibleWithFirstStep: Set[(Step, Step)] =
          tailSteps.flatMap(tailStep => {
            val tailStepPreconditions =
              tailStep.preconditions.toSet

            val tailStepAddedEffects =
              tailStep.addedEffects.toSet

            val tailStepDeletedEffects =
              tailStep.deletedEffects.toSet


            def incompatibleEffects: Boolean =
              firstStepAddedEffects.intersect(tailStepDeletedEffects).nonEmpty ||
                tailStepAddedEffects.intersect(firstStepDeletedEffects).nonEmpty


            def inconsistency: Boolean =
              firstStepDeletedEffects.intersect(tailStepPreconditions).nonEmpty ||
                tailStepDeletedEffects.intersect(firstStepPreconditions).nonEmpty


            def mutexPreconditions: Boolean =
              firstStepPreconditions.exists(firstStepPrecondition =>
                tailStepPreconditions.exists(tailStepPrecondition =>
                  previousPropositionLevel.inMutex(firstStepPrecondition, tailStepPrecondition)
                )
              )


            if (incompatibleEffects || inconsistency || mutexPreconditions)
              List(
                firstStep -> tailStep,
                tailStep -> firstStep
              )
            else
              List()
          }).toSet


        createMutexPairs(
          tailStepsIncompatibleWithFirstStep ++ cumulatedResult,
          tailSteps
        )
    }


  override protected def doCreateVertex(item: Step, mutexes: Set[Step], size: Dimension2D, center: Point2D): StepVertex =
    StepVertex(
      item,
      mutexes,
      size,
      center
    )


  override protected def getItemVertexLabel(item: Step, mutexes: Set[Step]): String =
    StepVertex.formatStep(item, mutexes)
}
