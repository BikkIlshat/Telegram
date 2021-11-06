package com.hfad.telegram.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import com.google.firebase.storage.StorageReference
import com.hfad.telegram.MainActivity
import com.hfad.telegram.R
import com.hfad.telegram.activities.RegisterActivity
import com.hfad.telegram.databinding.FragmentSettingsBinding
import com.hfad.telegram.utilits.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class SettingsFragment :
    ViewBindingFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cropActivityResultLauncher()
    }

    //Инициализация всех полей фрагмента настроек
    private fun initFields() = with(binding) {
        settingsBio.text = USER.bio
        settingsFullName.text = USER.fullname
        settingsPhoneNumber.text = USER.phone
        settingsStatus.text = USER.status
        settingsUsername.text = USER.username
        settingsBtnChangeUsername.setOnClickListener { replaceFragment(ChangeUsernameFragment()) }
        settingsBtnChangeBio.setOnClickListener { replaceFragment(ChangeBioFragment()) } // HW-19
        settingsChangePhoto.setOnClickListener { changePhotoUser() }
        binding.settingsUserPhoto.downloadAndSetImage(USER.photoUrl)
    }

    private fun cropActivityResultLauncher() {
        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract) {
            it?.let { uri ->
                val path: StorageReference =
                    REF_STORAGE_ROOT
                        .child(FOLD_PROFILE_IMAGE)
                        .child(CURRENT_UID)
                putImageToStorage(uri, path) {
                    getUrlFromStorage(path) {
                        putUrlToDatabase(it) {
                            binding.settingsUserPhoto.downloadAndSetImage(it) // Обновляем картинку нашего пользователя
                            showToast(getString(R.string.toast_data_update))
                            USER.photoUrl = it
                        }
                    }
                }
            }
        }
    }

    private fun changePhotoUser() {
        cropActivityResultLauncher.launch(null)
    }

    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>() {
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(1, 1)
                .setRequestedSize(600, 600)
                .setCropShape(CropImageView.CropShape.OVAL)
                .getIntent(context) // ....
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut() // обращаемся к объекту Аутентификации и выходим из профиля
                (activity as MainActivity).replaceActivity(RegisterActivity()) // запускаем окно регистрации
            }
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment()) // меняем фрагмент на ChangeNameFragment
        }
        return true
    }
}