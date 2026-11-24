package info.gianlucacosta.graphsj.scenarios.classic

import info.gianlucacosta.eighthbridge.core.Graph
import info.gianlucacosta.eighthbridge.core.directed.DirectedGraph
import info.gianlucacosta.eighthbridge.fx.{VisualDirectedGraph, VisualGraph, VisualLink, VisualUndirectedGraph, VisualVertex}
import info.gianlucacosta.graphsj.scenarios.classic.design.{ClassicDesignLink, ClassicDesignVertex}
import info.gianlucacosta.graphsj.scenarios.classic.runtime.{ClassicRuntimeLink, ClassicRuntimeVertex}
import info.gianlucacosta.graphsj.sdk.{Algorithm, OutputConsole}

abstract class ClassicAlgorithm(protected val verbose: Boolean) extends Algorithm {
  protected type DesignVertex <:
    ClassicDesignVertex

  protected type DesignLink <:
    ClassicDesignLink

  protected type DesignGraph <:
    Graph[DesignVertex, DesignLink]

  protected type RuntimeVertex <:
    ClassicRuntimeVertex

  protected type RuntimeLink <:
    ClassicRuntimeLink

  protected type RuntimeGraph <:
    Graph[RuntimeVertex, RuntimeLink]

  override def runStep(stepIndex: Int, graph: VisualGraph, console: OutputConsole): (VisualGraph, Boolean) = {

    val (nextGraph, shouldGoOn) =
      stepIndex match {
        case 0 =>
          val runtimeGraph =
            createRuntimeGraph(graph.asInstanceOf[DesignGraph])

          onInit(runtimeGraph, console)

        case _ =>
          onRunStep(stepIndex, graph.asInstanceOf[RuntimeGraph], console)
      }

    if (stepIndex > 0 && !shouldGoOn) {
      val finalGraph =
        onExpectedEnd(stepIndex, graph.asInstanceOf[RuntimeGraph], console)

      (finalGraph.asInstanceOf[VisualGraph], false)
    } else {
      (nextGraph.asInstanceOf[VisualGraph], shouldGoOn)
    }
  }

  protected def createRuntimeGraph(designGraph: DesignGraph): RuntimeGraph = {
    var runtimeGraph: RuntimeGraph = (
      if (designGraph.isDirected)
        new VisualDirectedGraph
      else
        new VisualUndirectedGraph
      ).asInstanceOf[RuntimeGraph]

    var runtimeVertexByDesignVertex: Map[DesignVertex, RuntimeVertex] =
      Map[DesignVertex, RuntimeVertex]()

    designGraph.vertexes.foreach(designVertex => {
      val runtimeVertex =
        createRuntimeVertex(designVertex)

      runtimeVertexByDesignVertex +=
        designVertex -> runtimeVertex

      runtimeGraph =
        runtimeGraph.addVertex(runtimeVertex)
    })

    designGraph.links.foreach(designLink => {
      val (firstDesignAnchor, secondDesignAnchor): (DesignVertex, DesignVertex) =
        designGraph.getAnchorPair(designLink).get

      val runtimeAnchors =
        (
          runtimeVertexByDesignVertex(firstDesignAnchor),
          runtimeVertexByDesignVertex(secondDesignAnchor)
        )

      val runtimeLink =
        createRuntimeLink(designLink)

      runtimeGraph =
        runtimeGraph.addLink(runtimeAnchors, runtimeLink)
    })

    runtimeGraph
  }

  protected def createRuntimeVertex(designVertex: DesignVertex): RuntimeVertex

  protected def createRuntimeLink(designLink: DesignLink): RuntimeLink


  protected def onInit(graph: RuntimeGraph, console: OutputConsole): (RuntimeGraph, Boolean)

  protected def onRunStep(stepIndex: Int, graph: RuntimeGraph, console: OutputConsole): (RuntimeGraph, Boolean)

  protected def onExpectedEnd(stepIndex: Int, graph: RuntimeGraph, console: OutputConsole): RuntimeGraph

  def partialSolutionVertexes: Set[ClassicRuntimeVertex]

  def partialSolutionLinks: Set[ClassicRuntimeLink]

  def fullSolutionVertexes: Set[ClassicRuntimeVertex]

  def fullSolutionLinks: Set[ClassicRuntimeLink]
}
