package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.canvas.basic.editing.{VertexNamingController, WeightLinkController}
import info.gianlucacosta.eighthbridge.graphs.point2point.visual.VisualGraph

import scalafx.geometry.Point2D

class PrimDesignController[G <: VisualGraph[PrimVertex, PrimLink, G]] extends VertexNamingController[PrimVertex, PrimLink, G] with WeightLinkController[PrimVertex, PrimLink, G] {
  override protected def instantiateVertex(center: Point2D, vertexName: String): PrimVertex =
    new PrimVertex(center = center, name = vertexName)


  override def createLink(graph: G, sourceVertex: PrimVertex, targetVertex: PrimVertex): Option[G] = {
    val link = new PrimLink(
      weight = 0
    )

    Some(
      graph.addLink(sourceVertex, targetVertex, link)
    )
  }


  override def renderDirected: Boolean =
    false
}
