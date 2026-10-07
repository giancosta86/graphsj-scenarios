package info.gianlucacosta.graphsj.scenarios.fordfulkerson

import info.gianlucacosta.eighthbridge.fx.controller.{AdvancedLayoutEditing, Directed, LayoutEditing}
import info.gianlucacosta.eighthbridge.fx.{GraphCanvasController, Styles}
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
    new FordFulkersonAlgorithm


  override def createDesignController(): GraphCanvasController[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph] =
    new FordFulkersonDesignController


  override def createRuntimeController(): GraphCanvasController[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph] =
    new LayoutEditing[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph]
      with Directed[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph]


  override def createDesignGraph(): FordFulkersonGraph =
    new FordFulkersonGraph


  override def runStepsBeforePausing: Int =
    0

  override def stylesheets: List[String] =
    List(
      Styles.resourceUrl.toExternalForm,
      getClass.getResource("FordFulkersonStyles.css").toExternalForm
    )
}
