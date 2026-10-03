package info.gianlucacosta.graphsj.scenarios.fordfulkerson

import info.gianlucacosta.eighthbridge.fx.canvas.GraphCanvasController
import info.gianlucacosta.eighthbridge.fx.canvas.basic.{BasicStyles, DragDropController}
import info.gianlucacosta.graphsj.{Algorithm, Scenario}
import info.gianlucacosta.helios.desktop.DesktopUtils
import info.gianlucacosta.helios.fx.dialogs.Alerts


object FordFulkersonScenario {
  val Name: String =
    "Ford-Fulkerson's flow algorithm"

  private val WebsiteUrl =
    "https://github.com/giancosta86/graphsj-scenarios"
}


class FordFulkersonScenario extends Scenario[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph] {
  override val name: String =
    FordFulkersonScenario.Name


  override def showHelp(): Unit = {
    DesktopUtils.openBrowser(FordFulkersonScenario.WebsiteUrl)
  }


  override def showSettings(designGraph: FordFulkersonGraph): Option[FordFulkersonGraph] = {
    Alerts.showInfo("No settings available for this scenario.")

    None
  }


  override def createAlgorithm(): Algorithm[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph] =
    new FordFulkersonAlgorithm[FordFulkersonGraph]


  override def createDesignController(): GraphCanvasController[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph] =
    new FordFulkersonDesignController[FordFulkersonGraph]


  override def createRuntimeController(): GraphCanvasController[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph] =
    new DragDropController[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph](renderDirected = false)


  override def createDesignGraph(): FordFulkersonGraph =
    new FordFulkersonGraph


  override def runStepsBeforePausing: Int =
    0

  override def stylesheets: List[String] =
    List(
      BasicStyles.resourceUrl.toExternalForm,
      getClass.getResource("FordFulkersonStyles.css").toExternalForm
    )
}
