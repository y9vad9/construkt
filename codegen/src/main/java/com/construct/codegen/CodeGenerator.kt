package com.construct.codegen

interface CodeGenerator<T> {
    fun generate(): T
}