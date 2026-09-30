package info.gianlucacosta.graphsj.scenarios.planbricks

import info.gianlucacosta.eighthbridge.fx.canvas.GraphCanvas
import info.gianlucacosta.eighthbridge.fx.canvas.basic.BasicVertexNodeMixin
import info.gianlucacosta.lambdaprism.logic.basic.formulas.Literal
import info.gianlucacosta.lambdaprism.planning.problem.Step

import scala.collection.JavaConversions._
import scalafx.Includes._
import scalafx.beans.property.ReadOnlyDoubleProperty
import scalafx.geometry.Pos
import scalafx.scene.Group
import scalafx.scene.control.Label
import scalafx.scene.layout.VBox
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.TextAlignment


class StepVertexNode(
                      val graphCanvas: GraphCanvas[StepVertex, PopLink, PopGraph],
                      step: Step
                    )
  extends Group with BasicVertexNodeMixin[StepVertex, PopLink, PopGraph] {

  private val signatureLabel =
    new Label {
      styleClass.add("signature")

      textAlignment =
        TextAlignment.Center

      alignment =
        Pos.Center

      maxWidth =
        Double.MaxValue

      maxHeight =
        Double.MaxValue
    }


  private val preconditionsBox =
    new LiteralsBox(
      step.preconditions,
      "Preconditions:",
      "preconditions"
    ) {
      override def getLiteralStyleClass(literal: Literal): Some[String] =
        Some(
          if (vertex.unsatisfiedPreconditions.contains(literal))
            "unsatisfied"
          else
            "satisfied"
        )
    }


  private val effectsBox =
    new LiteralsBox(
      step.effects,
      "Effects:",
      "effects"
    ) {
      override def getLiteralStyleClass(literal: Literal): Option[String] =
        None
    }


  private val layoutBox =
    new VBox {
      children.addAll(
        preconditionsBox,
        signatureLabel,
        effectsBox
      )

      layoutX <==
        centerX - width / 2

      layoutY <==
        centerY - height / 2

      spacing =
        7
    }


  private val bodyRectangle =
    new Rectangle {
      val padding =
        10

      width <==
        layoutBox.width + 2 * padding

      height <==
        layoutBox.height + 2 * padding

      layoutX <==
        layoutBox.layoutX - padding

      layoutY <==
        layoutBox.layoutY - padding

      styleClass.add("body")
    }


  children.addAll(
    bodyRectangle,
    layoutBox
  )


  override def width: ReadOnlyDoubleProperty =
    bodyRectangle.width


  override def height: ReadOnlyDoubleProperty =
    bodyRectangle.height


  override def render(): Unit = {
    super.render()

    preconditionsBox.render()

    signatureLabel.text =
      vertex.step.signature

    effectsBox.render()
  }
}
