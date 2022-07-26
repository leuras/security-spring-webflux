package com.mercadolivre.challenge.business.extension

import org.springframework.security.core.authority.SimpleGrantedAuthority

fun List<String>.toAuthorities() = this.map {
    SimpleGrantedAuthority(it)
}
