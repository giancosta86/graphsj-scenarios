package info.gianlucacosta.graphsj.scenarios

import info.gianlucacosta.eighthbridge.graphs.{Link, Vertex}
import info.gianlucacosta.eighthbridge.graphs.directed.DirectedGraph
import info.gianlucacosta.eighthbridge.graphs.features.{Named, Weighted}


package object prim {
  type PrimVertex =
    Vertex with Named

  type PrimEdge =
    Link with Weighted

  type PrimGraph =
    DirectedGraph[PrimVertex, PrimEdge]
}
