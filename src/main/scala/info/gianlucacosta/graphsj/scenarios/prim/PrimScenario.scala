package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.controller.{LayoutEditing, Undirected}
import info.gianlucacosta.eighthbridge.fx.graph.VisualGraph
import info.gianlucacosta.eighthbridge.fx.{GraphCanvasController, Styles}
import info.gianlucacosta.graphsj.sdk.{Algorithm, Scenario}
import info.gianlucacosta.helios.desktop.DesktopUtils
import info.gianlucacosta.helios.fx.dialogs.Alerts


object PrimScenario {
  val Name: String =
    "Prim's Shortest Spanning Tree (SST)"

  private val WebsiteUrl =
    "https://github.com/giancosta86/graphsj-scenarios"
}


class PrimScenario extends Scenario[PrimVertex, PrimEdge, PrimGraph] {
  override val name: String =
    PrimScenario.Name


  override def showHelp(): Unit = {
    DesktopUtils.openBrowser(PrimScenario.WebsiteUrl)
  }


  override def showSettings(designGraph: PrimGraph): Option[PrimGraph] = {
    Alerts.showInfo("No settings available for this scenario.")

    None
  }


  override def createAlgorithm(): Algorithm[PrimVertex, PrimEdge, PrimGraph] =
    new PrimAlgorithm


  override def createDesignController(): GraphCanvasController =
    new PrimDesignController


  override def createRuntimeController(): GraphCanvasController =
    new LayoutEditing
      with Undirected


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
