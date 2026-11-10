//TODO! Move this entire package to the SDK!
package info.gianlucacosta.graphsj.scenarios.classic

import info.gianlucacosta.eighthbridge.fx.graph.VisualVertex
import info.gianlucacosta.eighthbridge.graphs.facets.{HasCenterSettable, HasLabel, HasLabelSettable, HasNameSettable, HasSelectedSettable, HasStyleClassesSettable}

//TODO: Revise this test class
class ClassicDesignVertex
  extends VisualVertex
    with HasCenterSettable
    with HasSelectedSettable
    with HasStyleClassesSettable
    with HasNameSettable
    with HasLabel {
  override def label: String =
    name
}