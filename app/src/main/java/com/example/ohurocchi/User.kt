package com.example.ohurocchi

import com.google.android.gms.appdatasearch.DocumentId
import java.util.*

data class User(
    @com.google.firebase.firestore.DocumentId
    val id: String = "",
    val title: String = "",
    //var createdAt: Date = Date(System.currentTimeMillis()),
    val createdAt: String = ""
)