package info.gianlucacosta.graphsj.scenarios.classic.design

import info.gianlucacosta.eighthbridge.fx.graph.VisualEdge
import info.gianlucacosta.eighthbridge.graphs.facets._

//TODO! Weighted links should have a read-only label, returning the toString() of HasWeightBoundedSettable

//TODO! Revise this test class
class ClassicDesignEdge
  extends VisualEdge
    with HasSelectedSettable
    with HasStyleClassesSettable
    with HasHandlesSettable
    with HasLabelSettable
    with HasOptionalCustomLabelCenterSettable{
}
