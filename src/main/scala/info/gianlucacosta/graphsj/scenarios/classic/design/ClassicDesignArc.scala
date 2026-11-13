package info.gianlucacosta.graphsj.scenarios.classic.design

import info.gianlucacosta.eighthbridge.fx.graph.VisualArc
import info.gianlucacosta.eighthbridge.graphs.directed.HasBarbsSettable
import info.gianlucacosta.eighthbridge.graphs.facets._

//TODO! Revise this test class
class ClassicDesignArc
  extends VisualArc
    with HasSelectedSettable
    with HasStyleClassesSettable
    with HasHandlesSettable
    with HasLabelSettable
    with HasBarbsSettable
    with HasOptionalCustomLabelCenterSettable