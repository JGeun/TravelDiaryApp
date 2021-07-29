package com.hansung.traveldiary.src.plan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.src.plan.model.PlanTotalData

class TestDBActivity : AppCompatActivity() {

    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_d_b)

        user = Firebase.auth.currentUser
        db = Firebase.firestore
        db!!.collection(user!!.email.toString()).document(title.toString())
            .get().addOnSuccessListener  { documentSnapshot ->
                val data = documentSnapshot.toObject<PlanTotalData>()!!

            }
    }
}