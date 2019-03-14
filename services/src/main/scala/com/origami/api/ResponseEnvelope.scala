package com.origami.api

trait ResponseEnvelope[A] extends ResponseBasicEnvelope {
  def data: A
}