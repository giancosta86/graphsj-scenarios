package info.gianlucacosta.graphsj.scenarios.classic

import info.gianlucacosta.eighthbridge.core.Graph
import info.gianlucacosta.eighthbridge.fx.{VisualGraph, VisualLink, VisualVertex}
import info.gianlucacosta.graphsj.scenarios.classic.design.{ClassicDesignLink, ClassicDesignVertex}
import info.gianlucacosta.graphsj.scenarios.classic.runtime.{ClassicRuntimeLink, ClassicRuntimeVertex}
import info.gianlucacosta.graphsj.sdk.{Algorithm, OutputConsole}

abstract class ClassicAlgorithm(protected val verbose: Boolean) extends Algorithm {
  protected type DesignVertex <:
    ClassicDesignVertex

  protected type DesignLink <:
    ClassicDesignLink

  protected type DesignGraph =
    Graph[DesignVertex, DesignLink]

  protected type RuntimeVertex <:
    ClassicRuntimeVertex

  protected type RuntimeLink <:
    ClassicRuntimeLink

  protected type RuntimeGraph =
    Graph[RuntimeVertex, RuntimeLink]

  override def runStep(stepIndex: Int, graph: VisualGraph, console: OutputConsole): (VisualGraph, Boolean) = {
    val (nextGraph, shouldGoOn) =
      stepIndex match {
        case 0 =>
          val runtimeGraph: RuntimeGraph =
            createRuntimeGraph(graph.asInstanceOf[DesignGraph])

          onInit(runtimeGraph, console)

        case _ =>
          onMainStep(stepIndex, graph.asInstanceOf[RuntimeGraph], console)
      }

    if (shouldGoOn) {
      (nextGraph.asInstanceOf[VisualGraph], true)
    } else {
      val finalGraph: VisualGraph =
        onExpectedEnd(stepIndex, graph.asInstanceOf[RuntimeGraph], console)
          .asInstanceOf[VisualGraph] //TODO! Is there no way to delete this upcast and others?

      (finalGraph, false)
    }
  }

  protected def createRuntimeGraph(designGraph: DesignGraph): RuntimeGraph = {
    var runtimeGraph: RuntimeGraph = (
      if (designGraph.isDirected)
        Graph.createDirected()
      else
        Graph.createUndirected()
      ).asInstanceOf[RuntimeGraph]

    var runtimeVertexByDesignVertex: Map[DesignVertex, RuntimeVertex] =
      Map[DesignVertex, RuntimeVertex]()

    designGraph.vertexes.foreach(designVertex => {
      val runtimeVertex =
        createRuntimeVertex(designVertex)

      //TODO! Copy as many facets as possible!

      runtimeVertexByDesignVertex +=
        designVertex -> runtimeVertex

      runtimeGraph =
        runtimeGraph.addVertex(runtimeVertex)
    })

    designGraph.links.foreach(designLink => {
      val (firstDesignAnchor, secondDesignAnchor): (DesignVertex, DesignVertex) =
        designGraph.anchorsByLink(designLink).toPair

      val runtimeAnchors =
        (
          runtimeVertexByDesignVertex(firstDesignAnchor),
          runtimeVertexByDesignVertex(secondDesignAnchor)
        )

      val runtimeLink: RuntimeLink =
        createRuntimeLink(designLink)

      //TODO! Copy as many facets as possible!

      runtimeGraph =
        runtimeGraph.addLink(runtimeAnchors, runtimeLink)
    })

    runtimeGraph
  }

  protected def createRuntimeVertex(designVertex: DesignVertex): RuntimeVertex

  protected def createRuntimeLink(designLink: DesignLink): RuntimeLink

  protected def onInit(graph: RuntimeGraph, console: OutputConsole): (RuntimeGraph, Boolean)

  protected def onMainStep(stepIndex: Int, graph: RuntimeGraph, console: OutputConsole): (RuntimeGraph, Boolean)

  protected def onExpectedEnd(stepIndex: Int, graph: RuntimeGraph, console: OutputConsole): RuntimeGraph =
    graph

  def partialSolutionVertexes: Set[ClassicRuntimeVertex] =
    Set[ClassicRuntimeVertex]()

  def partialSolutionLinks: Set[ClassicRuntimeLink] =
    Set[ClassicRuntimeLink]()

  def fullSolutionVertexes: Set[ClassicRuntimeVertex] =
    Set[ClassicRuntimeVertex]()

  def fullSolutionLinks: Set[ClassicRuntimeLink] =
    Set[ClassicRuntimeLink]()
}
