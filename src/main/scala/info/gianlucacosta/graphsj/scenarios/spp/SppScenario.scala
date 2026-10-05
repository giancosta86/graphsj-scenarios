package info.gianlucacosta.graphsj.scenarios.spp

import info.gianlucacosta.eighthbridge.fx.canvas.GraphCanvasController
import info.gianlucacosta.eighthbridge.fx.canvas.basic.{BasicStyles, DragDropController}
import info.gianlucacosta.graphsj.{Algorithm, Scenario}
import info.gianlucacosta.helios.desktop.DesktopUtils
import info.gianlucacosta.helios.fx.dialogs.Alerts


object SppScenario {
  val Name: String =
    "Dijkstra's Shortest Path Problem (SPP)"

  private val WebsiteUrl =
    "https://github.com/giancosta86/graphsj-scenarios"
}


class SppScenario extends Scenario[SppVertex, SppLink, SppGraph] {
  override val name: String =
    SppScenario.Name


  override def showHelp(): Unit = {
    DesktopUtils.openBrowser(SppScenario.WebsiteUrl)
  }


  override def showSettings(designGraph: SppGraph): Option[SppGraph] = {
    Alerts.showInfo("No settings available for this scenario.")

    None
  }


  override def createAlgorithm(): Algorithm[SppVertex, SppLink, SppGraph] =
    new SppAlgorithm


  override def createDesignController(): GraphCanvasController[SppVertex, SppLink, SppGraph] =
    new SppDesignController[SppGraph]


  override def createRuntimeController(): GraphCanvasController[SppVertex, SppLink, SppGraph] =
    new DragDropController[SppVertex, SppLink, SppGraph](renderDirected = true)


  override def createDesignGraph(): SppGraph =
    new SppGraph


  override def runStepsBeforePausing: Int =
    0

  override def stylesheets: List[String] =
    List(
      BasicStyles.resourceUrl.toExternalForm,
      getClass.getResource("SppStyles.css").toExternalForm
    )
}
