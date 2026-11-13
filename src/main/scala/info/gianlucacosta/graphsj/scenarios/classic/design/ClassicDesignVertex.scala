//TODO! Move this entire package to the SDK!
package info.gianlucacosta.graphsj.scenarios.classic.design

import info.gianlucacosta.eighthbridge.fx.graph.VisualVertex
import info.gianlucacosta.eighthbridge.graphs.facets._

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