package com.origami.api.models

/**
	* Created by tal on 27/03/2017.
	*/
/**
	* this class represents the information on a one leg call (request /response)
	* @param trxId transaction id
	* @param callId request id
	*/
case class CallInfo(
    trxId: String = java.util.UUID.randomUUID.toString.replace("-", ""),
    callId: String = java.util.UUID.randomUUID.toString.replace("-", "")) {
  override def toString = s"CallInfo($trxId, $callId)"
}

object CallInfo {
  def apply(callInfo: CallInfo): CallInfo = {
    new CallInfo(trxId = callInfo.trxId, callInfo.callId)
  }

  def apply(): CallInfo = new CallInfo()
}
