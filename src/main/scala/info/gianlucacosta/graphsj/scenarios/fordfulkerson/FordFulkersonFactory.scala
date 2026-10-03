package info.gianlucacosta.graphsj.scenarios.fordfulkerson

import info.gianlucacosta.graphsj.{Scenario, ScenarioFactory}

class FordFulkersonScenarioFactory extends ScenarioFactory[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph] {
  override def scenarioName: String =
    FordFulkersonScenario.Name

  override def createScenario: Option[Scenario[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph]] =
    Some(new FordFulkersonScenario)
}
