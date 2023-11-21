package cz.kotox.common.core.domain

interface Mapper<I, O> {
    fun map(from: I): O
}
