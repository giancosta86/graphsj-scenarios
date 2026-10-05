package info.gianlucacosta.graphsj.scenarios.fordfulkerson

import info.gianlucacosta.eighthbridge.graphs.point2point.visual.VisualGraph
import info.gianlucacosta.graphsj.{Algorithm, OutputConsole}
import info.gianlucacosta.helios.fx.dialogs.InputDialogs
import info.gianlucacosta.helios.mathutils.Numbers


class FordFulkersonAlgorithm[G <: VisualGraph[FordFulkersonVertex, FordFulkersonLink, G]] extends Algorithm[FordFulkersonVertex, FordFulkersonLink, G] {
  private var startVertex: FordFulkersonVertex = _
  private var stopVertex: FordFulkersonVertex = _
  private var incChain: Option[List[(FordFulkersonVertex, FordFulkersonVertex)]] = _


  val verbose = true //This basic algorithm version is always verbose*/


  private def edgeBindingsAsString(edgeBindings: List[(FordFulkersonVertex, FordFulkersonVertex)]): String = {
    edgeBindings
      .map(binding => s"{${binding._1.name}, ${binding._2.name}}")
      .mkString("[", ", ", "]")
  }


  private def getMinWeightBetween(graph: G, vertexes: Set[FordFulkersonVertex]): Double = {
    graph.getLinksBetween(vertexes)
      .map(_.weight)
      .toList
      .sorted
      .headOption
      .getOrElse(Double.PositiveInfinity)
  }


  override def runStep(stepIndex: Int, graph: G, console: OutputConsole): (G, Boolean) = {
    stepIndex match {
      case 0 =>
        init(graph, console)

      case _ =>
        runStandardStep(stepIndex, graph, console)
    }
  }


  private def init(graph: G, console: OutputConsole): (G, Boolean) = {
    if (graph.vertexes.isEmpty) {
      throw new RuntimeException("No vertex defined!")
    }

    if (graph.unlinkedVertexes.nonEmpty) {
      throw new RuntimeException("Every vertex in the graph must be connected!")
    }

    val vertexPool = graph
        .vertexes
        .toList
        .sortBy(_.text)

    var startVertexInput = InputDialogs.askForItem("Start vertex:", vertexPool)
    if (startVertexInput.isDefined) {
      startVertex = startVertexInput.get
    } else {
      console.writeln("*** REQUIRED INPUT NOT PROVIDED ***") //TODO! turn this into a method?
      return (graph, false)
    }

    val updatedVertexPool = vertexPool
      .filter(_ != startVertex)

    val stopVertexInput = InputDialogs.askForItem("Stop vertex:", updatedVertexPool)
    if (stopVertexInput.isDefined) {
      stopVertex = stopVertexInput.get
    } else {
      console.writeln("*** REQUIRED INPUT NOT PROVIDED ***") //TODO! turn this into a method?
      return (graph, false)
    }

    incChain = None

    (graph, true)
  }


  private def runStandardStep(stepIndex: Int, graph: G, console: OutputConsole): (G, Boolean) = {
    if (verbose) {
      console.writeln();
      console.writeHeader("Step " + stepIndex);
      console.writeln()
    }

    val currentStepInSeries = stepIndex % 3 //TODO! It was "currentStep" in the original codebase

    var stepGraph = graph
    /*
    currentStepInSeries match {
      case 1 =>
        incChain = None

        stepGraph = stepGraph.replaceVertexes(
          stepGraph.vertexes.map { vertex =>
            vertex.copy(
              tag = None,
              explored = Some(false)
            )
          }
        )

        startVertex = startVertex.copy(
          tag = Some(FordFulkersonTag(
            plusVk = true,
            vk = None,
            delta = Double.PositiveInfinity
          )),
          explored = Some(false)
        )

        stepGraph.vertexes.foreach {
          var vi: Option[FordFulkersonVertex] = None
          
        }
    }*/

    (stepGraph, true)
  }
}
