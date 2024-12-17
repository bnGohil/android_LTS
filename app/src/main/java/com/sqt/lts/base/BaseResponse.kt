package com.example.lts.base
import com.google.gson.annotations.SerializedName
import java.io.Serializable



abstract class BaseResponse<DATA> : Serializable {
    abstract val `data`: DATA?
    val message: String? = null
    val status: Int? = null
    val statusCode: Int? = null
}