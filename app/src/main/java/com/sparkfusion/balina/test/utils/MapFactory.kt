package com.sparkfusion.balina.test.utils

interface MapFactory<I, O> {

    fun mapTo(input: I): O
    fun mapFrom(input: O): I
}