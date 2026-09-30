package info.gianlucacosta.graphsj.scenarios.planbricks


private object PopLinkType {
  val TemporalLink =
    PopLinkType("Temporal link")

  val CausalLink =
    PopLinkType("Causal link")

  val All = List(
    CausalLink,
    TemporalLink
  )
}


private case class PopLinkType private(name: String) {
  override def toString: String =
    name
}
