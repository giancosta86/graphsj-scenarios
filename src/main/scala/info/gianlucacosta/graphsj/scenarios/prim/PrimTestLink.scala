package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.eighthbridge.fx.graph.VisualLink
import info.gianlucacosta.eighthbridge.graphs.facets.{ArcBarbs, HasLabelSettable, HasOptionalCustomLabelCenterSettable}

class PrimTestLink
  extends VisualLink
  with HasLabelSettable
  with HasOptionalCustomLabelCenterSettable {
  override def arcBarbs: Option[ArcBarbs] =
    Some(ArcBarbs.Default)

  override def styleClasses: Set[String] =
    Set[String]()
}

