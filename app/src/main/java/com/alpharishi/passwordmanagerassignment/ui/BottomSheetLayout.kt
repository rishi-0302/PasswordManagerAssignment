package com.alpharishi.passwordmanagerassignment.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.alpharishi.passwordmanagerassignment.BottomSheetContent
import com.alpharishi.passwordmanagerassignment.EncryptionUtil
import com.alpharishi.passwordmanagerassignment.PasswordStrengthUtil

@Composable
fun BottomSheetLayout(
    bottomSheetContent: BottomSheetContent,
    onSubmit: (String, String, String) -> Unit,
    onDelete: () -> Unit
) {
    var accountName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordStrength by remember { mutableStateOf(0) }
    var passwordStrengthLabel by remember { mutableStateOf("") }
    var passwordStrengthColor by remember { mutableStateOf(Color.Gray) }

    LaunchedEffect(bottomSheetContent) {
        if (bottomSheetContent is BottomSheetContent.Edit) {
            accountName = bottomSheetContent.passwordEntity.accountName
            email = bottomSheetContent.passwordEntity.email
            password = EncryptionUtil.decrypt(bottomSheetContent.passwordEntity.password)
        } else {
            accountName = ""
            email = ""
            password = ""
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = accountName,
            onValueChange = { accountName = it },
            label = { Text("Account Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = password,
            onValueChange = { newPassword ->
                password = newPassword

                passwordStrength = PasswordStrengthUtil.calculateStrength(newPassword)
                passwordStrengthLabel = PasswordStrengthUtil.getStrengthLabel(passwordStrength)
                passwordStrengthColor = PasswordStrengthUtil.getStrengthColor(passwordStrength)
            },
            label = { Text("Password") },
            visualTransformation = if (bottomSheetContent is BottomSheetContent.Edit) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .background(passwordStrengthColor)
                .fillMaxWidth()
        )

        Text(text = "Password Strength: $passwordStrengthLabel", color = passwordStrengthColor)

        Spacer(modifier = Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth()) {
            Button(
                onClick = { onSubmit(accountName, email, password) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White),
                modifier = Modifier.weight(1f)
                    .padding(12.dp)
            ) {
                Text(
                    text = if (bottomSheetContent is BottomSheetContent.Edit) "Edit" else "Add New Account",
                    color = Color.White
                )
            }

            if (bottomSheetContent is BottomSheetContent.Edit) {
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { onDelete() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.White),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Delete",
                        color = Color.White
                    )
                }
            }
        }
    }
}