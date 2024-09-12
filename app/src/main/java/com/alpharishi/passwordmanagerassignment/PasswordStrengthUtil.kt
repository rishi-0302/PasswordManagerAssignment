package com.alpharishi.passwordmanagerassignment

import androidx.compose.ui.graphics.Color
import com.alpharishi.passwordmanagerassignment.ui.theme.LightGreen
import com.alpharishi.passwordmanagerassignment.ui.theme.Orange

object PasswordStrengthUtil {
    fun calculateStrength(password: String): Int {
        var strength = 0
        if (password.length >= 8) strength += 1
        if (password.any { it.isDigit() }) strength += 1
        if (password.any { it.isUpperCase() }) strength += 1
        if (password.any { it.isLowerCase() }) strength += 1
        if (password.any { "!@#$%^&*()_+-=[]|,.<>?".contains(it) }) strength += 1
        return strength
    }

    fun getStrengthLabel(strength: Int): String {
        return when (strength) {
            5 -> "Very Strong"
            4 -> "Strong"
            3 -> "Medium"
            2 -> "Weak"
            else -> "Very Weak"
        }
    }

    fun getStrengthColor(strength: Int): Color {
        return when (strength) {
            5 -> Color.Green
            4 -> LightGreen
            3 -> Color.Yellow
            2 -> Orange
            else -> Color.Red
        }
    }
}