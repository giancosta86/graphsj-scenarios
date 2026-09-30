package info.gianlucacosta.graphsj.scenarios.adgraphplan

import java.util.UUID

import scalafx.geometry.{Dimension2D, Point2D}


object PropositionVertex {
  def formatProposition(proposition: Proposition, mutexes: Set[Proposition]): String = {
    val mutexString: String =
      if (mutexes.nonEmpty)
        s"\n\n${ConstructionVertex.MutexString}\n\n${mutexes.mkString("\n")}"
      else
        ""
    s"${proposition}" + mutexString
  }
}

case class PropositionVertex(
                              proposition: Proposition,

                              mutexes: Set[Proposition],

                              dimension: Dimension2D,

                              center: Point2D,

                              selected: Boolean = false,

                              id: UUID = UUID.randomUUID()
                            ) extends ConstructionVertex {

  require(proposition.isPositive)


  override def text: String =
    PropositionVertex.formatProposition(proposition, mutexes)


  override val styleClasses: List[String] =
    List("propositionVertex")


  override def visualCopy(center: Point2D, selected: Boolean): PropositionVertex =
    copy(
      center = center,
      selected = selected
    )
}