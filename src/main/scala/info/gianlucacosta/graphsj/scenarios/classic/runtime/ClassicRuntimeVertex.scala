package info.gianlucacosta.graphsj.scenarios.classic.runtime

import info.gianlucacosta.eighthbridge.core.facets.{HasLabel, HasLabelSettable, HasNameSettable, HasSelectedSettable, HasStyleClasses}
import info.gianlucacosta.eighthbridge.fx.VisualVertex
import info.gianlucacosta.eighthbridge.fx.facets.HasCenterSettable
import info.gianlucacosta.graphsj.scenarios.classic.ClassicAlgorithm

class ClassicRuntimeVertex(algorithm: ClassicAlgorithm)
  extends VisualVertex
    with HasCenterSettable
    with HasSelectedSettable
    with HasStyleClasses {

  override def styleClasses: Set[String] = {
    if (algorithm.partialSolutionVertexes.contains(this))
      Set("partialSolution")
    else if (algorithm.fullSolutionVertexes.contains(this))
      Set("fullSolution")
    else
      Set()
  }
}

