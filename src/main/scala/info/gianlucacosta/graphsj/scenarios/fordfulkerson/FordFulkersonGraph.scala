package info.gianlucacosta.graphsj.scenarios.fordfulkerson

import info.gianlucacosta.eighthbridge.graphs.point2point.visual.VisualGraph
import info.gianlucacosta.eighthbridge.graphs.point2point.{ArcBinding, TopologyCacheDirectedGraph}

case class FordFulkersonGraph(
                      vertexes: Set[FordFulkersonVertex] = Set(),
                      links: Set[FordFulkersonLink] = Set(),
                      bindings: Set[ArcBinding] = Set()
                    )
  extends VisualGraph[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph]
    with TopologyCacheDirectedGraph[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph] {
  override protected def graphCopy(vertexes: Set[FordFulkersonVertex], links: Set[FordFulkersonLink], bindings: Set[ArcBinding]): FordFulkersonGraph =
    copy(
      vertexes = vertexes,
      links = links,
      bindings = bindings
    )
}
