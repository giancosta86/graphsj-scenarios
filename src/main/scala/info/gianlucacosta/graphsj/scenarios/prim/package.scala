package info.gianlucacosta.graphsj.scenarios

import info.gianlucacosta.eighthbridge.core.undirected.UndirectedGraph
import info.gianlucacosta.graphsj.scenarios.classic.design.{ClassicDesignEdge, ClassicDesignVertex}


package object prim {
  type PrimDesignGraph =
    UndirectedGraph[PrimDesignVertex, PrimDesignEdge]
}
