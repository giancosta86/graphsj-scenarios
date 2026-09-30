package info.gianlucacosta.graphsj.scenarios.adgraphplan

import info.gianlucacosta.graphsj.scenarios.adgraphplan.outcomes.{FeasiblePlan, MutexGoalPropositions, UnsatisfiedGoal}
import info.gianlucacosta.graphsj.{Algorithm, OutputConsole}
import info.gianlucacosta.lambdaprism.planning.problem.Problem


object GraphPlanAlgorithm {
  def evaluateOutcome(problem: Problem, finalGraph: ConstructionGraph): GraphPlanOutcome = {
    val lastPropositionLevel = finalGraph.propositionLevels.last
    val lastPropositions = lastPropositionLevel.propositions


    val unsatisfiedGoalOption = problem.goalLiterals.find(goalLiteral =>
      !lastPropositions.contains(goalLiteral)
    )


    unsatisfiedGoalOption match {
      case Some(unsatisfiedGoal) =>
        UnsatisfiedGoal(unsatisfiedGoal)


      case None =>
        val goalPropositions =
          lastPropositions
            .filter(problem.goalLiterals.contains)


        val goalPropositionPairs: List[(Proposition, Proposition)] =
          goalPropositions.flatMap(leftGoal =>
            goalPropositions.map(rightGoal =>
              leftGoal -> rightGoal
            )
          )


        val mutexGoalPropositionsOption =
          goalPropositionPairs
            .find(goalPair =>
              lastPropositionLevel.inMutex(goalPair._1, goalPair._2)
            )

        mutexGoalPropositionsOption match {
          case Some(mutexGoalPropositions) =>
            MutexGoalPropositions(Set(
              mutexGoalPropositions._1,
              mutexGoalPropositions._2
            ))

          case None =>
            FeasiblePlan
        }
    }
  }
}


class GraphPlanAlgorithm(problem: Problem) extends Algorithm[ConstructionVertex, ConstructionLink, ConstructionGraph] {
  override def runStep(stepIndex: Int, graph: ConstructionGraph, console: OutputConsole): (ConstructionGraph, Boolean) = {
    graph.advanceSteps() match {
      case Some(newGraph) =>
        (newGraph, true)

      case None =>
        GraphPlanAlgorithm.evaluateOutcome(problem, graph) match {
          case FeasiblePlan =>
            console.writeln("The problem has a feasible solution plan!")

          case UnsatisfiedGoal(unsatisfiedGoal) =>
            console.writeln(s"There is no feasible plan. Unsatisfied goal: ${unsatisfiedGoal}")

          case MutexGoalPropositions(mutexGoalPropositions) =>
            console.writeln(s"There is no feasible plan because of conflicting goal propositions - in particular: ${mutexGoalPropositions}")
        }

        (graph, false)
    }
  }
}
