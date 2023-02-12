package cz.kotox.core.domain

interface Mapper<I, O> {
    fun map(from: I): O
}
