package info.gianlucacosta.graphsj.scenarios.sst

import info.gianlucacosta.graphsj.{Scenario, ScenarioFactory}

class PrimScenarioFactory extends ScenarioFactory[PrimVertex, PrimLink, PrimGraph] {
  override def scenarioName: String =
    PrimScenario.Name

  override def createScenario: Option[Scenario[PrimVertex, PrimLink, PrimGraph]] =
    Some(new PrimScenario)
}
