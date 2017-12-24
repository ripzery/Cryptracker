package co.omisego.omgshop.deserialize

import android.util.Log
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.ByteArrayInputStream
import java.io.InputStreamReader


/**
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 30/11/2017 AD.
 * Copyright © 2017 OmiseGO. All rights reserved.
 */

class BxResponseBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {

    override fun convert(value: ResponseBody): T? {
        try {
            var responseData = value.string()
            responseData = responseData.replaceFirst("{", "[")
            val lastIndex = responseData.indexOfLast { '}' == it }
            responseData = responseData.replaceRange(lastIndex, lastIndex + 1, "]")
            responseData = responseData.replace(Regex("\"[0-9]+\":"), "")
            Log.d("BxResponse", responseData)
            val reader = InputStreamReader(ByteArrayInputStream(responseData.toByteArray()))
            val jsonReader = gson.newJsonReader(reader)
            jsonReader.use { _ ->
                return adapter.read(jsonReader)
            }
        } catch (e: Exception) {
            Log.d("Some exception", e.message)
            return null
        }
    }

}