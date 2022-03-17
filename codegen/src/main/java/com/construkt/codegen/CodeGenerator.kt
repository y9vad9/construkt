package com.construkt.codegen

interface CodeGenerator<T> {
    fun generate(): T
}