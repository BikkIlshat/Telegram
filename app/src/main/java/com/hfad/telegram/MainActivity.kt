package com.hfad.telegram

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.storage.StorageReference
import com.hfad.telegram.activities.RegisterActivity
import com.hfad.telegram.databinding.ActivityMainBinding
import com.hfad.telegram.models.User
import com.hfad.telegram.ui.fragments.ChatsFragment
import com.hfad.telegram.ui.objects.AppDrawer
import com.hfad.telegram.utilits.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class MainActivity : AppCompatActivity() {

    lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY = this // присвоили нашей константе ссылку на MainActivity (HW-20)
        initFields()
        initFunc()
    }

    // выполняем всю функциональность нашей активити
    private fun initFunc() {
        if (AUTH.currentUser != null) { //  проверка если пользователь авторизовался выполняем код ниже:
            setSupportActionBar(mToolbar)
            mAppDrawer.create() // нарисовали выдвижное  меню
            replaceFragment(
                ChatsFragment(),
                false
            ) // сделали её вызов в файле расширений функций funs.kt
        } else { // иначе переходим в окно авторизации пользователя
            replaceActivity(RegisterActivity()) // сделали её вызов в файле расширений функций funs.kt
        }
        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract) {
            it?.let { uri ->
                val path: StorageReference = REF_STORAGE_ROOT.child(FOLD_PROFILE_IMAGE)
                    .child(CURRENT_UID)
                path.putFile(uri).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(getString(R.string.toast_data_update))
                    }
                }
            }
        }

    }


    private fun initFields() {
        mToolbar = binding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar) // проинициализировали наше mToolbar
        initFirebase() //  проинициализировали наш FirebaseAuth и REF_DATABASE_ROOT
        initUser()
    }

    private fun initUser() {
        REF_DATABASE_ROOT // Realtime Database root - > telegram-f39eb
            .child(NODE_USERS)  // Realtime Database - > users
            .child(CURRENT_UID) // Realtime Database - >  SQvMtyLe6lNU9V55H8N1vQoAUdN2
            .addListenerForSingleValueEvent(AppValueEventListener {
                USER = it.getValue(User::class.java)
                    ?: User() // ЭлвисОператор  выполняем  код it.getValue(User::class.java) если он не null. Если null он выполнит этот код -> User()
            })
    }

    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(1, 1)
                .setRequestedSize(600, 600)
                .setCropShape(CropImageView.CropShape.OVAL)
                .getIntent(this@MainActivity)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }
    }


    fun hideKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}