package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.arcnodes.labeled.LabeledArcNode
import info.gianlucacosta.eighthbridge.fx.{ArcNode, GraphCanvasController, VertexNode}
import info.gianlucacosta.eighthbridge.fx.controller.{AdvancedLayoutEditing, Undirected, VertexNameEditing}
import info.gianlucacosta.eighthbridge.fx.graph.{NamedVisualVertex, VisualVertex}
import info.gianlucacosta.eighthbridge.fx.vertexnodes.labeled.LabeledVertexNode
import info.gianlucacosta.eighthbridge.theory.features.Named
import scalafx.geometry.Point2D

class PrimDesignController extends GraphCanvasController
  with AdvancedLayoutEditing
  with VertexNameEditing
  with Undirected {

  override def createVertexNode(): VertexNode =
    new LabeledVertexNode()

  override def createArcNode(): ArcNode =
    new LabeledArcNode()

  override protected def createNamedVertex(center: Point2D, vertexName: String): VisualVertex with Named =
    new NamedVisualVertex(center, vertexName)
}
