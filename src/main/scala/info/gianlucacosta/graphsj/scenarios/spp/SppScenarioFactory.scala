package info.gianlucacosta.graphsj.scenarios.spp

import info.gianlucacosta.graphsj.{Scenario, ScenarioFactory}

class SppScenarioFactory extends ScenarioFactory[SppVertex, SppLink, SppGraph] {
  override def scenarioName: String =
    SppScenario.Name

  override def createScenario: Option[Scenario[SppVertex, SppLink, SppGraph]] =
    Some(new SppScenario)
}
