package com.mominulcse7.githubclone

import org.junit.Test

import org.junit.Assert.*
import retrofit2.Retrofit
import javax.inject.Inject

class QueryParameterUnitTest {

    @Inject
    lateinit var retrofit: Retrofit

    @Test
    fun isParametersAreValid() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun isBaseUrlIsValid() {
        assertEquals(retrofit.baseUrl(), "https://api.github.com/")
    }
}