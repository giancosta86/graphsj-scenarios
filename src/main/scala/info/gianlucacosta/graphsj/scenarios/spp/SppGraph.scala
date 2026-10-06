package info.gianlucacosta.graphsj.scenarios.spp

import info.gianlucacosta.eighthbridge.fx.VisualGraph
import info.gianlucacosta.eighthbridge.graphs.point2point.ArcBinding

case class SppGraph(
                      vertexes: Set[SppVertex] = Set(),
                      links: Set[SppLink] = Set(),
                      bindings: Set[ArcBinding] = Set()
                    )
  extends VisualGraph[SppVertex, SppLink] {
  override protected def graphCopy(vertexes: Set[SppVertex], links: Set[SppLink], bindings: Set[ArcBinding]): this.type =
    copy(
      vertexes = vertexes,
      links = links,
      bindings = bindings
    ).asInstanceOf[this.type]
}
