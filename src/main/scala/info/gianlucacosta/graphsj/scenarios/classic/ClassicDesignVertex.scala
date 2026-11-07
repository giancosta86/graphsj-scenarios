package info.gianlucacosta.graphsj.scenarios.classic

import info.gianlucacosta.eighthbridge.fx.graph.VisualVertex
import info.gianlucacosta.eighthbridge.graphs.facets.{HasLabel, HasNameSettable}

//TODO! Move this entire package to the SDK!
class ClassicDesignVertex
  extends VisualVertex
    with HasNameSettable
    with HasLabel {
  override def label: String =
    name

  override def styleClasses: Set[String] =
    Set[String]()
}
