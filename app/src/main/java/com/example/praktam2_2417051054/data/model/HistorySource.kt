package com.example.praktam2_2417051054.data.model

import androidx.compose.runtime.mutableStateListOf

object HistorySource {
    val historyTransaksi = mutableStateListOf<History>()

    fun transaksiBaru(history: History) {
        historyTransaksi.add(history)
    }
}