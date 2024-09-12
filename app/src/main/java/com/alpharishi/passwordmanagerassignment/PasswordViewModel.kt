package com.alpharishi.passwordmanagerassignment


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class PasswordViewModel : ViewModel() {
    var passwordList = mutableStateListOf<PasswordEntity>()
        private set
    fun addPassword(accountName: String, email: String, password: String) {
        val encryptedPassword = EncryptionUtil.encrypt(password)
        val passwordEntity = PasswordEntity(accountName = accountName, email = email, password = encryptedPassword)
        passwordList.add(passwordEntity)
    }

    fun editPassword(id: Int, accountName: String, email: String, password: String) {
        val index = passwordList.indexOfFirst { it.id == id }
        if (index != -1) {
            passwordList[index] = PasswordEntity(id, accountName, email, EncryptionUtil.encrypt(password))
        }
    }

    fun deletePassword(passwordEntity: PasswordEntity) {
        passwordList.remove(passwordEntity)
    }
}