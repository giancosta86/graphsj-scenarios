package info.gianlucacosta.graphsj.scenarios.classic

import info.gianlucacosta.eighthbridge.fx.graph.VisualVertex
import info.gianlucacosta.eighthbridge.graphs.facets.{HasLabel, HasNameSettable}

class ClassicDesignVertex
  extends VisualVertex
    with HasNameSettable
    with HasLabel {
  override def label: String =
    name
}
