package com.andreich.moviesearcher.data.datasource.local

import com.andreich.moviesearcher.data.entity.RequestEntity

interface HistoryDataSource {

    suspend fun getRequestTableSize(): Int

    suspend fun insertRequest(request: RequestEntity)

    fun deleteOldestRequest()
}