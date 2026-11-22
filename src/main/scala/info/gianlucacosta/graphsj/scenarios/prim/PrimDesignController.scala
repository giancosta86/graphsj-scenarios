package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.controllers.creation.CreateVertexWithName
import info.gianlucacosta.eighthbridge.fx.controllers.editing.{EditLinkWeight, EditVertexName}
import info.gianlucacosta.eighthbridge.fx.linknodes.labeled.LabeledLinkNode
import info.gianlucacosta.eighthbridge.fx.vertexnodes.labeled.LabeledVertexNode
import info.gianlucacosta.eighthbridge.fx.{LinkNode, VertexNode, VisualLink, VisualVertex}
import info.gianlucacosta.graphsj.scenarios.classic.ClassicDesignController
import scalafx.geometry.Point2D

class PrimDesignController
  extends ClassicDesignController
  with CreateVertexWithName
  with EditVertexName
  with EditLinkWeight {
  override def createVertexNode(vertex: VisualVertex): VertexNode =
    new LabeledVertexNode(graphCanvas, vertex)

  override def createLinkNode(link: VisualLink): LinkNode =
    new LabeledLinkNode(graphCanvas, link.asInstanceOf[PrimDesignEdge])

  override protected def createNamedVertex(center: Point2D, vertexName: String): PrimDesignVertex =
    new PrimDesignVertex()
      .setCenter(center)
      .setName(vertexName)

  override def addLink(sourceVertex: VisualVertex, targetVertex: VisualVertex): Unit = {
    graph =
      graph.addLink(
        sourceVertex,
        targetVertex,
        new PrimDesignEdge()
      )
  }
}
