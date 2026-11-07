package info.gianlucacosta.graphsj.scenarios

import info.gianlucacosta.eighthbridge.graphs.{Link, Vertex}
import info.gianlucacosta.eighthbridge.graphs.directed.DirectedGraph
import info.gianlucacosta.eighthbridge.graphs.facets.{HasNameSettable, HasWeightSettable}


package object prim {
  type PrimVertex =
    Vertex with HasNameSettable

  type PrimEdge =
    Link with HasWeightSettable

  type PrimGraph =
    DirectedGraph[PrimVertex, PrimEdge]
}
