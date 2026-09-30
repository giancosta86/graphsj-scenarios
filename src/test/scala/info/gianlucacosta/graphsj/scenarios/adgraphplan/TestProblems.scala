package info.gianlucacosta.graphsj.scenarios.adgraphplan

import java.io.InputStreamReader
import javafx.embed.swing.JFXPanel

import info.gianlucacosta.graphsj.scenarios.adgraphplan.outcomes.{FeasiblePlan, UnsatisfiedGoal}
import info.gianlucacosta.graphsj.{BufferedOutputConsole, NopOutputConsole, OutputConsole}
import info.gianlucacosta.lambdaprism.logic.basic.formulas.Constant
import info.gianlucacosta.lambdaprism.planning.problem.Problem
import info.gianlucacosta.lambdaprism.planning.problem.parser.ProblemParser
import org.scalatest.{FlatSpec, Matchers}

class TestProblems extends FlatSpec with Matchers {
  "Robot problem" should "have a feasible plan" in {
    val outcome =
      runGraphPlanProblem("Robot.txt")._2

    outcome should be(FeasiblePlan)
  }


  "Elevator problem" should "have a feasible plan" in {
    val outcome =
      runGraphPlanProblem("Elevator.txt")._2

    outcome should be(FeasiblePlan)
  }


  "Plane and car problem" should "have a feasible plan" in {
    val outcome =
      runGraphPlanProblem("PlaneCar.txt")._2

    outcome should be(FeasiblePlan)
  }


  "Truck problem" should "have a feasible plan" in {
    val outcome =
      runGraphPlanProblem("Truck.txt")._2

    outcome should be(FeasiblePlan)
  }


  "The problem with an unsatisfied goal" should "be identified" in {
    val outcome =
      runGraphPlanProblem("UnsatisfiedGoal.txt")._2

    outcome should be(
      UnsatisfiedGoal(Constant("z"))
    )
  }


  "Mutex pairs problem" should "have a feasible plan" in {
    val outcome =
      runGraphPlanProblem("MutexPairs.txt")._2

    outcome should be(FeasiblePlan)
  }


  "Step mutex pairs in first step level" should "be identified" in {
    val finalGraph =
      runGraphPlanProblem("MutexPairs.txt")._1

    val firstStepLevel =
      finalGraph.stepLevels.head

    firstStepLevel.items.size should be(4)

    assertStepMutexes(
      firstStepLevel,
      "op1",
      Set("op2")
    )


    assertStepMutexes(
      firstStepLevel,
      "op2",
      Set("op1")
    )


    assertStepMutexes(
      firstStepLevel,
      "op3",
      Set()
    )


    assertStepMutexes(
      firstStepLevel,
      "NOP {s}",
      Set()
    )
  }


  "Proposition mutex pairs in second proposition level" should "be identified" in {
    val finalGraph =
      runGraphPlanProblem("MutexPairs.txt")._1

    val secondPropositionLevel =
      finalGraph.propositionLevels(1)

    assertPropositionMutexes(
      secondPropositionLevel,
      Constant("a"),
      Set(
        Constant("d")
      )
    )


    assertPropositionMutexes(
      secondPropositionLevel,
      Constant("a2"),
      Set(
        Constant("b"),
        Constant("b2")
      )
    )


    assertPropositionMutexes(
      secondPropositionLevel,
      Constant("b"),
      Set(
        Constant("a2")
      )
    )


    assertPropositionMutexes(
      secondPropositionLevel,
      Constant("b2"),
      Set(
        Constant("a2")
      )
    )


    assertPropositionMutexes(
      secondPropositionLevel,
      Constant("c"),
      Set(
        Constant("d")
      )
    )


    assertPropositionMutexes(
      secondPropositionLevel,
      Constant("d"),
      Set(
        Constant("a"),
        Constant("c")
      )
    )

    assertPropositionMutexes(
      secondPropositionLevel,
      Constant("s"),
      Set()
    )
  }



  "Step mutex pairs also considering mutex preconditions" should "be identified" in {
    val finalGraph =
      runGraphPlanProblem("MutexPreconditionSteps.txt")._1

    val secondStepLevel =
      finalGraph.stepLevels(1)

    assertStepMutexes(
      secondStepLevel,
      "NOP {a}",
      Set("op4", "NOP {c}")
    )


    assertStepMutexes(
      secondStepLevel,
      "op3",
      Set("op4", "NOP {c}")
    )


    assertStepMutexes(
      secondStepLevel,
      "NOP {b}",
      Set("op2", "op4", "NOP {c}")
    )


    assertStepMutexes(
      secondStepLevel,
      "op4",
      Set("NOP {a}", "op3", "NOP {b}")
    )


    assertStepMutexes(
      secondStepLevel,
      "NOP {c}",
      Set("op3", "NOP {a}", "NOP {b}")
    )


    assertStepMutexes(
      secondStepLevel,
      "NOP {s}",
      Set()
    )


    assertStepMutexes(
      secondStepLevel,
      "op1",
      Set("op2")
    )


    assertStepMutexes(
      secondStepLevel,
      "op2",
      Set("op1", "NOP {b}")
    )
  }


  "The outcome of a problem having a feasible plan" should "be printed out" in {
    val outputConsole =
      new BufferedOutputConsole

    val outcome =
      runGraphPlanProblem("Robot.txt", outputConsole)._2


    outcome should be(FeasiblePlan)
    outputConsole.text should be("The problem has a feasible solution plan!\n")
  }


  "The outcome of a problem having an unsatisfied goal" should "be printed out" in {
    val outputConsole =
      new BufferedOutputConsole

    val outcome =
      runGraphPlanProblem("UnsatisfiedGoal.txt", outputConsole)._2


    outcome should be(UnsatisfiedGoal(Constant("z")))
    outputConsole.text should be("There is no feasible plan. Unsatisfied goal: z\n")
  }

  def assertStepMutexes(stepLevel: StepLevel, stepName: String, expectedMutexNames: Set[String]): Unit = {
    val step =
      stepLevel
        .steps
        .filter(_.action.name == stepName)
        .head

    val stepVertex =
      stepLevel.getVertex(step)

    val stepMutexNames =
      stepVertex
        .mutexes
        .map(_.action.name)

    stepMutexNames should be(expectedMutexNames)
  }


  def assertPropositionMutexes(propositionLevel: PropositionLevel, proposition: Proposition, expectedMutexes: Set[Proposition]): Unit = {
    val propositionVertex =
      propositionLevel.getVertex(proposition)

    propositionVertex.mutexes should be(expectedMutexes)
  }


  def runGraphPlanProblem(
                           problemFileName: String,
                           outputConsole: OutputConsole = NopOutputConsole
                         ): (ConstructionGraph, GraphPlanOutcome) = {

    //This is required to initialize the JavaFX toolkit in tests
    new JFXPanel

    val problem =
      readProblem(problemFileName)


    val algorithm =
      new GraphPlanAlgorithm(problem)


    val initialGraph =
      ConstructionGraph(problem)


    val finalConstructionGraph =
      algorithm.fullRun(
        initialGraph,
        outputConsole
      )


    val outcome = GraphPlanAlgorithm.evaluateOutcome(
      problem,
      finalConstructionGraph
    )

    (finalConstructionGraph, outcome)
  }


  def readProblem(problemFileName: String): Problem = {
    val inputStream =
      getClass.getResourceAsStream(problemFileName)

    try {
      val reader =
        new InputStreamReader(inputStream)

      ProblemParser.parse(reader)
    } finally {
      inputStream.close()
    }
  }
}
