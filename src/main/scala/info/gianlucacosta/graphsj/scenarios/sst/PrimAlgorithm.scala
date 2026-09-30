package info.gianlucacosta.graphsj.scenarios.sst

import info.gianlucacosta.eighthbridge.graphs.point2point.visual.VisualGraph
import info.gianlucacosta.graphsj.{Algorithm, OutputConsole}
import info.gianlucacosta.helios.fx.dialogs.InputDialogs
import info.gianlucacosta.helios.mathutils.Numbers


class PrimAlgorithm[G <: VisualGraph[PrimVertex, PrimLink, G]] extends Algorithm[PrimVertex, PrimLink, G] {
  private var vList: List[PrimVertex] = _
  private var wList: List[PrimVertex] = _
  private var vBar: PrimVertex = _

  private var treeWeight: Double = 0

  private var treeBindings: List[(PrimVertex, PrimVertex)] = _

  val verbose = true //This basic algorithm version is always verbose


  private def edgeBindingsAsString(edgeBindings: List[(PrimVertex, PrimVertex)]): String = {
    edgeBindings
      .map(binding => s"{${binding._1.name}, ${binding._2.name}}")
      .mkString("[", ", ", "]")
  }


  private def getMinWeightBetween(graph: G, vertexes: Set[PrimVertex]): Double = {
    graph.getLinksBetween(vertexes)
      .map(_.weight)
      .toList
      .sorted
      .headOption
      .getOrElse(Double.PositiveInfinity)
  }


  override def runStep(stepIndex: Int, graph: G, console: OutputConsole): (G, Boolean) = {
    stepIndex match {
      case 0 =>
        init(graph, console)

      case _ =>
        runStandardStep(stepIndex, graph, console)
    }
  }


  private def init(graph: G, console: OutputConsole): (G, Boolean) = {
    if (graph.unlinkedVertexes.nonEmpty) {
      throw new RuntimeException("Every vertex in the graph must be connected!")
    }

    vList = graph
      .vertexes
      .toList
      .sortBy(_.text)


    val v1input = InputDialogs.askForItem("Start vertex:", vList)
    if (v1input.isEmpty) {
      console.writeln("*** REQUIRED INPUT NOT PROVIDED ***")
      return (graph, false)
    }


    val v1 = v1input.get

    wList = List(v1)
    vList =
      vList
        .filter(_ != v1)
        .map(vertex =>
          vertex.copy(
            bestVertex = Some(v1),
            distanceFromBestVertex = Some(getMinWeightBetween(graph, Set(v1, vertex)))
          )
        )


    vBar = v1
    treeBindings = List()


    if (verbose) {
      console.writeHeader("Legend")
      console.writeln()
      console.writeln("V = The graph vertexes")
      console.writeln("W = Vertexes belonging to the tree in the current step")
      console.writeln("E = Edges belonging to the tree in the current step")
      console.writeln("Vbar = Vertex added to the tree in the current step")
      console.writeln()

      console.writeHeader("Before step 1")


      console.writeln(s"W = ${wList.map(_.name).mkString("[", ", ", "]")}")
      console.writeln(s"V \\ W = ${vList.map(_.name).mkString("[", ", ", "]")}")
      console.writeln(s"E = ${edgeBindingsAsString(treeBindings)}")

      console.writeln()
      console.writeln()
    }

    (
      graph.replaceVertex(
        v1.copy(
          bestVertex = Some(v1),
          distanceFromBestVertex = Some(0)
        )
      )
        .replaceVertexes(
          vList.toSet
        ),

      true
      )
  }


  private def runStandardStep(stepIndex: Int, graph: G, console: OutputConsole): (G, Boolean) = {
    if (verbose) {
      console.writeHeader("Step " + stepIndex)
      console.writeln()
    }


    vList = vList.map(vertex => {
      val distanceFromVBar = getMinWeightBetween(graph, Set(vBar, vertex))

      if (distanceFromVBar < vertex.distanceFromBestVertex.get) {
        vertex.copy(
          bestVertex = Some(vBar),
          distanceFromBestVertex = Some(distanceFromVBar)
        )
      } else {
        vertex
      }
    })


    //Now, let's determine the "vBar" vertex
    var minBest = Double.PositiveInfinity
    vBar = null
    var treeBinding: (PrimVertex, PrimVertex) = null

    vList.foreach(vertex => {
      val hopWeight = vertex.distanceFromBestVertex.get
      if (hopWeight < minBest) {
        vBar = vertex
        minBest = hopWeight
        treeBinding = (vertex.bestVertex.get, vBar)
      }
    })


    if (vBar == null) {
      throw new RuntimeException("Could not determine vBar! Error in the algorithm!")
    }


    val link =
      graph.getLinksBetween(Set(vBar.bestVertex.get, vBar))
        .filter(link => link.weight == minBest)
        .head

    val newLink =
      link.copy(
        styleClasses = List("solution")
      )

    val newGraph =
      graph
        .replaceVertexes(
          vList
            .map(vVertex => graph.getVertex(vVertex.id).get.copy(
              bestVertex = vVertex.bestVertex,
              distanceFromBestVertex = vVertex.distanceFromBestVertex
            ))
            .toSet
        )
        .replaceLink(
          newLink
        )


    //Adding vBar to the "W" set, and removing it from the "V" set
    wList = (wList ::: List(vBar)).sortBy(_.name)
    vList = vList.filter(_ != vBar)

    //Also adding the corresponding link to E
    treeBindings = treeBindings ::: List(treeBinding)

    //Updating the tree weight
    treeWeight += minBest

    if (verbose) {
      console.writeln("At the end of the step:")
      console.writeln()
      console.writeln(s"Vbar = ${vBar.name}")
      console.writeln(s"W = ${wList.map(_.name).mkString("[", ", ", "]")}")
      console.writeln(s"V \\ W = ${vList.map(_.name).mkString("[", ", ", "]")}")
      console.writeln(s"E = ${edgeBindingsAsString(treeBindings)}")

      console.writeln()
      console.writeln()
    }

    if (vList.isEmpty) {
      console.writeln(s"The branches of the spanning tree are: ${edgeBindingsAsString(treeBindings)}")
      console.writeln()
      console.writeln(s"The total weight of the spanning tree is: ${Numbers.smartString(treeWeight)}")

      (newGraph, false)
    } else {
      (newGraph, true)
    }
  }
}
