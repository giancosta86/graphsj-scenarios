package info.gianlucacosta.graphsj.scenarios.spp

import info.gianlucacosta.eighthbridge.fx.GraphCanvasController
import info.gianlucacosta.eighthbridge.fx.controller.{AdvancedLayoutEditing, Directed, LinkWeightEditing, VertexNameEditing}
import scalafx.geometry.Point2D

class SppDesignController extends GraphCanvasController[SppVertex, SppLink, SppGraph]
with AdvancedLayoutEditing[SppVertex, SppLink, SppGraph]
with VertexNameEditing[SppVertex, SppLink, SppGraph]
with LinkWeightEditing[SppVertex, SppLink, SppGraph]
with Directed[SppVertex, SppLink, SppGraph] {
  override protected def createNamedVertex(center: Point2D, vertexName: String): SppVertex =
    SppVertex(center = center, name = vertexName)


  override def createLink(sourceVertex: SppVertex, targetVertex: SppVertex): Option[SppGraph] = {
    val link = SppLink(
      weight = 0
    )

    Some(
      graph.addLink(sourceVertex, targetVertex, link)
    )
  }
}
