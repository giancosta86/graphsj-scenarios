package info.gianlucacosta.graphsj.scenarios

import info.gianlucacosta.eighthbridge.graphs.directed.DirectedGraph
import info.gianlucacosta.eighthbridge.graphs.undirected.UndirectedGraph
import info.gianlucacosta.graphsj.scenarios.classic.design.{ClassicDesignArc, ClassicDesignEdge, ClassicDesignVertex}


package object prim {
  type PrimVertex =
    ClassicDesignVertex

  type PrimEdge =
    ClassicDesignEdge

  type PrimGraph =
    UndirectedGraph[PrimVertex, PrimEdge]
}
