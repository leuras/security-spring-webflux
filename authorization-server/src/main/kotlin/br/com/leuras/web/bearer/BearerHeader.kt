package br.com.leuras.web.bearer

fun interface BearerHeader {
    fun content(): String?
}