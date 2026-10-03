package info.gianlucacosta.graphsj.scenarios.spp

import info.gianlucacosta.eighthbridge.graphs.point2point.visual.VisualGraph
import info.gianlucacosta.graphsj.{Algorithm, OutputConsole}
import info.gianlucacosta.helios.fx.dialogs.InputDialogs
import info.gianlucacosta.helios.mathutils.Numbers


class SppAlgorithm[G <: VisualGraph[SppVertex, SppLink, G]] extends Algorithm[SppVertex, SppLink, G] {
  private var vList: List[SppVertex] = _;
  private var pathVertexes: List[SppVertex] = _;
  private var pathEdges: List[(SppVertex, SppVertex)] = _;
  private var vBar: SppVertex = _;

  val verbose = true //This basic algorithm version is always verbose

  //TODO! Duplicated!
  private def edgeBindingsAsString(edgeBindings: List[(SppVertex, SppVertex)]): String = {
    edgeBindings
      .map(binding => s"{${binding._1.name}, ${binding._2.name}}")
      .mkString("[", ", ", "]")
  }

  //TODO! Duplicated!
  private def getMinWeightBetween(graph: G, sourceVertex: SppVertex, targetVertex: SppVertex): Double = {
    graph.getArcsBetween(sourceVertex, targetVertex)
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
    //TODO! Duplicated!
    if (graph.rootVertexes.isEmpty) {
      throw new RuntimeException("No vertex defined!")
    }

    if (graph.unlinkedVertexes.nonEmpty) {
      throw new RuntimeException("Every vertex in the graph must be connected!")
    }

    vList = graph
      .vertexes
      .toList
      .sortBy(_.text)


    val startVertexInput = InputDialogs.askForItem("Start vertex:", vList)
    if (startVertexInput.isEmpty) {
      console.writeln("*** REQUIRED INPUT NOT PROVIDED ***")
      return (graph, false)
    }

    val startVertex = startVertexInput.get

    pathVertexes = List(startVertex)

    vList =
      vList
        .filter(_ != startVertex)
        .map(vertex =>
          vertex.copy(
            previousVertex = Some(startVertex),
            pathLength = Some(getMinWeightBetween(graph, startVertex, vertex))
          )
        )

    pathEdges = List()

    vBar = startVertex.copy(
      previousVertex = Some(startVertex),
      pathLength = Some(0)
    )

    if (verbose) {
        console.writeHeader("Legend");
        console.writeln();
        console.writeln("Vbar", "Vertex added to the shortest path in the current step");
        console.writeln();
        console.writeHeader("Before step 1");
        console.writeln();
        console.writeln(s"Path vertexes = ${pathVertexes.map(_.name).mkString("[", ", ", "]")}");
        console.writeln(s"Path edges = ${edgeBindingsAsString(pathEdges)}");
        console.writeln();
    }

    (
      graph.replaceVertex(
        vBar
      )
        .replaceVertexes(
          vList.toSet
        ),

      true
    )
  }


  private def runStandardStep(stepIndex: Int, graph: G, console: OutputConsole): (G, Boolean) = {
    if (verbose) {
      console.writeHeader("Step " + stepIndex);
    }

    vList = vList.map(vertex => {
      var currentPathLength = vBar.pathLength.get
      var minWeightFromVBarToVertex = getMinWeightBetween(graph, vBar, vertex)

      val recomputedPathLength = currentPathLength + minWeightFromVBarToVertex

      if (recomputedPathLength < vertex.pathLength.get) {
        vertex.copy(
          previousVertex = Some(vBar),
          pathLength = Some(recomputedPathLength)
        )
      } else {
        vertex
      }
    })


    var minPathLength = Double.PositiveInfinity;

    vList.foreach { vertex =>
      if (vertex.pathLength.get < minPathLength) {
        vBar = vertex;
        minPathLength = vertex.pathLength.get;
      }
    }

    //I update the sets
    pathVertexes = pathVertexes ::: List(vBar);
    vList = vList.filter(_ != vBar)

    val link: SppLink =
      graph.getLinksBetween(Set(vBar.previousVertex.get, vBar))
        .filter(link =>
          link.weight == getMinWeightBetween(graph, vBar.previousVertex.get, vBar)
        )
        .head

    val newLink =
      link.copy(
        styleClasses = List("solution")
      )

    pathEdges = pathEdges ::: List((vBar.previousVertex.get, vBar));


    if (verbose) {
        console.writeln();
        console.writeln("At the end of the step:");
        console.writeln();
        console.writeln(s"Vbar = ${vBar.name}");
        console.writeln(s"Path vertexes = ${pathVertexes.map(_.name).mkString("[", ", ", "]")}");
        console.writeln(s"Path edges = ${edgeBindingsAsString(pathEdges)}");
        console.writeln();
    }

    val newGraph =
      graph
        .replaceVertexes(
          vList
            .toSet
        )
        .replaceLink(
          newLink
        )

    (newGraph, vList.nonEmpty)
  }
}
