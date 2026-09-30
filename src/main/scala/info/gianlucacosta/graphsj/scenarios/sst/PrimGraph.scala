package info.gianlucacosta.graphsj.scenarios.sst

import info.gianlucacosta.eighthbridge.graphs.point2point.visual.VisualGraph
import info.gianlucacosta.eighthbridge.graphs.point2point.{ArcBinding, TopologyCacheDirectedGraph}

case class PrimGraph(
                      vertexes: Set[PrimVertex] = Set(),
                      links: Set[PrimLink] = Set(),
                      bindings: Set[ArcBinding] = Set()
                    )
  extends VisualGraph[PrimVertex, PrimLink, PrimGraph]
    with TopologyCacheDirectedGraph[PrimVertex, PrimLink, PrimGraph] {
  override protected def graphCopy(vertexes: Set[PrimVertex], links: Set[PrimLink], bindings: Set[ArcBinding]): PrimGraph =
    copy(
      vertexes = vertexes,
      links = links,
      bindings = bindings
    )
}
