package info.gianlucacosta.graphsj.scenarios.classic

import info.gianlucacosta.eighthbridge.fx.graph.VisualLink
import info.gianlucacosta.eighthbridge.graphs.facets.{HasDefaultArcBarbs, HasHandlesSettable, HasLabelSettable, HasOptionalCustomLabelCenterSettable, HasSelectedSettable, HasStyleClasses, HasStyleClassesSettable}

//TODO! Revise this test class
class ClassicDesignLink
  extends VisualLink
    with HasSelectedSettable
    with HasStyleClassesSettable
    with HasDefaultArcBarbs
    with HasHandlesSettable
    with HasLabelSettable
    with HasOptionalCustomLabelCenterSettable