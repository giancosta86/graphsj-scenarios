package info.gianlucacosta.graphsj.scenarios.basicdecisiontree

import java.util.UUID

import info.gianlucacosta.lambdaprism.classification.basic.DecisionTreeLeaf

import scalafx.geometry.Point2D

object TreeLeafVertex {
  def formatLeaf(treeLeaf: DecisionTreeLeaf): String =
    treeLeaf.toString
}


case class TreeLeafVertex(
                           treeLeaf: DecisionTreeLeaf,
                           center: Point2D,
                           selected: Boolean = false,
                           id: UUID = UUID.randomUUID()
                         ) extends DecisionTreeVertex {

  override def text: String =
    TreeLeafVertex.formatLeaf(treeLeaf)


  override def visualCopy(center: Point2D, selected: Boolean): TreeLeafVertex =
    copy(
      center = center,
      selected = selected
    )

  override def styleClasses: List[String] =
    List()
}
