package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.arcnodes.labeled.LabeledArcNode
import info.gianlucacosta.eighthbridge.fx.{ArcNode, GraphCanvasController, VertexNode}
import info.gianlucacosta.eighthbridge.fx.controller.{AdvancedLayoutEditing, Undirected, VertexNameEditing}
import info.gianlucacosta.eighthbridge.fx.graph.{NamedVisualVertex, VisualArc, VisualVertex}
import info.gianlucacosta.eighthbridge.fx.vertexnodes.labeled.LabeledVertexNode
import info.gianlucacosta.eighthbridge.theory.features.Named
import scalafx.geometry.Point2D

class PrimDesignController extends GraphCanvasController
  with AdvancedLayoutEditing
  with VertexNameEditing
  with Undirected {

  override def createVertexNode(vertex: VisualVertex): VertexNode =
    new LabeledVertexNode(graphCanvas, vertex)

  override def createArcNode(arc: VisualArc): ArcNode =
    new LabeledArcNode(graphCanvas, arc)

  override protected def createNamedVertex(center: Point2D, vertexName: String): VisualVertex with Named =
    new NamedVisualVertex(center, vertexName)
}
