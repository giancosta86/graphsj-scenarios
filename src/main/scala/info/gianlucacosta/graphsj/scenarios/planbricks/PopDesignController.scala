package info.gianlucacosta.graphsj.scenarios.planbricks

import info.gianlucacosta.eighthbridge.fx.canvas.GraphCanvas
import info.gianlucacosta.eighthbridge.fx.canvas.basic.DragDropController
import info.gianlucacosta.eighthbridge.graphs.CircularGraphException
import info.gianlucacosta.helios.fx.dialogs.{Alerts, InputDialogs}
import info.gianlucacosta.helios.fx.geometry.DiagonalBounds
import info.gianlucacosta.helios.fx.Includes._
import info.gianlucacosta.lambdaprism.logic.basic.formulas.{Argument, Literal, Variable}
import info.gianlucacosta.lambdaprism.logic.basic.matching.{Environment, SymbolicMatch}
import info.gianlucacosta.lambdaprism.planning.problem.{Action, Problem}

import scala.annotation.tailrec
import scalafx.geometry.Point2D

class PopDesignController(problem: Problem)
  extends DragDropController[StepVertex, PopLink, PopGraph](true)
    with PopController {

  private case class CausalMatch(
                                  effect: Literal,
                                  precondition: Literal,
                                  matchEnvironment: Environment
                                ) {
    override def toString: String =
      s"${effect} --> ${precondition}"
  }


  override def createVertex(graph: PopGraph, center: Point2D): Option[PopGraph] = {
    val actionOption =
      InputDialogs.askForItem(
        "Please, choose an action:",
        problem.mainActions
      )


    actionOption.flatMap(action => {
      val bindingsOption =
        askForBindings(action)


      bindingsOption.map(bindings => {
        val step =
          action.reify(bindings)

        val stepVertex =
          new StepVertex(step, center)

        graph
          .addVertex(stepVertex)
          .makeConsistent()
      })
    })
  }


  private def askForBindings(action: Action): Option[Map[Variable, Argument]] =
    askForBindings(
      Map(),
      action.variables.toList.sorted
    )


  @tailrec
  private def askForBindings(cumulatedBindings: Map[Variable, Argument], unboundVariables: List[Variable])
  : Option[Map[Variable, Argument]] = {
    unboundVariables match {
      case List() =>
        Some(cumulatedBindings)


      case headUnboundVariable :: tailUnboundVariables =>
        val headBindingOption =
          askForBinding(headUnboundVariable)

        headBindingOption match {
          case Some(headBinding) =>
            askForBindings(
              cumulatedBindings + headBinding,
              tailUnboundVariables
            )

          case None =>
            None
        }
    }
  }


  @tailrec
  private def askForBinding(variable: Variable): Option[(Variable, Argument)] = {
    val argumentTokenOption =
      InputDialogs.askForString(
        s"Value (constant or variable) for ${variable}:",
        variable.name
      )


    try {
      argumentTokenOption.map(argumentToken => {
        variable -> Argument.parse(argumentToken)
      })
    } catch {
      case _: IllegalArgumentException =>
        askForBinding(variable)
    }
  }


  override def createLink(graph: PopGraph, sourceVertex: StepVertex, targetVertex: StepVertex): Option[PopGraph] = {
    if (sourceVertex == graph.goalVertex) {
      Alerts.showWarning("The goal vertex cannot be the source of an arc!")
      None
    } else if (targetVertex == graph.startVertex) {
      Alerts.showWarning("The start vertex cannot be the target of an arc!")
      None
    } else {

      val canCreateTemporalLink =
        graph
          .getArcsBetween(sourceVertex, targetVertex)
          .isEmpty


      val virtualMatches: List[CausalMatch] =
        sourceVertex.step.effects.flatMap(effect => {
          targetVertex.unsatisfiedPreconditions.map(precondition => {
            SymbolicMatch.unify(
              effect,
              precondition
            ).map(symbolicMatch =>
              CausalMatch(
                effect,
                precondition,
                symbolicMatch.environment
              )
            )
          })
            .filter(_.nonEmpty)
            .map(_.get)
        })


      val compatibleMatches: List[CausalMatch] =
        virtualMatches
          .filter(virtualMatch =>
            virtualMatch.matchEnvironment.isCompatibleWith(
              graph.environment
            )
          )
          .sortBy(compatibleMatch =>
            compatibleMatch.effect.toString -> compatibleMatch.precondition.toString
          )


      val canCreateCausalLink =
        compatibleMatches.nonEmpty


      val linkTypeOption: Option[PopLinkType] =
        if (canCreateCausalLink && canCreateTemporalLink) {
          InputDialogs.askForItem("Link type:", PopLinkType.All)
        } else if (canCreateCausalLink) {
          Some(PopLinkType.CausalLink)
        } else if (canCreateTemporalLink) {
          Some(PopLinkType.TemporalLink)
        } else {
          Alerts.showWarning("Neither causal links nor temporal links can be created between these steps")
          None
        }


      linkTypeOption match {
        case Some(linkType) =>
          val internalPoint =
            new DiagonalBounds(
              sourceVertex.center,
              targetVertex.center
            )
              .centerPoint2D


          try {
            linkType match {
              case PopLinkType.TemporalLink =>
                val temporalLink =
                  new TemporalLink(
                    internalPoints = List(internalPoint)
                  )

                Some(
                  graph.addLink(
                    sourceVertex,
                    targetVertex,
                    temporalLink
                  )
                    .makeConsistent()
                )


              case PopLinkType.CausalLink =>

                val selectedMatchOption: Option[CausalMatch] =
                  compatibleMatches match {
                    case List(singleMatch) =>
                      Some(singleMatch)

                    case _ =>
                      InputDialogs.askForItem(
                        "Please, select a match:",
                        compatibleMatches
                      )
                  }

                selectedMatchOption.flatMap(selectedMatch => {
                  val existingTemporalLinkOption =
                    graph
                      .getArcsBetween(sourceVertex, targetVertex)
                      .find(arc => arc.isInstanceOf[TemporalLink])


                  val tempGraph: PopGraph =
                    existingTemporalLinkOption.map(existingTemporalLink =>
                      graph.removeLink(existingTemporalLink)
                    ).getOrElse(
                      graph
                    )


                  val causalLink =
                    new CausalLink(
                      selectedMatch.effect,
                      selectedMatch.precondition,
                      selectedMatch.matchEnvironment,
                      internalPoints = List(internalPoint)
                    )

                  Some(
                    tempGraph
                      .addLink(
                        sourceVertex,
                        targetVertex,
                        causalLink
                      )
                      .makeConsistent()
                  )
                })
            }
          } catch {
            case _: CircularGraphException =>
              Alerts.showWarning("The graph cannot contain cycles")
              None
          }

        case None =>
          None
      }
    }
  }


  override def deleteSelection(graphCanvas: GraphCanvas[StepVertex, PopLink, PopGraph], graph: PopGraph): Option[PopGraph] = {
    Some(
      graph
        .removeLinks(
          graph.selectedLinks -- graph.reservedLinks
        )

        .removeVertexes(
          graph.selectedVertexes -- graph.reservedVertexes
        )

        .makeConsistent()
    )
  }
}
