package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.controller.advanced.{CreateNamedVertex, EditNamedVertex}
import info.gianlucacosta.eighthbridge.fx.controller.composite.StructuralEditing
import info.gianlucacosta.eighthbridge.fx.{GraphCanvasController, LinkNode, VertexNode}
import info.gianlucacosta.eighthbridge.fx.graph.{ArcBarbs, LabeledVisualLink, NamedVisualVertex, VisualLink, VisualVertex}
import info.gianlucacosta.eighthbridge.fx.linknodes.labeled.LabeledLinkNode
import info.gianlucacosta.eighthbridge.fx.vertexnodes.labeled.LabeledVertexNode
import info.gianlucacosta.eighthbridge.fx.vertexnodes.plain.PlainVertexNode
import info.gianlucacosta.eighthbridge.graphs.features.Named
import scalafx.geometry.Point2D

class PrimControllerBase extends GraphCanvasController {
  override def createVertexNode(vertex: VisualVertex): VertexNode =
    new LabeledVertexNode(graphCanvas, vertex)

  override def createLinkNode(arc: VisualLink): LinkNode =
    new LabeledLinkNode(graphCanvas, arc.asInstanceOf[LabeledVisualLink])
}
