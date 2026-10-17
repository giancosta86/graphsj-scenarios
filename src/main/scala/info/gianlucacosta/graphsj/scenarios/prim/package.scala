package info.gianlucacosta.graphsj.scenarios

import info.gianlucacosta.eighthbridge.fx.graph.{LabeledVisualArc, LabeledVisualVertex, NamedVisualVertex, VisualGraph}
import info.gianlucacosta.eighthbridge.theory.{Arc, DirectedGraph, Vertex}
import info.gianlucacosta.eighthbridge.theory.features.{Named, Weighted}

package object prim {
  type PrimVertex =
    Vertex with Named

  type PrimEdge =
    Arc with Weighted

  type PrimGraph =
    DirectedGraph[PrimVertex, PrimEdge]
}
