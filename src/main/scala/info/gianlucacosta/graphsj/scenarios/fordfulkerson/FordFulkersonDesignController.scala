package info.gianlucacosta.graphsj.scenarios.fordfulkerson

import info.gianlucacosta.eighthbridge.fx.controller.{AdvancedLayoutEditing, Directed, LinkWeightEditing, VertexNameEditing}
import info.gianlucacosta.eighthbridge.fx.{GraphCanvasController, VisualGraph}
import scalafx.geometry.Point2D

class FordFulkersonDesignController extends GraphCanvasController[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph]
  with AdvancedLayoutEditing[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph]
  with VertexNameEditing[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph]
  with LinkWeightEditing[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph]
  with Directed[FordFulkersonVertex, FordFulkersonLink, FordFulkersonGraph] {
  override protected def createNamedVertex(center: Point2D, vertexName: String): FordFulkersonVertex =
    FordFulkersonVertex(center = center, name = vertexName)


  override def createLink(sourceVertex: FordFulkersonVertex, targetVertex: FordFulkersonVertex): Option[FordFulkersonGraph] = {
    val link = FordFulkersonLink(
      weight = 0
    )

    Some(
      graph.addLink(sourceVertex, targetVertex, link)
    )
  }
}
