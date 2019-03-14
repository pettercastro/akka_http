package com.akkahttp.services

import com.akkahttp.models.Cat
import com.akkahttp.http.{CatRequestEnvelope, CatResponseEnvelope}

class AkkaHttpService {
  def saveCat(c: Cat): CatResponseEnvelope  = {
    CatResponseEnvelope(c)
  }
}
