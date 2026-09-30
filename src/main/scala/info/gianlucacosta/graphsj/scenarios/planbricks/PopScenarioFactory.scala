package info.gianlucacosta.graphsj.scenarios.planbricks

import info.gianlucacosta.graphsj.ScenarioFactory
import info.gianlucacosta.lambdaprism.planning.problem.dialog.ProblemDialog

class PopScenarioFactory extends ScenarioFactory[StepVertex, PopLink, PopGraph] {
  override def scenarioName: String =
    PopScenario.Name


  override def createScenario: Option[PopScenario] = {
    val problemOption = ProblemDialog.askForProblem(
      None,
      PopScenario.ProblemValidator
    )

    problemOption.map(problem =>
      new PopScenario(problem)
    )
  }
}
