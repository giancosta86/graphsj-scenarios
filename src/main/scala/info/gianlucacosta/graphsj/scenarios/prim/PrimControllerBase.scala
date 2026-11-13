package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.{GraphCanvas, GraphCanvasController, LinkNode, VertexNode}
import info.gianlucacosta.eighthbridge.fx.graph.{VisualLink, VisualVertex}
import info.gianlucacosta.eighthbridge.fx.linknodes.labeled.LabeledLinkNode
import info.gianlucacosta.eighthbridge.fx.vertexnodes.labeled.LabeledVertexNode

class PrimControllerBase extends GraphCanvasController {
  override def createVertexNode(vertex: VisualVertex): VertexNode =
    new LabeledVertexNode(graphCanvas, vertex)

  override def createLinkNode(link: VisualLink): LinkNode =
    new LabeledLinkNode(graphCanvas, link.asInstanceOf[PrimEdge])
}
