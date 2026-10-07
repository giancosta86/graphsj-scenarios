package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.GraphCanvasController
import info.gianlucacosta.eighthbridge.fx.controller.{AdvancedLayoutEditing, LinkWeightEditing, Undirected, VertexNameEditing}
import scalafx.geometry.Point2D

class PrimDesignController extends GraphCanvasController[PrimVertex, PrimLink, PrimGraph]
  with AdvancedLayoutEditing[PrimVertex, PrimLink, PrimGraph]
  with VertexNameEditing[PrimVertex, PrimLink, PrimGraph]
  with LinkWeightEditing[PrimVertex, PrimLink, PrimGraph]
  with Undirected[PrimVertex, PrimLink, PrimGraph] {
  override protected def createNamedVertex(center: Point2D, vertexName: String): PrimVertex =
    PrimVertex(center = center, name = vertexName)


  override def createLink(sourceVertex: PrimVertex, targetVertex: PrimVertex): Option[PrimGraph] = {
    val link = PrimLink(
      weight = 0
    )

    Some(
      graph.addLink(sourceVertex, targetVertex, link)
    )
  }
}
