package info.gianlucacosta.graphsj.scenarios.spp

import info.gianlucacosta.eighthbridge.graphs.point2point.visual.VisualGraph
import info.gianlucacosta.eighthbridge.graphs.point2point.{ArcBinding, TopologyCacheDirectedGraph}

case class SppGraph(
                      vertexes: Set[SppVertex] = Set(),
                      links: Set[SppLink] = Set(),
                      bindings: Set[ArcBinding] = Set()
                    )
  extends VisualGraph[SppVertex, SppLink, SppGraph]
    with TopologyCacheDirectedGraph[SppVertex, SppLink, SppGraph] {
  override protected def graphCopy(vertexes: Set[SppVertex], links: Set[SppLink], bindings: Set[ArcBinding]): SppGraph =
    copy(
      vertexes = vertexes,
      links = links,
      bindings = bindings
    )
}
