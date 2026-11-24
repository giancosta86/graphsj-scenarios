package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.VisualGraph
import info.gianlucacosta.graphsj.scenarios.classic.ClassicAlgorithm
import info.gianlucacosta.graphsj.scenarios.classic.runtime.{ClassicRuntimeLink, ClassicRuntimeVertex}
import info.gianlucacosta.graphsj.sdk.OutputConsole

import scala.language.implicitConversions


class PrimAlgorithm(verbose: Boolean) extends ClassicAlgorithm(verbose) {
  override protected type DesignVertex =
    PrimDesignVertex
  override protected type DesignLink =
    PrimDesignEdge

  override protected type RuntimeVertex =
    PrimRuntimeVertex

  override protected type RuntimeLink =
    PrimRuntimeEdge

  private var treeWeight: Double = 0

  private var treeBindings: List[(PrimDesignVertex, PrimDesignVertex)] = _


  override protected def createRuntimeVertex(designVertex: PrimDesignVertex): PrimRuntimeVertex = ???

  override protected def createRuntimeLink(designLink: PrimDesignEdge): PrimRuntimeEdge = ???

  override protected def onInit(graph: RuntimeGraph, console: OutputConsole): (RuntimeGraph, Boolean) = ???

  override protected def onRunStep(stepIndex: Int, graph: RuntimeGraph, console: OutputConsole): (RuntimeGraph, Boolean) = ???

  override protected def onExpectedEnd(stepIndex: Int, graph: RuntimeGraph, console: OutputConsole): RuntimeGraph = ???

  override def partialSolutionVertexes: Set[ClassicRuntimeVertex] = ???

  override def partialSolutionLinks: Set[ClassicRuntimeLink] = ???

  override def fullSolutionVertexes: Set[ClassicRuntimeVertex] = ???

  override def fullSolutionLinks: Set[ClassicRuntimeLink] = ???
}
