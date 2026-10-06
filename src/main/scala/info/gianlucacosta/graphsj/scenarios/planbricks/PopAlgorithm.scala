package info.gianlucacosta.graphsj.scenarios.planbricks

import info.gianlucacosta.graphsj.scenarios.planbricks.outcomes.{Threat, UnsatisfiedPrecondition, ValidPlan}
import info.gianlucacosta.graphsj.{Algorithm, OutputConsole}
import info.gianlucacosta.lambdaprism.logic.basic.formulas.Literal
import info.gianlucacosta.lambdaprism.planning.problem.Step


object PopAlgorithm {
  def getOutcome(graph: PopGraph): PopOutcome = {
    val unsatisfiedPreconditions: Set[(StepVertex, Literal)] =
      graph
        .vertexes
        .flatMap(vertex => {
          vertex.unsatisfiedPreconditions.map(precondition =>
            vertex -> precondition
          )
        })


    unsatisfiedPreconditions.headOption.map(unsatisfiedPrecondition =>
      UnsatisfiedPrecondition(unsatisfiedPrecondition._1, unsatisfiedPrecondition._2)
    ).getOrElse {
      val threatVertexOption =
        graph.vertexes
          .find(_.isThreat)

      threatVertexOption.map(threatVertex =>
        Threat(threatVertex)
      ).getOrElse(
        ValidPlan
      )
    }
  }
}

class PopAlgorithm extends Algorithm[StepVertex, PopLink, PopGraph] {
  override def runStep(stepIndex: Int, graph: PopGraph, console: OutputConsole): (PopGraph, Boolean) = {
    console.writeHeader("ENVIRONMENT")

    if (graph.environment.bindingGroups.nonEmpty) {
      console.writeln(graph.environment)
    } else {
      console.writeln("(empty environment)")
    }
    console.writeln()


    console.writeHeader("OUTCOME")

    PopAlgorithm.getOutcome(graph) match {
      case ValidPlan =>
        console.writeln("The plan is valid!")
        console.writeln()

        val linearPlan: List[Step] =
          graph
            .fold(List[Step]())((cumulatedSteps, vertex, _) => {
              vertex.step :: cumulatedSteps
            })
            .reverse


        console.writeHeader("A LINEAR PLAN")
        console.writeln()

        linearPlan.zipWithIndex.foreach {
          case (step, index) =>
            val signatureWithBoundVariables =
              step.replaceVariablesInSignature(graph.environment.boundVariables)

            val canvasSignature =
              step.signature

            val canvasNotice =
              if (signatureWithBoundVariables != canvasSignature)
                s"  (in the canvas: ${canvasSignature})"
              else
                ""

            console.writeln(s"${index + 1} - ${signatureWithBoundVariables}${canvasNotice}")
        }


      case UnsatisfiedPrecondition(stepVertex, precondition) =>
        console.writeln(s"Unsatisfied precondition '${precondition}' for step '${stepVertex.step}'")

      case Threat(stepVertex) =>
        console.writeln(s"Step '${stepVertex.step}' is threatening causal links")
    }

    (graph, false)
  }
}
