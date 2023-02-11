package com.alacrity.testTask.ui.main

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.alacrity.testTask.App
import com.alacrity.testTask.TemplateApp
import javax.inject.Inject

class MainActivity: AppCompatActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    var contactName by mutableStateOf("")
    var contactNumber by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        setContent {
            TemplateApp(activity = this, homeViewModel = mainViewModel)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == 1 && data != null) {
            val contactData: Uri? = data.data
            val cursor: Cursor = managedQuery(contactData, null, null, null, null)
            cursor.moveToFirst()

            val number: String =
                cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val name: String =
                cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))

            contactName = name
            contactNumber = number

            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
            startActivity(intent)
        }

    }

    companion object {
        const val REQUEST_CODE = 112
    }
}