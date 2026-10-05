package info.gianlucacosta.graphsj.scenarios.planbricks

import info.gianlucacosta.eighthbridge.fx.canvas.VisualGraph

import java.util.UUID
import info.gianlucacosta.eighthbridge.graphs.point2point.ArcBinding
import info.gianlucacosta.lambdaprism.logic.basic.matching.{Environment, SymbolicMatch}
import info.gianlucacosta.lambdaprism.planning.problem.Problem
import scalafx.geometry.Point2D


object PopGraph {
  def apply(problem: Problem): PopGraph = {
    val startStep =
      problem.startAction.reify(Map())

    val startVertex =
      new StepVertex(
        startStep,

        new Point2D(700, 130)
      )


    val goalStep =
      problem.goalAction.reify(Map())

    val goalVertex =
      new StepVertex(
        goalStep,

        new Point2D(700, 1850)
      )


    new PopGraph(
      startVertex,
      goalVertex,

      vertexes = Set(
        startVertex,
        goalVertex
      )
    )
      .makeConsistent()
  }


  private def updateTemporalLinks(graph: PopGraph): PopGraph = {
    val tempGraph =
      graph
        .mainStepVertexes
        .foldLeft(graph)((currentGraph, currentVertex) => {
          updateTemporalLinkToGoal(
            updateTemporalLinkFromStart(
              currentGraph,
              currentVertex
            ),

            currentVertex
          )
        })


    updateStartToGoalTemporalLink(tempGraph)
  }


  private def updateTemporalLinkFromStart(graph: PopGraph, vertex: StepVertex): PopGraph = {
    val enteringArcs =
      graph.getEnteringArcs(vertex)


    enteringArcs.size match {
      case 0 =>
        graph.addLink(
          graph.startVertex,
          vertex,
          new TemporalLink
        )

      case 1 =>
        graph

      case _ =>
        val startTemporalLinkOption =
          graph.getArcsBetween(
            graph.startVertex,
            vertex
          )
            .find(_.isInstanceOf[TemporalLink])

        startTemporalLinkOption.map(startTemporalLink =>
          graph
            .removeLink(startTemporalLink)
        ).getOrElse(
          graph
        )
    }
  }


  private def updateTemporalLinkToGoal(graph: PopGraph, vertex: StepVertex): PopGraph = {
    val exitingArcs =
      graph.getExitingArcs(vertex)


    exitingArcs.size match {
      case 0 =>
        graph.addLink(
          vertex,
          graph.goalVertex,
          new TemporalLink
        )

      case 1 =>
        graph

      case _ =>
        val goalTemporalLinkOption =
          graph.getArcsBetween(
            vertex,
            graph.goalVertex
          )
            .find(_.isInstanceOf[TemporalLink])


        goalTemporalLinkOption.map(goalTemporalLink =>
          graph
            .removeLink(goalTemporalLink)
        ).getOrElse(
          graph
        )
    }
  }


  private def updateStartToGoalTemporalLink(graph: PopGraph): PopGraph = {
    val startVertex =
      graph.startVertex

    val goalVertex =
      graph.goalVertex


    if (graph.mainStepVertexes.isEmpty) {
      graph.addLink(
        startVertex,
        goalVertex,
        new TemporalLink
      )
    } else {
      val startToGoalTemporalLinkOption =
        graph
          .getArcsBetween(
            startVertex,
            goalVertex
          )
          .find(_.isInstanceOf[TemporalLink])

      startToGoalTemporalLinkOption.map(startToGoalTemporalLink =>
        graph
          .removeLink(startToGoalTemporalLink)
      ).getOrElse(
        graph
      )
    }
  }


  private def updatePreconditions(graph: PopGraph): PopGraph = {
    val updatedVertexes: Set[StepVertex] =
      graph
        .vertexes
        .map(vertex => {
          val enteringCausalLinks =
            graph
              .getEnteringArcs(vertex)
              .filter(_.isInstanceOf[CausalLink])
              .map(_.asInstanceOf[CausalLink])

          val satisfiedPreconditions =
            enteringCausalLinks.map(_.precondition)

          vertex.copy(satisfiedPreconditions = satisfiedPreconditions)
        })

    graph.copy(vertexes = updatedVertexes)
  }


  private def updateThreats(graph: PopGraph): PopGraph = {
    val sequentialChains =
      getSequentialChains(graph)


    val linkThreats: Map[CausalLink, Set[StepVertex]] =
      graph
        .mainStepVertexes
        .flatMap(vertex => {
          val vertexChain =
            sequentialChains(vertex.id)

          graph
            .causalLinks
            .flatMap(causalLink => {
              val causalLinkChain =
                sequentialChains(causalLink.id)

              if (
                causalLinkChain.subsetOf(vertexChain) ||
                  vertexChain.subsetOf(causalLinkChain)
              ) {
                List()
              } else {
                if (vertex.step.effects.exists(effect => {
                  val threateningMatchOption =
                    SymbolicMatch.unify(
                      effect,
                      ~causalLink.precondition
                    )

                  val isThreat =
                    threateningMatchOption.exists(threateningMatch => {
                      threateningMatch.environment.isCompatibleWith(graph.environment)
                    })

                  isThreat
                })) {
                  List(causalLink -> vertex)
                } else {
                  List()
                }
              }
            })
        })
        .groupBy(_._1)
        .mapValues(_.map(_._2))


    val threateningVertexes: Set[StepVertex] =
      linkThreats
        .values
        .flatten
        .toSet


    val updatedMainVertexes =
      graph.mainStepVertexes.map(vertex =>
        vertex.copy(isThreat = threateningVertexes.contains(vertex))
      )


    val updatedCausalLinks: Set[PopLink] =
      graph
        .causalLinks
        .map(causalLink =>
          causalLink
            .copy(threats = linkThreats.getOrElse(causalLink, Set()))
        )


    graph
      .replaceVertexes(updatedMainVertexes)
      .replaceLinks(updatedCausalLinks)
  }


  /**
    * Both vertexes and links are assigned a <em>sequential chain</em>, that is a set of UUIDs defined as follows:
    *
    * <ul>
    * <li>
    * The chain of a <b>vertex</b> consists of all the ids obtained by merging the chains
    * of every arc entering it, plus the vertex's id
    * </li>
    * <li>
    * The chain of an arc is the chain of the vertex from which the arc exits, plus the arc's id
    * </li>
    *
    * @param graph
    * @return
    */
  private def getSequentialChains(graph: PopGraph): Map[UUID, Set[UUID]] = {
    graph.fold(Map[UUID, Set[UUID]]())((cumulatedChains, enteringArcs, vertex, exitingArcs, _) => {
      val mergedEnteringChain: Set[UUID] =
        enteringArcs
          .flatMap(enteringArc =>
            cumulatedChains(enteringArc.id)
          )


      val vertexChain: Set[UUID] =
        mergedEnteringChain + vertex.id


      val exitingChains: Set[(UUID, Set[UUID])] =
        exitingArcs.map(exitingArc =>
          exitingArc.id ->
            (vertexChain + exitingArc.id)
        )


      cumulatedChains +
        (vertex.id -> vertexChain) ++
        exitingChains
    })
  }
}


case class PopGraph private(
                             startVertex: StepVertex,
                             goalVertex: StepVertex,
                             vertexes: Set[StepVertex] = Set(),
                             links: Set[PopLink] = Set(),
                             bindings: Set[ArcBinding] = Set()
                           ) extends VisualGraph[StepVertex, PopLink] {

  @transient
  lazy val temporalLinks: Set[TemporalLink] =
    links
      .filter(_.isInstanceOf[TemporalLink])
      .map(_.asInstanceOf[TemporalLink])


  @transient
  lazy val causalLinks: Set[CausalLink] =
    links
      .filter(_.isInstanceOf[CausalLink])
      .map(_.asInstanceOf[CausalLink])


  @transient
  lazy val environment: Environment =
    causalLinks.foldLeft(new Environment)((environment, causalLink) => {
      (environment ++ causalLink.matchEnvironment).get
    })


  val reservedVertexes: Set[StepVertex] =
    Set(startVertex, goalVertex)


  val mainStepVertexes: Set[StepVertex] =
    vertexes -- reservedVertexes


  val reservedLinks =
    getExitingArcs(startVertex)
      .filter(_.isInstanceOf[TemporalLink]) ++
      getEnteringArcs(goalVertex)
        .filter(_.isInstanceOf[TemporalLink])


  override protected def graphCopy(vertexes: Set[StepVertex], links: Set[PopLink], bindings: Set[ArcBinding]): this.type =
    copy(
      vertexes = vertexes,
      links = links,
      bindings = bindings
    ).asInstanceOf[this.type]


  def makeConsistent(): PopGraph = {
    PopGraph.updateThreats(
      PopGraph.updatePreconditions(
        PopGraph.updateTemporalLinks(
          this
        )
      )
    )
  }
}
