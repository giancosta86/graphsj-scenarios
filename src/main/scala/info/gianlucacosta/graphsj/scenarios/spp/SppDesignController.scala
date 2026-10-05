package info.gianlucacosta.graphsj.scenarios.spp

import info.gianlucacosta.eighthbridge.fx.canvas.basic.editing.{VertexNamingController, WeightLinkController}
import info.gianlucacosta.eighthbridge.graphs.point2point.visual.VisualGraph

import scalafx.geometry.Point2D

class SppDesignController extends VertexNamingController[SppVertex, SppLink, SppGraph] with WeightLinkController[SppVertex, SppLink, SppGraph] {
  override protected def instantiateVertex(center: Point2D, vertexName: String): SppVertex =
    new SppVertex(center = center, name = vertexName)


  override def createLink(graph: SppGraph, sourceVertex: SppVertex, targetVertex: SppVertex): Option[SppGraph] = {
    val link = new SppLink(
      weight = 0
    )

    Some(
      graph.addLink(sourceVertex, targetVertex, link)
    )
  }


  override def renderDirected: Boolean =
    true
}
