package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.controllers.bundles.{NonStructuralEditing, StructuralEditing}
import info.gianlucacosta.eighthbridge.fx.controllers.creation.CreateVertexWithName
import info.gianlucacosta.eighthbridge.fx.controllers.editing.EditVertexName
import info.gianlucacosta.eighthbridge.fx.{GraphCanvasController, Styles, VisualGraph, VisualUndirectedGraph, VisualVertex}
import info.gianlucacosta.graphsj.scenarios.classic.design.ClassicDesignVertex
import info.gianlucacosta.graphsj.sdk.{Algorithm, Scenario}
import info.gianlucacosta.helios.desktop.DesktopUtils
import info.gianlucacosta.helios.fx.dialogs.Alerts
import scalafx.geometry.Point2D


object PrimScenario {
  val Name: String =
    "Prim's Shortest Spanning Tree (SST)"

  private val WebsiteUrl =
    "https://github.com/giancosta86/graphsj-scenarios"
}

class PrimScenario extends Scenario {
  override val name: String =
    PrimScenario.Name

  override def showHelp(): Unit = {
    DesktopUtils.openBrowser(PrimScenario.WebsiteUrl)
  }

  override def showSettings(designGraph: VisualGraph): Option[VisualGraph] = {
    Alerts.showInfo("No settings available for this scenario.")

    None
  }

  override def createAlgorithm(): Algorithm =
    new PrimAlgorithm

  override def createDesignController(): GraphCanvasController =
    new PrimControllerBase with StructuralEditing
      with CreateVertexWithName
      with EditVertexName {

      override protected def createNamedVertex(center: Point2D, vertexName: String): ClassicDesignVertex =
        new PrimVertex()
          .setCenter(center)
          .setName(vertexName)

      override def addLink(sourceVertex: VisualVertex, targetVertex: VisualVertex): Unit = {
        graph =
          graph.addLink(
            sourceVertex,
            targetVertex,
            new PrimEdge()
              .setLabel("FacetedDodo")
          )
      }
    }

  override def createRuntimeController(): GraphCanvasController =
    new PrimControllerBase with NonStructuralEditing

  override def createDesignGraph(): VisualGraph =
    new VisualUndirectedGraph

  override def runStepsBeforePausing: Int =
    0

  override def stylesheets: List[String] =
    List(
      Styles.resourceUrl.toExternalForm,
      getClass.getResource("PrimStyles.css").toExternalForm
    )
}
