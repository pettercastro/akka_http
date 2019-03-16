package com.akkahttp.http

import com.akkahttp.models.Cat

import com.origami.api.ResponseEnvelope

case class CatResponseEnvelope(data: Cat) extends ResponseEnvelope[Cat]
