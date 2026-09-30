package info.gianlucacosta.graphsj.scenarios.adgraphplan

import info.gianlucacosta.lambdaprism.planning.problem.Step

import scala.annotation.tailrec
import scalafx.geometry.{Dimension2D, Point2D}


case class PropositionLevel(
                             propositions: List[Proposition],
                             previousStepLevel: Option[StepLevel]
                           ) extends ConstructionLevel[Proposition, PropositionVertex] {

  require(
    propositions.distinct.length == propositions.length,
    "The propositions in a level must be unique for the mutex algorithm to work correctly"
  )

  override def items: List[Proposition] =
    propositions


  override def left: Double =
    previousStepLevel.map(stepLevel => {
      stepLevel.left + stepLevel.width + horizontalSpacing
    }).getOrElse(
      50
    )


  private type PossibleMutexPairs =
  Set[(Proposition, Proposition)]


  private type NonMutexPairs =
  Set[(Proposition, Proposition)]


  override protected def createMutexMap(): Map[Proposition, Set[Proposition]] =
    previousStepLevel match {
      case Some(stepLevel) =>
        val (
          possibleMutexPairs: PossibleMutexPairs,
          nonMutexPairs: NonMutexPairs
          ) = getPossibleMutexPairsAndNonMutexPairs(stepLevel)


        val addDeletePairs: Set[(Proposition, Proposition)] =
          stepLevel.steps.flatMap(step =>
            step.addedEffects.flatMap(addedEffect =>
              step.deletedEffects.flatMap(deletedEffect =>
                List(
                  addedEffect -> deletedEffect,
                  deletedEffect -> addedEffect
                )
              )
            )
          ).toSet

        val mutexPairs: Set[(Proposition, Proposition)] =
          possibleMutexPairs.union(addDeletePairs).diff(nonMutexPairs)

        mutexPairs
          .groupBy(_._1)
          .mapValues(_.map(_._2))


      case None =>
        Map()
    }


  /**
    * @param stepLevel
    * @return
    */
  private def getPossibleMutexPairsAndNonMutexPairs(stepLevel: StepLevel)
  : (PossibleMutexPairs, NonMutexPairs) =
    getPossibleMutexPairsAndNonMutexPairs(
      (Set(), Set()),
      stepLevel.steps,
      stepLevel
    )


  @tailrec
  private def getPossibleMutexPairsAndNonMutexPairs(
                                                     cumulatedResult: (PossibleMutexPairs, NonMutexPairs),
                                                     currentSteps: List[Step],
                                                     stepLevel: StepLevel
                                                   )
  : (PossibleMutexPairs, NonMutexPairs) =
    currentSteps match {
      case List() =>
        cumulatedResult

      case List(singleStep) =>
        (
          cumulatedResult._1,

          getStepNonMutexEffectPairs(singleStep) ++
            cumulatedResult._2
          )


      case firstStep :: tailSteps =>
        val (tailMutexSteps, tailCompatibleSteps) =
          tailSteps.partition(stepLevel.inMutex(_, firstStep))


        val possibleMutexPairs: PossibleMutexPairs =
          firstStep.addedEffects.flatMap(firstStepAddedEffect =>
            tailMutexSteps.flatMap(tailMutexStep =>
              tailMutexStep.addedEffects.flatMap(tailMutexStepAddedEffect =>
                if (firstStepAddedEffect != tailMutexStepAddedEffect)
                  List(
                    firstStepAddedEffect -> tailMutexStepAddedEffect,
                    tailMutexStepAddedEffect -> firstStepAddedEffect
                  )
                else
                  List()
              )
            )
          ).toSet



        val nonMutexPairsForFirstStep: NonMutexPairs =
          getStepNonMutexEffectPairs(firstStep)


        val nonMutexPairsForTailSteps: NonMutexPairs =
          firstStep.addedEffects.flatMap(firstStepAddedEffect =>
            tailCompatibleSteps.flatMap(tailCompatibleStep =>
              tailCompatibleStep.addedEffects.flatMap(tailCompatibleAddedEffect =>
                if (firstStepAddedEffect != tailCompatibleAddedEffect)
                  List(
                    firstStepAddedEffect -> tailCompatibleAddedEffect,
                    tailCompatibleAddedEffect -> firstStepAddedEffect
                  )
                else
                  List()
              )
            )
          ).toSet


        val nonMutexPairs: NonMutexPairs =
          nonMutexPairsForFirstStep ++ nonMutexPairsForTailSteps


        val newCumulatedResult = (
          possibleMutexPairs ++ cumulatedResult._1,
          nonMutexPairs ++ cumulatedResult._2
          )

        getPossibleMutexPairsAndNonMutexPairs(
          newCumulatedResult,
          tailSteps,
          stepLevel
        )
    }


  private def getStepNonMutexEffectPairs(step: Step): NonMutexPairs =
    getStepSafeEffectPairs(
      Set(),
      step.addedEffects
    )


  @tailrec
  private def getStepSafeEffectPairs(
                                      cumulatedResult: NonMutexPairs,
                                      currentAddedEffects: List[Proposition]
                                    ): NonMutexPairs = {
    currentAddedEffects match {
      case List() =>
        cumulatedResult


      case List(singleEffect) =>
        cumulatedResult


      case firstEffect :: tail =>
        val newCumulatedResult: NonMutexPairs =
          tail.flatMap(tailEffect => {
            List(
              firstEffect -> tailEffect,
              tailEffect -> firstEffect
            )
          }).toSet ++ cumulatedResult


        getStepSafeEffectPairs(
          newCumulatedResult,
          tail
        )
    }

  }


  override protected def doCreateVertex(item: Proposition, mutexes: Set[Proposition], size: Dimension2D, center: Point2D): PropositionVertex =
    PropositionVertex(
      item,
      mutexes,
      size,
      center
    )


  override protected def getItemVertexLabel(item: Proposition, mutexes: Set[Proposition]): String =
    PropositionVertex.formatProposition(item, mutexes)
}
