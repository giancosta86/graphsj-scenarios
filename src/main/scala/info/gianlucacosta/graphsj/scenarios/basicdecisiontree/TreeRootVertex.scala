package info.gianlucacosta.graphsj.scenarios.basicdecisiontree

import java.util.UUID

import info.gianlucacosta.lambdaprism.classification.basic.{Attribute, DecisionTree}

import scalafx.geometry.Point2D


object TreeRootVertex {
  def formatAttribute(attribute: Attribute): String =
    attribute
}

case class TreeRootVertex(
                           decisionTree: DecisionTree,
                           center: Point2D,
                           selected: Boolean = false,
                           id: UUID = UUID.randomUUID()
                         ) extends DecisionTreeVertex {

  override def text: String =
    TreeRootVertex.formatAttribute(decisionTree.attribute)

  override def styleClasses: Set[String] =
    Set()

  override def visualCopy(
                           text: String,
                           center: Point2D,
                           selected: Boolean,
                           styleClasses: Set[String]
                         ): TreeRootVertex.this.type =
    copy(
      center = center,
      selected = selected
    ).asInstanceOf[this.type]
}
