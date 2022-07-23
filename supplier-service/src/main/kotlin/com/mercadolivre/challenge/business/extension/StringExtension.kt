package com.mercadolivre.challenge.business.extension

fun String.mask(from: Int, to: Int = this.length, char: String = "*"): String {
    var maskedString = this
    val interval = from.rangeTo(to)
    val re = """\p{Punct}""".toRegex()

    for (index in 0 until this.length) {

        if (re.matches(maskedString[index].toString()))
            continue

        if (index in interval) {
            maskedString = maskedString.replaceRange(index, (index + 1), char)
        }
    }

    return maskedString
}