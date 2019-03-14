package com.akkahttp.http

import com.origami.api.RequestEnvelope
import com.akkahttp.models.Cat

case class CatRequestEnvelope(data: Cat) extends RequestEnvelope[Cat]
