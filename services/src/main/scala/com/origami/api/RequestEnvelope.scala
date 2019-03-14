package com.origami.api

trait RequestEnvelope[T <: AnyRef] extends RequestBasicEnvelope {
  def data: T
}
