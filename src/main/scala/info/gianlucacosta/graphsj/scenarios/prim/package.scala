package info.gianlucacosta.graphsj.scenarios

import info.gianlucacosta.eighthbridge.graphs.directed.DirectedGraph
import info.gianlucacosta.graphsj.scenarios.classic.{ClassicDesignArc, ClassicDesignVertex}


package object prim {
  type PrimVertex =
    ClassicDesignVertex

  type PrimEdge =
    ClassicDesignArc //TODO! Edge!

  //TODO! Undirected!
  type PrimGraph =
    DirectedGraph[PrimVertex, PrimEdge]
}
