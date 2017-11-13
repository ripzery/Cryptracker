package com.ripzery.cryptracker.utils

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.jaredrummler.android.device.DeviceName
import java.util.*

/**
 * Created by ripzery on 10/14/17.
 */
object FirestoreHelper {
    private val COLLECTION_USERS = "users"
    private val mUsersCollection: CollectionReference by lazy { FirebaseFirestore.getInstance().collection(COLLECTION_USERS) }

    @SuppressLint("HardwareIds")
    fun addAllLastSeenPrice(deviceId: String) {
        val allLastSeenPrice = DbHelper.db.lastSeen().getAll()
        val documentPayload: Map<String, Any> by lazy {
            allLastSeenPrice.map {
                Pair(CurrencyIdHelper.getCurrency(it.id), hashMapOf(Pair("bx_price", it.bxPrice), Pair("cmc_price", it.cmcPrice)))
            }.toMap()
        }

        updateModifiedDate(deviceId)

        mUsersCollection.document(deviceId).set(documentPayload, SetOptions.merge())
                .addOnSuccessListener { Log.d("FirestoreHelper", "Add last seen price to Firestore successfully.") }
                .addOnFailureListener { Log.e("FirestoreHelper", "Error ${it.message}") }
    }

    fun updateRefreshedToken(deviceId: String, refreshedToken: String) {
        val documentPayload: Map<String, Any> by lazy {
            hashMapOf(
                    Pair("refreshedToken", refreshedToken)
            )
        }

        updateDeviceName(deviceId)
        updateModifiedDate(deviceId)


        mUsersCollection.document(deviceId).set(documentPayload, SetOptions.merge())
                .addOnSuccessListener { Log.d("FirestoreHelper", "Add refreshedToken $refreshedToken to Firestore successfully.") }
                .addOnFailureListener { Log.e("FirestoreHelper", "Error ${it.message}") }
    }

    private fun updateDeviceName(deviceId: String) {
        DeviceName.with(Contextor.context).request({ info, error ->
            val documentPayload: Map<String, Any> by lazy {
                hashMapOf(
                        Pair("deviceName", info.marketName)
                )
            }
            mUsersCollection.document(deviceId).set(documentPayload, SetOptions.merge())
        })
    }

    private fun updateModifiedDate(deviceId: String) {
        val documentPayload: Map<String, Any> by lazy {
            hashMapOf(
                    Pair("modifiedDate", DateHelper.getReadableDate(Date()))
            )
        }
        mUsersCollection.document(deviceId).set(documentPayload, SetOptions.merge())
    }
}