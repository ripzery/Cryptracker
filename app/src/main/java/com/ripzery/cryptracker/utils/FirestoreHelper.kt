package com.ripzery.cryptracker.utils

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

/**
 * Created by ripzery on 10/14/17.
 */
object FirestoreHelper {
    private val COLLECTION_USERS = "users"
    private val mUsersCollection: CollectionReference by lazy { FirebaseFirestore.getInstance().collection(COLLECTION_USERS) }

    @SuppressLint("HardwareIds")
    fun addLastSeenPrice(deviceId: String, cmcPrice: Double, bxPrice: Double, currency: String) {
        val price = hashMapOf(Pair("bx_price", bxPrice), Pair("cmc_price", cmcPrice))
        val documentPayload: Map<String, Any> by lazy {
            hashMapOf(
                    Pair(currency, price)
            )
        }
        mUsersCollection.document(deviceId).set(documentPayload, SetOptions.merge())
                .addOnSuccessListener { Log.d("FirestoreHelper", "Add last seen price $bxPrice to Firestore successfully.") }
                .addOnFailureListener { Log.e("FirestoreHelper", "Error ${it.message}") }
    }

    fun updateRefreshedToken(deviceId: String, refreshedToken: String) {
        val documentPayload: Map<String, Any> by lazy {
            hashMapOf(
                    Pair("refreshedToken", refreshedToken),
                    Pair("deviceName", DeviceInfoHelper.getDeviceName())
            )
        }

        mUsersCollection.document(deviceId).set(documentPayload, SetOptions.merge())
                .addOnSuccessListener { Log.d("FirestoreHelper", "Add refreshedToken $refreshedToken to Firestore successfully.") }
                .addOnFailureListener { Log.e("FirestoreHelper", "Error ${it.message}") }
    }
}