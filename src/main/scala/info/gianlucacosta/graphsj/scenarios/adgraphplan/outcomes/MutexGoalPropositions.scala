package info.gianlucacosta.graphsj.scenarios.adgraphplan.outcomes

import info.gianlucacosta.graphsj.scenarios.adgraphplan.{GraphPlanOutcome, Proposition}

case class MutexGoalPropositions(goalPropositions: Set[Proposition]) extends GraphPlanOutcome {
  require(goalPropositions.size == 2)
}
