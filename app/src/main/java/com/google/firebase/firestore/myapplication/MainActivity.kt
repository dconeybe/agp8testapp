package com.google.firebase.firestore.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    lateinit var selfRef: WeakReference<MainActivity>
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selfRef = WeakReference(this)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.text)
        textView.text = "Initializing Firestore..."

        val firestore = FirebaseFirestore.getInstance()
        firestore.document("docs/abc").get().addOnCompleteListener {
            val activity = selfRef.get()
            if (activity !== null) {
                if (it.isSuccessful) {
                    textView.text = "Document loaded; exists=" + it.result.exists()
                } else {
                    textView.text = it.exception.toString()
                }
            }
        }
    }

    override fun onDestroy() {
        selfRef.clear()
        super.onDestroy()
    }
}