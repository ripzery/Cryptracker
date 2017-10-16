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
    fun addLastSeenOmiseGoPrice(deviceId: String, cmcPrice: Double, bxPrice: Double) {
        val omg = hashMapOf(Pair("bx_price", bxPrice), Pair("cmc_price", cmcPrice))
        val evx = hashMapOf(Pair("bx_price", 45), Pair("cmc_price", 1.33))
        val documentPayload: Map<String, Any> by lazy {
            hashMapOf(
                    Pair("evx", evx),
                    Pair("omg", omg)
            )
        }
        mUsersCollection.document(deviceId).set(documentPayload, SetOptions.merge())
                .addOnSuccessListener { Log.d("FirestoreHelper", "Add last seen price $bxPrice to Firestore successfully.") }
                .addOnFailureListener { Log.e("FirestoreHelper", "Error ${it.message}") }
    }

    fun updateRefreshedToken(deviceId: String, refreshedToken: String) {
        val documentPayload: Map<String, Any> by lazy {
            hashMapOf(
                    Pair("refreshedToken", refreshedToken)
            )
        }

        mUsersCollection.document(deviceId).set(documentPayload, SetOptions.merge())
                .addOnSuccessListener { Log.d("FirestoreHelper", "Add refreshedToken $refreshedToken to Firestore successfully.") }
                .addOnFailureListener { Log.e("FirestoreHelper", "Error ${it.message}") }
    }
}