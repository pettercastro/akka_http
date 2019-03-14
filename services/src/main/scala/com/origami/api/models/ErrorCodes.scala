package com.origami.api.models

/**
	* Created by talbs on 22/12/2016.
	*/
/**
	* this object represents the possible error codes
	*/
object ErrorCodes extends Enumeration {

  //no error
  val None = Code(0)

  //207 category - partial success
  val PartialSuccess = Code(207000)

  //400 category - bad request
  val ParseJson = Code(400001)
  val Skip = Code(400003)
  val NotFound = Code(400004)

  //404 category - endpoint not found
  val EndpointNotFound = Code(404000)
  val EntityNotFound = Code(404001)

  //417 category - validation
  val Validation = Code(417000)
  val MissingRequiredField = Code(417001)
  val DataTypeMismatch = Code(417002)
  val InvalidFieldValue = Code(417003)

  //500 category
  val Unknown = Code(500000)
  val RetryDeadlineExceeded = Code(500001)
  val RetryExhausted = Code(500002)

  //503 category - retriable
  val ServiceNotReady = Code(503001, true)

  val codeMap: Map[Int, Code] = ErrorCodes.values.toSeq.map { codeVal =>
    codeVal.asInstanceOf[Code].code -> codeVal.asInstanceOf[Code]
  }.toMap

  /**
		* resolves an ErrorCode from the code
		* @param code code to resolve
		* @return error code correlates to the input. none if not found
		*/
  def resolve(code: Int): Code =
    if (codeMap.contains(code)) codeMap(code) else None

  /**
		* checks if this code is retriable
		* @param code code to check
		* @return true/false if code is retriable
		*/
  def isRetry(code: Int): Boolean =
    if (codeMap.contains(code)) codeMap(code).retry else false

  protected case class Code(code: Int, retry: Boolean = false)
      extends super.Val(nextId)
}
