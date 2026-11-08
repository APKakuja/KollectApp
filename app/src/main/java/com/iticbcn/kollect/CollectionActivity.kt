package com.iticbcn.kollect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.iticbcn.kollect.collection.CollectionDetailFragment
import com.iticbcn.kollect.collection.CreateCollectionFragment
import com.iticbcn.kollect.databinding.ActivityCollectionBinding

class CollectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val id = intent.getStringExtra(EXTRA_COLLECTION_ID).orEmpty()
        if (savedInstanceState == null) {
            val initialFragment = if (intent.getBooleanExtra(EXTRA_CREATE, false)) {
                CreateCollectionFragment()
            } else {
                CollectionDetailFragment().apply {
                    arguments = Bundle().apply { putString(ARG_COLLECTION_ID, id) }
                }
            }
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.collectionNavHost, initialFragment)
                .commit()
        }
    }

    companion object {
        const val EXTRA_COLLECTION_ID = "collectionId"
        const val ARG_COLLECTION_ID = "collectionId"
        const val EXTRA_CREATE = "createCollection"
    }
}
