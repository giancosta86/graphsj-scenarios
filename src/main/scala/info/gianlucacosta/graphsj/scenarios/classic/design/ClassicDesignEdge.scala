package info.gianlucacosta.graphsj.scenarios.classic.design

import info.gianlucacosta.eighthbridge.core.facets._
import info.gianlucacosta.eighthbridge.fx.VisualEdge
import info.gianlucacosta.eighthbridge.fx.facets.{HasHandlesSettable, HasOptionalCustomLabelCenterSettable}

//TODO! Weighted links should have a read-only label, returning the toString() of HasWeightBoundedSettable

//TODO! Revise this test class
class ClassicDesignEdge
  extends VisualEdge
    with ClassicDesignLink

