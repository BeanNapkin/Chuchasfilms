package pro.fateeva.chuchasfilms.ui.main

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import pro.fateeva.chuchasfilms.R
import pro.fateeva.chuchasfilms.databinding.ContactsFragmentBinding
import java.security.Permission

class ContactsFragment : Fragment() {

    private var _binding: ContactsFragmentBinding? = null
    private val binding get() = _binding!!

    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            var allPermissionsGranted = true
            for ((_, value) in result) {
                if (value.not()) {
                    allPermissionsGranted = false
                }
            }

            if (allPermissionsGranted) {
                getContacts()
            } else {
                Toast.makeText(context, "Need permissions", Toast.LENGTH_SHORT)
            }
        }

    companion object {
        fun newInstance() = ContactsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ContactsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    private fun checkPermission() {
        requestPermission()
    }

    private fun requestPermission() {
        permissionResult.launch(
            arrayOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CALL_PHONE
            )
        )
    }

    private fun getContacts() {
        context?.let {
            // Получаем ContentResolver у контекста
            val contentResolver: ContentResolver = it.contentResolver
            // Отправляем запрос на получение контактов и получаем ответ в виде Cursor
            val cursorWithContacts: Cursor? = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            )

            cursorWithContacts?.let { cursor ->
                for (i in 0..cursor.count) {
                    // Переходим на позицию в Cursor
                    if (cursor.moveToPosition(i)) {
                        // Берём из Cursor столбец с именем
                        val name =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                        val phone =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        addView(it, name, phone)
                    }
                }
            }
            cursorWithContacts?.close()
        }
    }

    private fun addView(context: Context, textToShow: String, phone: String) {
        binding.containerForContacts.addView(TextView(context).apply {
            text = textToShow
            textSize = resources.getDimension(R.dimen.main_container_text_size)
            setOnClickListener {
                val intent = Intent(Intent.ACTION_CALL);
                intent.data = Uri.parse("tel:$phone")
                startActivity(intent)
            }
        })
    }
}