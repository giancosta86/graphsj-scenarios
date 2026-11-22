package info.gianlucacosta.graphsj.scenarios.classic

import info.gianlucacosta.eighthbridge.fx.{VisualDirectedGraph, VisualGraph}
import info.gianlucacosta.graphsj.sdk.Scenario

trait DirectedGraphScenario extends Scenario {
  override def createDesignGraph(): VisualGraph =
    new VisualDirectedGraph
}
