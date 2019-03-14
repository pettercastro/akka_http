package com.origami.api

import com.origami.api.models._

trait RequestPaginationEnvelope[T <: AnyRef] extends RequestEnvelope[T] {
  def sort: Option[Seq[RequestSortItem]]
}
