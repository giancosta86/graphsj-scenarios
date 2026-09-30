package info.gianlucacosta.graphsj.scenarios.adgraphplan

import info.gianlucacosta.graphsj.ScenarioFactory
import info.gianlucacosta.lambdaprism.planning.problem.dialog.ProblemDialog

class GraphPlanFactory extends ScenarioFactory[ConstructionVertex, ConstructionLink, ConstructionGraph] {
  override val scenarioName: String =
    "Add/Delete GraphPlan"

  override def createScenario: Option[GraphPlanScenario] = {
    val problemOption = ProblemDialog.askForProblem(
      None,
      GraphPlanScenario.ProblemValidator
    )

    problemOption.map(problem =>
      new GraphPlanScenario(problem)
    )
  }

}
