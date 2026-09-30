package info.gianlucacosta.graphsj.scenarios.adgraphplan

import info.gianlucacosta.eighthbridge.fx.canvas.basic.BasicVertexNode
import info.gianlucacosta.eighthbridge.fx.canvas.basic.BasicVertexNode.DimensionQuery

import scalafx.geometry.{Dimension2D, Point2D}

trait ConstructionLevel[TItem, TVertex <: ConstructionVertex] {
  def items: List[TItem]


  private val mutexMap: Map[TItem, Set[TItem]] =
    createMutexMap()


  protected def createMutexMap(): Map[TItem, Set[TItem]]


  private val itemDimensions: Map[TItem, Dimension2D] =
    items.zip(
      BasicVertexNode.getDimensions(
        GraphPlanScenario.Stylesheets,

        items.map(item =>
          DimensionQuery(
            getItemVertexLabel(
              item,
              mutexMap.getOrElse(item, Set())
            )
          )
        )
      )
    )
      .toMap


  protected def getItemVertexLabel(item: TItem, mutexes: Set[TItem]): String


  private val itemWidths: Map[TItem, Double] =
    itemDimensions.map {
      case (item, dimension) =>
        item -> dimension.width
    }


  private val itemHeights: Map[TItem, Double] =
    itemDimensions.map {
      case (item, dimension) =>
        item -> dimension.height
    }


  val width: Double =
    if (items.isEmpty)
      0
    else
      itemWidths.values.max


  protected val horizontalSpacing: Double =
    250


  protected val verticalSpacing: Double =
    80


  val top: Double =
    20


  private val vertexMap: Map[TItem, TVertex] =
    if (items.isEmpty)
      Map()
    else {
      val firstPair =
        items.head -> createVertex(
          items.head,
          top
        )

      items.tail.foldLeft(List(firstPair)) {
        case (cumulatedPairs, item) =>
          val (previousItem, previousVertex) =
            cumulatedPairs.head

          val vertex = createVertex(
            item,
            previousVertex.center.y + itemHeights(previousItem) / 2 + verticalSpacing
          )

          val currentPair =
            item -> vertex


          currentPair :: cumulatedPairs
      }.toMap
    }


  def left: Double


  def height: Double =
    items
      .lastOption
      .map(lastItem => {
        val lastVertex =
          vertexMap(lastItem)

        val lastHeight =
          itemHeights(lastItem)

        lastVertex.center.y + lastHeight / 2 - top
      })
      .getOrElse(0)


  private def createVertex(item: TItem, currentTop: Double): TVertex = {
    val center = new Point2D(
      left + width / 2,
      currentTop + itemHeights(item) / 2
    )


    val size =
      itemDimensions(item)

    doCreateVertex(
      item,
      mutexMap.getOrElse(item, Set()),
      size,
      center
    )
  }


  protected def doCreateVertex(item: TItem, mutexes: Set[TItem], size: Dimension2D, center: Point2D): TVertex


  def getVertex(item: TItem): TVertex =
    vertexMap(item)


  def vertexes: Iterable[TVertex] =
    vertexMap.values


  def inMutex(anItem: TItem, anotherItem: TItem): Boolean =
    mutexMap.getOrElse(anItem, Set()).contains(anotherItem)


  def duplicates(otherLevel: ConstructionLevel[TItem, TVertex]): Boolean = {
    items == otherLevel.items &&
      mutexMap == otherLevel.mutexMap
  }
}
