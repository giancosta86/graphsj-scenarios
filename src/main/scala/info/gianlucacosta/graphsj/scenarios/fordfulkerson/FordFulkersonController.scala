package info.gianlucacosta.graphsj.scenarios.fordfulkerson

import info.gianlucacosta.eighthbridge.fx.canvas.basic.editing.{VertexNamingController, WeightLinkController}
import info.gianlucacosta.eighthbridge.graphs.point2point.visual.VisualGraph

import scalafx.geometry.Point2D

class FordFulkersonDesignController[G <: VisualGraph[FordFulkersonVertex, FordFulkersonLink]] extends VertexNamingController[FordFulkersonVertex, FordFulkersonLink, G] with WeightLinkController[FordFulkersonVertex, FordFulkersonLink, G] {
  override protected def instantiateVertex(center: Point2D, vertexName: String): FordFulkersonVertex =
    new FordFulkersonVertex(center = center, name = vertexName)


  override def createLink(graph: G, sourceVertex: FordFulkersonVertex, targetVertex: FordFulkersonVertex): Option[G] = {
    val link = new FordFulkersonLink(
      weight = 0
    )

    Some(
      graph.addLink(sourceVertex, targetVertex, link)
    )
  }


  override def renderDirected: Boolean =
    true
}
