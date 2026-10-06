package info.gianlucacosta.graphsj.scenarios.basicdecisiontree

import info.gianlucacosta.eighthbridge.fx.controllers.{DragDropController, ReadOnlyController}
import info.gianlucacosta.eighthbridge.fx.{GraphCanvasController, Styles}
import info.gianlucacosta.graphsj.{Algorithm, Scenario}
import info.gianlucacosta.helios.desktop.DesktopUtils
import info.gianlucacosta.lambdaprism.classification.basic.{ClassificationProblem, ClassificationProblemDialog}
import scalafx.geometry.Dimension2D

object DecisionTreeScenario {
  val Name =
    "Basic Decision Tree"

  val Stylesheets: List[String] =
    List(
      Styles.resourceUrl.toExternalForm,
      getClass.getResource("BasicDecisionTree.css").toExternalForm
    )

  private val WebsiteUrl =
    "https://github.com/giancosta86/GraphsJ-scenarios"
}


class DecisionTreeScenario(private var problem: ClassificationProblem) extends Scenario[DecisionTreeVertex, DecisionTreeLink, DecisionTreeGraph] {
  override val name: String =
    DecisionTreeScenario.Name


  override def createAlgorithm(): Algorithm[DecisionTreeVertex, DecisionTreeLink, DecisionTreeGraph] =
    new DecisionTreeAlgorithm(problem)


  override def createDesignController(): GraphCanvasController[DecisionTreeVertex, DecisionTreeLink, DecisionTreeGraph] =
    new ReadOnlyController[DecisionTreeVertex, DecisionTreeLink, DecisionTreeGraph](true)


  override def runStepsBeforePausing: Int =
    0


  override def stylesheets: List[String] =
    DecisionTreeScenario.Stylesheets


  override def createRuntimeController(): GraphCanvasController[DecisionTreeVertex, DecisionTreeLink, DecisionTreeGraph] =
    new DragDropController[DecisionTreeVertex, DecisionTreeLink, DecisionTreeGraph](true) {
      override def minCanvasDimension: Dimension2D =
        new Dimension2D(1500, 1500)
    }


  override def showHelp(): Unit =
    DesktopUtils.openBrowser(DecisionTreeScenario.WebsiteUrl)


  override def createDesignGraph(): DecisionTreeGraph =
    new DecisionTreeGraph


  override def showSettings(designGraph: DecisionTreeGraph): Option[DecisionTreeGraph] = {
    val newProblemOption = ClassificationProblemDialog.askForProblem(
      Some(problem)
    )

    newProblemOption.map(problem => {
      this.problem =
        problem

      new DecisionTreeGraph
    })
  }
}
