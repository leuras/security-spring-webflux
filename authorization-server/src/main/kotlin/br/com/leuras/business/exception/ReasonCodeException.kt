package br.com.leuras.business.exception

import br.com.leuras.entity.ReasonCode

class ReasonCodeException(val reasonCode: ReasonCode, exception: Throwable? = null) : Exception(reasonCode.description, exception)