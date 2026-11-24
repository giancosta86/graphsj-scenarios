package info.gianlucacosta.graphsj.scenarios.classic.runtime

import info.gianlucacosta.eighthbridge.core.facets.{HasSelectedSettable, HasStyleClasses}
import info.gianlucacosta.eighthbridge.fx.VisualLink
import info.gianlucacosta.eighthbridge.fx.facets.{HasHandlesSettable, HasOptionalCustomLabelCenterSettable}
import info.gianlucacosta.graphsj.scenarios.classic.ClassicAlgorithm

abstract class ClassicRuntimeLink(algorithm: ClassicAlgorithm)
  extends VisualLink
  with HasSelectedSettable
  with HasHandlesSettable
  with HasStyleClasses {
  override def styleClasses: Set[String] = {
    if (algorithm.partialSolutionLinks.contains(this))
      Set("partialSolution")
    else if (algorithm.fullSolutionLinks.contains(this))
      Set("fullSolution")
    else
      Set()
  }
}
