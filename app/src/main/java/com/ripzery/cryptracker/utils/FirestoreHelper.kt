package com.ripzery.cryptracker.utils

import android.annotation.SuppressLint
import android.provider.Settings
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by ripzery on 10/14/17.
 */
object FirestoreHelper {
    private val COLLECTION_USERS = "users"
    private val mUsersCollection: CollectionReference by lazy { FirebaseFirestore.getInstance().collection(COLLECTION_USERS) }

    @SuppressLint("HardwareIds")
    fun addLastSeenOmiseGoPrice(cmcPrice: Double, bxPrice: Double) {
        val deviceId = Settings.Secure.getString(Contextor.context.contentResolver, Settings.Secure.ANDROID_ID)
        val omg = hashMapOf(Pair("bx_price", bxPrice), Pair("cmcPrice", cmcPrice))
        val evx = hashMapOf(Pair("bx_price", 45), Pair("cmcPrice", 1.33))
        val documentPayload: Map<String, Any> by lazy {
            hashMapOf(
                    Pair("deviceId", deviceId),
                    Pair("evx", evx),
                    Pair("omg", omg)
            )
        }
        mUsersCollection.document(deviceId).set(documentPayload)
                .addOnSuccessListener { Log.d("FirestoreHelper", "Add last seen price $bxPrice to Firestore successfully.") }
                .addOnFailureListener { Log.e("FirestoreHelper", "Error ${it.message}") }
    }
}