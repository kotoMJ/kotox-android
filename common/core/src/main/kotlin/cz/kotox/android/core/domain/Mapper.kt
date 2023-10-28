package cz.kotox.common.domain

interface Mapper<I, O> {
    fun map(from: I): O
}
