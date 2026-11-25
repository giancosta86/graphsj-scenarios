package info.gianlucacosta.graphsj.scenarios.classic

import info.gianlucacosta.eighthbridge.core.directed.{Arc, HasBarbsSettable}
import info.gianlucacosta.eighthbridge.core.facets.{HasLabel, HasLabelSettable, HasName, HasNameSettable, HasSelected, HasSelectedSettable, HasStyleClasses, HasStyleClassesSettable, HasWeightBounded, HasWeightBoundedSettable}
import info.gianlucacosta.eighthbridge.core.{Facet, Graph}
import info.gianlucacosta.eighthbridge.fx.facets.{HasCenter, HasCenterSettable, HasHandles, HasHandlesSettable, HasOptionalCustomLabelCenter, HasOptionalCustomLabelCenterSettable}
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
        copyVertexFacets(
          designVertex,
          createRuntimeVertex(designVertex)
        )

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

  protected def copyVertexFacets(designVertex: DesignVertex, runtimeVertex: RuntimeVertex): RuntimeVertex = {
    var result: RuntimeVertex =
      runtimeVertex

    Facet.as[RuntimeVertex with HasCenterSettable](result).foreach(facetedRuntimeVertex =>
      Facet.as[HasCenter](designVertex).foreach(facetedDesignVertex => {
        result =
          facetedRuntimeVertex.setCenter(facetedDesignVertex.center)
      })
    )

    Facet.as[RuntimeVertex with HasNameSettable](result).foreach(facetedRuntimeVertex =>
      Facet.as[HasName](designVertex).foreach(facetedDesignVertex => {
        result =
          facetedRuntimeVertex.setName(facetedDesignVertex.name)
      })
    )

    Facet.as[RuntimeVertex with HasLabelSettable](result).foreach(facetedRuntimeVertex =>
      Facet.as[HasLabel](designVertex).foreach(facetedDesignVertex => {
        result =
          facetedRuntimeVertex.setLabel(facetedDesignVertex.label)
      })
    )

    Facet.as[RuntimeVertex with HasSelectedSettable](result).foreach(facetedRuntimeVertex =>
      Facet.as[HasSelected](designVertex).foreach(facetedDesignVertex => {
        result =
          facetedRuntimeVertex.setSelected(facetedDesignVertex.selected)
      })
    )

    Facet.as[RuntimeVertex with HasStyleClassesSettable](result).foreach(facetedRuntimeVertex =>
      Facet.as[HasStyleClasses](designVertex).foreach(facetedDesignVertex => {
        result =
          facetedRuntimeVertex.setStyleClasses(facetedDesignVertex.styleClasses)
      })
    )

    result
  }

  protected def createRuntimeLink(designLink: DesignLink): RuntimeLink

  protected def copyLinkFacets(designLink: DesignLink, runtimeLink: RuntimeLink): RuntimeLink = {
    var result: RuntimeLink =
      runtimeLink

    Facet.as[RuntimeLink with HasNameSettable](result).foreach(facetedRuntimeLink =>
      Facet.as[HasName](designLink).foreach(facetedDesignLink => {
        result =
          facetedRuntimeLink.setName(facetedDesignLink.name)
      })
    )

    Facet.as[RuntimeLink with HasLabelSettable](result).foreach(facetedRuntimeLink =>
      Facet.as[HasLabel](designLink).foreach(facetedDesignLink => {
        result =
          facetedRuntimeLink.setLabel(facetedDesignLink.label)
      })
    )

    Facet.as[RuntimeLink with HasSelectedSettable](result).foreach(facetedRuntimeLink =>
      Facet.as[HasSelected](designLink).foreach(facetedDesignLink => {
        result =
          facetedRuntimeLink.setSelected(facetedDesignLink.selected)
      })
    )

    Facet.as[RuntimeLink with HasStyleClassesSettable](result).foreach(facetedRuntimeLink =>
      Facet.as[HasStyleClasses](designLink).foreach(facetedDesignLink => {
        result =
          facetedRuntimeLink.setStyleClasses(facetedDesignLink.styleClasses)
      })
    )

    Facet.as[RuntimeLink with HasHandlesSettable](result).foreach(facetedRuntimeLink =>
      Facet.as[HasHandles](designLink).foreach(facetedDesignLink => {
        result =
          facetedRuntimeLink.setHandles(facetedDesignLink.handles)
      })
    )

    Facet.as[RuntimeLink with HasOptionalCustomLabelCenterSettable](result).foreach(facetedRuntimeLink =>
      Facet.as[HasOptionalCustomLabelCenter](designLink).foreach(facetedDesignLink => {
        result =
          facetedRuntimeLink.setCustomLabelCenter(facetedDesignLink.customLabelCenter)
      })
    )

    Facet.as[RuntimeLink with HasBarbsSettable](result).foreach(facetedRuntimeLink =>
      Facet.as[Arc](designLink).foreach(facetedDesignLink => {
        result =
          facetedRuntimeLink.setBarbs(facetedDesignLink.barbs)
      })
    )

    Facet.as[RuntimeLink with HasWeightBoundedSettable](result).foreach(facetedRuntimeLink =>
      Facet.as[HasWeightBounded](designLink).foreach(facetedDesignLink => {
        result =
          facetedRuntimeLink.setWeight(
            demand=
              facetedDesignLink.demand,
            weight=
              facetedDesignLink.weight,
            capacity=
              facetedDesignLink.capacity
          )
      })
    )

    result
  }

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
