package br.com.leuras.business.extension

import org.springframework.security.core.authority.SimpleGrantedAuthority

fun List<String>.toAuthorities() = this.map {
    SimpleGrantedAuthority(it)
}
