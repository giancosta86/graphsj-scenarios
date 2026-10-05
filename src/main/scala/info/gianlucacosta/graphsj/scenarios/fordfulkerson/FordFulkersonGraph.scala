package info.gianlucacosta.graphsj.scenarios.fordfulkerson

import info.gianlucacosta.eighthbridge.fx.canvas.VisualGraph
import info.gianlucacosta.eighthbridge.graphs.point2point.ArcBinding

case class FordFulkersonGraph(
                      vertexes: Set[FordFulkersonVertex] = Set(),
                      links: Set[FordFulkersonLink] = Set(),
                      bindings: Set[ArcBinding] = Set()
                    )
  extends VisualGraph[FordFulkersonVertex, FordFulkersonLink] {
  override protected def graphCopy(vertexes: Set[FordFulkersonVertex], links: Set[FordFulkersonLink], bindings: Set[ArcBinding]): this.type =
    copy(
      vertexes = vertexes,
      links = links,
      bindings = bindings
    ).asInstanceOf[this.type]
}
