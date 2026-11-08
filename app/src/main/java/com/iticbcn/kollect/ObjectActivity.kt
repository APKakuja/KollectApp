package com.iticbcn.kollect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.iticbcn.kollect.objectscreen.ObjectDetailFragment
import com.iticbcn.kollect.objectscreen.AddObjectFragment
import com.iticbcn.kollect.databinding.ActivityObjectBinding

class ObjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityObjectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val id = intent.getStringExtra(EXTRA_OBJECT_ID).orEmpty()
        if (savedInstanceState == null) {
            val initialFragment = if (intent.getBooleanExtra(EXTRA_CREATE, false)) {
                AddObjectFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_COLLECTION_ID, intent.getStringExtra(EXTRA_COLLECTION_ID).orEmpty())
                    }
                }
            } else {
                ObjectDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_OBJECT_ID, id)
                        putString(ARG_COLLECTION_ID, intent.getStringExtra(EXTRA_COLLECTION_ID).orEmpty())
                    }
                }
            }
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.objectNavHost, initialFragment)
                .commit()
        }
    }

    companion object {
        const val EXTRA_OBJECT_ID = "objectId"
        const val EXTRA_COLLECTION_ID = "collectionId"
        const val ARG_OBJECT_ID = "objectId"
        const val ARG_COLLECTION_ID = "collectionId"
        const val EXTRA_CREATE = "createObject"
    }
}
