package info.gianlucacosta.graphsj.scenarios.prim

import info.gianlucacosta.graphsj.scenarios.classic.runtime.ClassicRuntimeVertex

class PrimRuntimeVertex(algorithm: PrimAlgorithm) extends ClassicRuntimeVertex(algorithm) {

  override def label: String =
    name //TODO! Update this based on the algorithm!
}
