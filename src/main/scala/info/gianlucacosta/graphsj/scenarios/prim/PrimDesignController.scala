package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.controllers.{VertexNamingController, WeightLinkController}
import scalafx.geometry.Point2D

class PrimDesignController extends VertexNamingController[PrimVertex, PrimLink, PrimGraph] with WeightLinkController[PrimVertex, PrimLink, PrimGraph] {
  override protected def instantiateVertex(center: Point2D, vertexName: String): PrimVertex =
    new PrimVertex(center = center, name = vertexName)


  override def createLink(graph: PrimGraph, sourceVertex: PrimVertex, targetVertex: PrimVertex): Option[PrimGraph] = {
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
