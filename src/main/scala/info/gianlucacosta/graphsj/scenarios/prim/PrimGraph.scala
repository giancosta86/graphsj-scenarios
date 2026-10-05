package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.canvas.VisualGraph
import info.gianlucacosta.eighthbridge.graphs.point2point.{ArcBinding, TopologyCacheDirectedGraph}

case class PrimGraph(
                      vertexes: Set[PrimVertex] = Set(),
                      links: Set[PrimLink] = Set(),
                      bindings: Set[ArcBinding] = Set()
                    )
  extends VisualGraph[PrimVertex, PrimLink]
    with TopologyCacheDirectedGraph[PrimVertex, PrimLink] {
  override protected def graphCopy(vertexes: Set[PrimVertex], links: Set[PrimLink], bindings: Set[ArcBinding]): this.type =
    copy(
      vertexes = vertexes,
      links = links,
      bindings = bindings
    ).asInstanceOf[this.type]
}
