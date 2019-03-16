package com.akkahttp.services

import com.akkahttp.http.CatResponseEnvelope
import com.akkahttp.models.Cat

class AkkaHttpService {
  def saveCat(c: Cat): CatResponseEnvelope  = {
    CatResponseEnvelope(c)
  }
}
