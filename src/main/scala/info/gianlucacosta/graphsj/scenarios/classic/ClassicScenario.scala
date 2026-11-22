package info.gianlucacosta.graphsj.scenarios.classic

import info.gianlucacosta.eighthbridge.fx.{Styles, VisualGraph}
import info.gianlucacosta.graphsj.scenarios.prim.PrimScenario
import info.gianlucacosta.graphsj.sdk.Scenario
import info.gianlucacosta.helios.desktop.DesktopUtils
import info.gianlucacosta.helios.fx.dialogs.Alerts

object ClassicScenario {
  private val WebsiteUrl =
    "https://github.com/giancosta86/graphsj-scenarios"

  private val Styles =
    getClass.getResource("ClassicStyles.css")
}

abstract class ClassicScenario extends Scenario {
  private var _verbose: Boolean =
    true

  protected def verbose: Boolean =
    _verbose

  override def stylesheets: List[String] =
    List(
      Styles.resourceUrl,
      ClassicScenario.Styles
    )
      .map(_.toExternalForm)

  override def showHelp(): Unit = {
    DesktopUtils.openBrowser(ClassicScenario.WebsiteUrl)
  }

  //TODO! Add support for the "verbose" flag here?
  override def showSettings(designGraph: VisualGraph): Option[VisualGraph] = {
    Alerts.showInfo("No settings available for this scenario.")
    None
  }

  override def runStepsBeforePausing: Int =
    0
}
