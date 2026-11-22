package info.gianlucacosta.graphsj.scenarios.classic

import info.gianlucacosta.eighthbridge.fx.{GraphCanvasController, VisualGraph, VisualUndirectedGraph}
import info.gianlucacosta.graphsj.sdk.Scenario

trait UndirectedGraphScenario extends Scenario {
  override def createDesignGraph(): VisualGraph =
    new VisualUndirectedGraph
}
