package info.gianlucacosta.graphsj.scenarios

import info.gianlucacosta.eighthbridge.graphs.{Link, Vertex}
import info.gianlucacosta.eighthbridge.graphs.directed.DirectedGraph
import info.gianlucacosta.eighthbridge.graphs.facets.{HasNameSettable, HasWeightSettable}
import info.gianlucacosta.graphsj.scenarios.classic.{ClassicDesignLink, ClassicDesignVertex}


package object prim {
  type PrimVertex =
    ClassicDesignVertex

  type PrimLink =
    ClassicDesignLink

  //TODO! Undirected!
  type PrimGraph =
    DirectedGraph[PrimVertex, PrimLink]
}
