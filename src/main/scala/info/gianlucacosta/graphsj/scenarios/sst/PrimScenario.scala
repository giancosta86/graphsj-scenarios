package info.gianlucacosta.graphsj.scenarios.sst

import info.gianlucacosta.eighthbridge.fx.canvas.GraphCanvasController
import info.gianlucacosta.eighthbridge.fx.canvas.basic.{BasicStyles, DragDropController}
import info.gianlucacosta.graphsj.{Algorithm, Scenario}
import info.gianlucacosta.helios.desktop.DesktopUtils
import info.gianlucacosta.helios.fx.dialogs.Alerts


object PrimScenario {
  val Name: String =
    "Prim's Shortest Spanning Tree (SST)"

  private val WebsiteUrl =
    "https://github.com/giancosta86/graphsj-scenarios"
}


class PrimScenario extends Scenario[PrimVertex, PrimLink, PrimGraph] {
  override val name: String =
    PrimScenario.Name


  override def showHelp(): Unit = {
    DesktopUtils.openBrowser(PrimScenario.WebsiteUrl)
  }


  override def showSettings(designGraph: PrimGraph): Option[PrimGraph] = {
    Alerts.showInfo("No settings available for this scenario.")

    None
  }


  override def createAlgorithm(): Algorithm[PrimVertex, PrimLink, PrimGraph] =
    new PrimAlgorithm[PrimGraph]


  override def createDesignController(): GraphCanvasController[PrimVertex, PrimLink, PrimGraph] =
    new PrimDesignController[PrimGraph]


  override def createRuntimeController(): GraphCanvasController[PrimVertex, PrimLink, PrimGraph] =
    new DragDropController[PrimVertex, PrimLink, PrimGraph](false)


  override def createDesignGraph(): PrimGraph =
    new PrimGraph


  override def runStepsBeforePausing: Int =
    0

  override def stylesheets: List[String] =
    List(
      BasicStyles.resourceUrl.toExternalForm,
      getClass.getResource("PrimStyles.css").toExternalForm
    )
}
