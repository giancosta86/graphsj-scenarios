package info.gianlucacosta.graphsj.scenarios.planbricks

import info.gianlucacosta.lambdaprism.logic.basic.formulas.Literal

import scala.collection.JavaConversions._
import scalafx.Includes._
import scalafx.geometry.Insets
import scalafx.scene.layout.FlowPane
import scalafx.scene.text.Text


private abstract class LiteralsBox(
                                    literals: List[Literal],
                                    header: String,
                                    dedicatedStyleClass: String
                                  ) extends FlowPane {
  padding =
    Insets(5)

  vgap =
    4

  hgap =
    8

  prefWrapLength =
    420


  styleClass.add(dedicatedStyleClass)


  initLabels()


  def initLabels(): Unit = {
    if (literals.nonEmpty) {
      val headerLabel =
        new Text(header) {
          styleClass.add("literalsHeader")
        }

      children.add(headerLabel)

      val literalLabels =
        literals
          .map(literal => {
            new Text(literal.toString)
          })

      literalLabels.foreach(literalLabel =>
        children.add(literalLabel)
      )
    }
  }


  def render(): Unit = {
    literals.zipWithIndex.foreach {
      case (literal, literalIndex) =>
        val literalStyleClassOption =
          getLiteralStyleClass(literal)

        val literalNode =
          children(1 + literalIndex)

        literalNode.styleClass.clear()

        literalStyleClassOption.foreach(literalStyleClass => {
          literalNode.styleClass.add(
            literalStyleClass
          )
        })
    }
  }

  def getLiteralStyleClass(literal: Literal): Option[String]
}