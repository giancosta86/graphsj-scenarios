package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.controllers.bundles.{NonStructuralEditing, StructuralEditing}
import info.gianlucacosta.eighthbridge.fx.controllers.creation.CreateVertexWithName
import info.gianlucacosta.eighthbridge.fx.controllers.editing.EditVertexName
import info.gianlucacosta.eighthbridge.fx.{GraphCanvasController, Styles, VisualGraph, VisualUndirectedGraph, VisualVertex}
import info.gianlucacosta.graphsj.scenarios.classic.{ClassicDesignController, ClassicScenario, UndirectedGraphScenario}
import info.gianlucacosta.graphsj.scenarios.classic.design.ClassicDesignVertex
import info.gianlucacosta.graphsj.sdk.{Algorithm, Scenario}
import info.gianlucacosta.helios.desktop.DesktopUtils
import info.gianlucacosta.helios.fx.dialogs.Alerts
import scalafx.geometry.Point2D


object PrimScenario {
  val Name: String =
    "Prim's Shortest Spanning Tree (SST)"
}

class PrimScenario
  extends ClassicScenario
  with UndirectedGraphScenario {
  override val name: String =
    PrimScenario.Name

  override def createAlgorithm(): Algorithm =
    new PrimAlgorithm(verbose)

  override def createDesignController(): GraphCanvasController =
    new PrimDesignController

  override def createRuntimeController(): GraphCanvasController =
    new PrimRuntimeController
}
