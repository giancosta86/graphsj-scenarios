package info.gianlucacosta.graphsj.scenarios.basicdecisiontree

import info.gianlucacosta.graphsj.{Scenario, ScenarioFactory}
import info.gianlucacosta.lambdaprism.classification.basic.ClassificationProblemDialog

class DecisionTreeScenarioFactory extends ScenarioFactory[DecisionTreeVertex, DecisionTreeLink, DecisionTreeGraph] {
  override def scenarioName: String =
    DecisionTreeScenario.Name

  override def createScenario: Option[Scenario[DecisionTreeVertex, DecisionTreeLink, DecisionTreeGraph]] = {
    val problemOption = ClassificationProblemDialog.askForProblem(
      None
    )

    problemOption.map(problem =>
      new DecisionTreeScenario(problem)
    )
  }
}
