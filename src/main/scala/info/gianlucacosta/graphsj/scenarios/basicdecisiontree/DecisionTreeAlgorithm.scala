package info.gianlucacosta.graphsj.scenarios.basicdecisiontree

import info.gianlucacosta.graphsj.{Algorithm, OutputConsole}
import info.gianlucacosta.helios.fx.dialogs.InputDialogs
import info.gianlucacosta.lambdaprism.classification.basic.{Attribute, ClassificationProblem}

class DecisionTreeAlgorithm(problem: ClassificationProblem) extends Algorithm[DecisionTreeVertex, DecisionTreeLink, DecisionTreeGraph] {
  override def runStep(stepIndex: Int, graph: DecisionTreeGraph, console: OutputConsole): (DecisionTreeGraph, Boolean) = {
    console.writeHeader("General problem data")
    console.writeln()
    console.writeln(s"#(items) = ${problem.table.length}")
    console.writeln()
    problem.outputValues.foreach(outputValue => {
      console.writeln(s"\t#(${problem.outputAttribute} = ${outputValue}) = ${problem.getOutputCount(outputValue)}")
    })
    console.writeln()
    console.writeln()
    console.writeln(s"Entropy = ${problem.entropy}")
    console.writeln()
    console.writeln()


    val sortedOutputValues =
      problem.outputValues.toList.sorted

    val gainMap: Map[Attribute, Double] =
      problem.inputAttributes.map(attribute => {
        console.writeHeader(s"Attribute: ${attribute}")
        console.writeln()

        val attributeView =
          problem.getView(attribute)

        console.writeln(s"#(total known items) = ${attributeView.knownItemsTotalCount}")
        console.writeln()

        attributeView.attributeValues.toList.sorted.foreach(attributeValue => {
          console.writeln(s"\t#(${attribute} = ${attributeValue}) = ${attributeView.getKnownItemsAggregateCount(attributeValue)}")
          console.writeln()

          sortedOutputValues.foreach(outputValue => {
            console.writeln(s"\t\t#(${problem.outputAttribute} = ${outputValue}) = ${attributeView.getKnownItemsCount(attributeValue)(outputValue)}")
          })

          console.writeln()
        })


        console.writeln()
        console.writeln(s"#(total unknown items) = ${attributeView.unknownItemsTotalCount}")
        console.writeln()

        sortedOutputValues.foreach(outputValue => {
          console.writeln(s"\t#(${problem.outputAttribute} = ${outputValue}) = ${attributeView.getUnknownItemsCount(outputValue)}")
        })

        console.writeln()
        console.writeln()

        console.writeln(s"Entropy[${attribute}] = ${attributeView.entropy}")
        console.writeln()
        console.writeln(s"Gain[${attribute}] = ${attributeView.gain}")

        console.writeln()
        console.writeln()

        attribute -> attributeView.gain
      })
        .toMap

    console.writeln()
    console.writeHeader("Decision Tree")
    console.writeln()

    val attributeWithHighestGain: Attribute =
      gainMap.maxBy(_._2)._1

    console.writeln(s"The attribute having highest gain is: '${attributeWithHighestGain}'")
    console.writeln()
    console.writeln()


    val decisionTree =
      problem
        .getView(attributeWithHighestGain)
        .createDecisionTree()


    val solutionGraph =
      DecisionTreeGraph(
        decisionTree
      )


    val classificationInputOption =
      InputDialogs
        .askForString(
          s"Value of '${decisionTree.attribute}' in the instance to classify:",
          "?",
          "Classify instance"
        )


    console.writeHeader("Instance classification")
    console.writeln()

    classificationInputOption match {
      case Some(attributeValue) =>
        val classificationMap =
          decisionTree.classify(attributeValue)

        sortedOutputValues.foreach(outputValue => {
          val outputProbabilityPercentage =
            classificationMap.getOrElse(outputValue, 0.0) * 100

          console.writeln(s"Probability(${problem.outputAttribute} = ${outputValue}) = ${outputProbabilityPercentage}%")
        })

      case None =>
        console.writeln("Instance classification canceled")
    }

    console.writeln()

    (solutionGraph, false)
  }
}
