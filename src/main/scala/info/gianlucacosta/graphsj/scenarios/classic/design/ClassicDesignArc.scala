package info.gianlucacosta.graphsj.scenarios.classic.design

import info.gianlucacosta.eighthbridge.core.directed.HasBarbsSettable
import info.gianlucacosta.eighthbridge.core.facets._
import info.gianlucacosta.eighthbridge.fx.VisualArc

//TODO! Revise this test class
class ClassicDesignArc
  extends VisualArc
    with HasSelectedSettable
    with HasStyleClassesSettable
    with HasHandlesSettable
    with HasLabelSettable
    with HasBarbsSettable
    with HasOptionalCustomLabelCenterSettable