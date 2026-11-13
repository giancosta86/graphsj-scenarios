package info.gianlucacosta.graphsj.scenarios.classic

import info.gianlucacosta.eighthbridge.fx.graph.VisualArc
import info.gianlucacosta.eighthbridge.graphs.directed.HasBarbsSettable
import info.gianlucacosta.eighthbridge.graphs.facets.{HasHandlesSettable, HasLabelSettable, HasOptionalCustomLabelCenterSettable, HasSelectedSettable, HasStyleClasses, HasStyleClassesSettable}

//TODO! Revise this test class
class ClassicDesignArc
  extends VisualArc
    with HasSelectedSettable
    with HasStyleClassesSettable
    with HasHandlesSettable
    with HasLabelSettable
    with HasBarbsSettable
    with HasOptionalCustomLabelCenterSettable