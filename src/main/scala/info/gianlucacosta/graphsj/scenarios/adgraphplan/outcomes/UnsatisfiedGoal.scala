package info.gianlucacosta.graphsj.scenarios.adgraphplan.outcomes

import info.gianlucacosta.graphsj.scenarios.adgraphplan.GraphPlanOutcome
import info.gianlucacosta.lambdaprism.logic.basic.formulas.Literal

case class UnsatisfiedGoal(goal: Literal) extends GraphPlanOutcome