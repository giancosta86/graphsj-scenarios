package info.gianlucacosta.graphsj.scenarios.planbricks.outcomes

import info.gianlucacosta.graphsj.scenarios.planbricks.{PopOutcome, StepVertex}
import info.gianlucacosta.lambdaprism.logic.basic.formulas.Literal

case class UnsatisfiedPrecondition(
                                    stepVertex: StepVertex,
                                    precondition: Literal
                                  ) extends PopOutcome
