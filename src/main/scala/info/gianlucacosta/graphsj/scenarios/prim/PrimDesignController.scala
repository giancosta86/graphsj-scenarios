package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.arcnodes.labeled.LabeledLinkNode
import info.gianlucacosta.eighthbridge.fx.controller.advanced.{CreateNamedVertex, EditNamedVertex}
import info.gianlucacosta.eighthbridge.fx.controller.composite.StructuralEditing
import info.gianlucacosta.eighthbridge.fx.{GraphCanvasController, LinkNode, VertexNode}
import info.gianlucacosta.eighthbridge.fx.graph.{ArcBarbs, LabeledVisualLink, NamedVisualVertex, VisualLink, VisualVertex}
import info.gianlucacosta.eighthbridge.fx.vertexnodes.labeled.LabeledVertexNode
import info.gianlucacosta.eighthbridge.graphs.features.Named
import scalafx.geometry.Point2D

class PrimDesignController extends GraphCanvasController
  with StructuralEditing
  with CreateNamedVertex
  with EditNamedVertex  {

  override type V =
    NamedVisualVertex

  override protected def createNamedVertex(center: Point2D, vertexName: String): VisualVertex with Named =
    new NamedVisualVertex(center, vertexName)

  override def createVertexNode(vertex: VisualVertex): VertexNode =
    new LabeledVertexNode(graphCanvas, vertex)

  override def addLink(sourceVertex: VisualVertex, targetVertex: VisualVertex): Unit = {
    graph =
      graph.addArc(
        sourceVertex,
        targetVertex,
        new LabeledVisualLink(Some(ArcBarbs.Default), "Dodus")
      )
  }

  override def createLinkNode(arc: VisualLink): LinkNode =
    new LabeledLinkNode(graphCanvas, arc.asInstanceOf[LabeledVisualLink])

}
