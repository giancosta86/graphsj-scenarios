package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.controller.advanced.{CreateNamedVertex, EditNamedVertex}
import info.gianlucacosta.eighthbridge.fx.controller.composite.{NonStructuralEditing, StructuralEditing}
import info.gianlucacosta.eighthbridge.fx.graph.{ArcBarbs, LabeledVisualLink, NamedVisualVertex, VisualGraph, VisualVertex}
import info.gianlucacosta.eighthbridge.fx.{GraphCanvasController, Styles}
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
      with CreateNamedVertex
      with EditNamedVertex {

      override protected def createNamedVertex(center: Point2D, vertexName: String): NamedVisualVertex =
        new NamedVisualVertex

      override def addLink(sourceVertex: VisualVertex, targetVertex: VisualVertex): Unit = {
        graph =
          graph.addArc(
            sourceVertex,
            targetVertex,
            new LabeledVisualLink().setArcBarbs(Some(ArcBarbs.Default)).setLabel("NewDodo")
          )
      }
    }


  override def createRuntimeController(): GraphCanvasController =
    new PrimControllerBase with NonStructuralEditing


  override def createDesignGraph(): VisualGraph =
    new VisualGraph


  override def runStepsBeforePausing: Int =
    0

  override def stylesheets: List[String] =
    List(
      Styles.resourceUrl.toExternalForm,
      getClass.getResource("PrimStyles.css").toExternalForm
    )
}
