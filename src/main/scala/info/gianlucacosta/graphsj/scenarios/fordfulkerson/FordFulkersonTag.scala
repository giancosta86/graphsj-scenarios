package info.gianlucacosta.graphsj.scenarios.fordfulkerson

case class FordFulkersonTag(
  plusVk: Boolean,
  vk: Option[FordFulkersonVertex],
  delta: Double
)