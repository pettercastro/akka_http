package com.origami.api

import com.origami.api.models.{CallInfo, ErrorInfo}

case class EmptyResponse()
case class ErrorResponse(errorInfo: ErrorInfo,
                         var callInfo: CallInfo)
    extends ResponseBasicEnvelope
