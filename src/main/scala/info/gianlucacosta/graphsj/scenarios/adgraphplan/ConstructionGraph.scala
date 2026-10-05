package info.gianlucacosta.graphsj.scenarios.adgraphplan

import java.util.UUID

import info.gianlucacosta.eighthbridge.graphs.point2point.ArcBinding
import info.gianlucacosta.eighthbridge.graphs.point2point.visual.VisualGraph
import info.gianlucacosta.lambdaprism.logic.basic.formulas.Literal
import info.gianlucacosta.lambdaprism.logic.basic.matching.{Environment, SymbolicMatch}
import info.gianlucacosta.lambdaprism.planning.problem._

import scala.collection.mutable
import scalafx.geometry.{BoundingBox, Bounds}

object ConstructionGraph {
  def apply(problem: Problem): ConstructionGraph = {
    val propositionLevel =
      PropositionLevel(problem.startLiterals, None)

    ConstructionGraph(
      problem,
      List(propositionLevel),
      List(),
      vertexes = propositionLevel.vertexes.toSet
    )
  }
}


case class ConstructionGraph private(
                                      problem: Problem,
                                      propositionLevels: List[PropositionLevel],
                                      stepLevels: List[StepLevel],
                                      selectionBounds: Bounds = new BoundingBox(0, 0, 0, 0),
                                      vertexes: Set[ConstructionVertex] = Set(),
                                      links: Set[ConstructionLink] = Set(),
                                      bindings: Set[ArcBinding] = Set()
                                    ) extends VisualGraph[ConstructionVertex, ConstructionLink] {

  require(propositionLevels.size == stepLevels.size + 1)


  override protected def graphCopy(vertexes: Set[ConstructionVertex], links: Set[ConstructionLink], bindings: Set[ArcBinding]): this.type =
    copy(
      vertexes = vertexes,
      links = links,
      bindings = bindings
    ).asInstanceOf[this.type]


  def advanceSteps(): Option[ConstructionGraph] = {
    val latestStepLevelOption =
      stepLevels.lastOption


    val latestPropositionLevel =
      propositionLevels.last


    val nextMainSteps =
      problem
        .mainActions
        .flatMap(mainAction =>
          createNextMainSteps(mainAction, latestPropositionLevel)
        )


    val nextNopSteps =
      createNextNopSteps(latestPropositionLevel)


    val nextSteps =
      nextMainSteps ++ nextNopSteps


    val nextStepLevel = StepLevel(
      nextSteps,

      latestPropositionLevel
    )


    val nextPropositions =
      createNextPropositions(nextStepLevel)


    val nextPropositionLevel = PropositionLevel(
      nextPropositions,
      Some(nextStepLevel)
    )


    val isStepLevelDuplicated =
      latestStepLevelOption.exists(latestStepLevel =>
        nextStepLevel.duplicates(latestStepLevel))


    val isPropositionLevelDuplicated =
      nextPropositionLevel.duplicates(latestPropositionLevel)


    val isGraphRepeating =
      isStepLevelDuplicated && isPropositionLevelDuplicated


    if (isGraphRepeating)
      None
    else {
      val newVertexes =
        nextStepLevel.vertexes ++ nextPropositionLevel.vertexes


      val (newLinks, newBindings) = nextSteps.flatMap(step => {
        val stepVertex =
          nextStepLevel.getVertex(step)

        val preconditionComponents = step.preconditions.map(precondition => {
          val preconditionVertex =
            latestPropositionLevel.getVertex(precondition)

          val preconditionLink =
            PreconditionLink()

          val addBinding = ArcBinding(
            UUID.randomUUID(),
            preconditionVertex.id,
            stepVertex.id,
            preconditionLink.id
          )

          preconditionLink -> addBinding
        })


        val addComponents = step.addedEffects.map(addedEffect => {
          val effectVertex =
            nextPropositionLevel.getVertex(addedEffect)

          val addLink =
            AddLink()

          val addBinding = ArcBinding(
            UUID.randomUUID(),
            stepVertex.id,
            effectVertex.id,
            addLink.id
          )

          addLink -> addBinding
        })


        val deleteComponents = step.deletedEffects.map(deletedEffect => {
          val effectVertex =
            nextPropositionLevel.getVertex(deletedEffect)

          val deleteLink =
            DeleteLink()

          val addBinding = ArcBinding(
            UUID.randomUUID(),
            stepVertex.id,
            effectVertex.id,
            deleteLink.id
          )

          deleteLink -> addBinding
        })


        preconditionComponents ++ addComponents ++ deleteComponents
      }).unzip


      Some(
        new ConstructionGraph(
          problem,
          propositionLevels :+ nextPropositionLevel,
          stepLevels :+ nextStepLevel,
          vertexes = vertexes ++ newVertexes,
          links = links ++ newLinks,
          bindings = bindings ++ newBindings
        )
      )
    }
  }


  private def createNextMainSteps(action: MainAction, latestPropositionLevel: PropositionLevel): List[Step] =
    createNextMainSteps(
      action,
      latestPropositionLevel,
      Set(),
      new Environment,
      action.preconditions
    )


  private def createNextMainSteps(
                                   action: MainAction,
                                   latestPropositionLevel: PropositionLevel,
                                   cumulatedPreconditions: Set[Literal],
                                   cumulatedEnvironment: Environment,
                                   preconditionsToSatisfy: List[Literal]
                                 ): List[Step] =
    if (preconditionsToSatisfy.isEmpty) {
      try {
        List(
          action.reify(cumulatedEnvironment.boundVariables)
        )
      } catch {
        case ex: IllegalArgumentException =>
          List()
      }
    } else {
      val currentPrecondition =
        preconditionsToSatisfy.head

      latestPropositionLevel
        .propositions
        .map(proposition =>
          SymbolicMatch.unify(proposition, currentPrecondition)
        )
        .filter(
          _.nonEmpty
        )
        .map(newMatchOption =>
          newMatchOption.get
        )
        .map(newMatch =>
          newMatch -> (cumulatedEnvironment ++ newMatch.environment)
        )
        .filter {
          case (newMatch, newCumulatedEnvironmentOption) =>
            newCumulatedEnvironmentOption.nonEmpty
        }
        .map {
          case (newMatch, newCumulatedEnvironmentOption) =>
            newMatch -> newCumulatedEnvironmentOption.get
        }
        .flatMap {
          case (newMatch, newCumulatedEnvironment) =>
            val newGroundPrecondition =
              newMatch.left

            if (cumulatedPreconditions.exists(
              latestPropositionLevel.inMutex(_, newGroundPrecondition)
            )) {
              List()
            } else {
              createNextMainSteps(
                action,
                latestPropositionLevel,
                cumulatedPreconditions + newGroundPrecondition,
                newCumulatedEnvironment,
                preconditionsToSatisfy.tail
              )
            }
        }
    }

  private def createNextNopSteps(latestPropositionLevel: PropositionLevel): List[Step] =
    latestPropositionLevel.propositions.map(proposition => {
      val nopAction = new NopAction(proposition)
      nopAction.reify(Map())
    })


  private def createNextPropositions(latestStepLevel: StepLevel): List[Proposition] = {
    val result = mutable.LinkedHashSet[Proposition]()

    latestStepLevel.items.foreach(step => {
      step.addedEffects.foreach(result.add)

      step.deletedEffects.foreach(result.add)
    })

    result.toList
  }
}
