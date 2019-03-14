package com.akkahttp.http

import com.origami.api.ResponseEnvelope
import com.akkahttp.models.Cat

case class CatResponseEnvelope(data: Cat) extends ResponseEnvelope[Cat]
