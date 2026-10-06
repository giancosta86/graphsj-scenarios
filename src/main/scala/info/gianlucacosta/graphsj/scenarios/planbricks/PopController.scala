package info.gianlucacosta.graphsj.scenarios.planbricks

import info.gianlucacosta.eighthbridge.fx.{GraphCanvas, GraphCanvasController, VertexNode}
import scalafx.geometry.{Dimension2D, Point2D}

trait PopController extends GraphCanvasController[StepVertex, PopLink, PopGraph] {
  override def createVertexNode(graphCanvas: GraphCanvas[StepVertex, PopLink, PopGraph], vertex: StepVertex): VertexNode[StepVertex, PopLink, PopGraph] =
    new StepVertexNode(graphCanvas, vertex.step)


  override def createLinkInternalPoint(graph: PopGraph, link: PopLink, newInternalPoints: List[Point2D], internalPoint: Point2D): Option[PopGraph] = {
    val newLink =
      link.visualCopy(internalPoints = newInternalPoints)

    Some(
      graph.replaceLink(newLink)
    )
  }


  override def deleteLinkInternalPoint(graph: PopGraph, link: PopLink, newInternalPoints: List[Point2D], internalPoint: Point2D): Option[PopGraph] = {
    val newLink =
      link.visualCopy(internalPoints = newInternalPoints)

    Some(
      graph.replaceLink(newLink)
    )
  }


  override def minCanvasDimension: Dimension2D =
    new Dimension2D(
      2000,
      2000
    )
}
