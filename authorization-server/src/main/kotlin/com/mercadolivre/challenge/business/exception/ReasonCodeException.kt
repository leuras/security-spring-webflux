package com.mercadolivre.challenge.business.exception

import com.mercadolivre.challenge.entity.ReasonCode

class ReasonCodeException(val reasonCode: ReasonCode, exception: Throwable? = null) : Exception(reasonCode.description, exception)