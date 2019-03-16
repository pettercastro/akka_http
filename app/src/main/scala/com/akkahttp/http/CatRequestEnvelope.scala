package com.akkahttp.http

import com.akkahttp.models.Cat

import com.origami.api.RequestEnvelope

case class CatRequestEnvelope(data: Cat) extends RequestEnvelope[Cat]
