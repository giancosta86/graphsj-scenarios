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


  override def visualCopy(center: Point2D, selected: Boolean): this.type =
    copy(
      center = center,
      selected = selected
    ).asInstanceOf[this.type]

  override def styleClasses: List[String] =
    List()
}
