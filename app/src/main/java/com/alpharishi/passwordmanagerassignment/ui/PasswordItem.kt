package com.alpharishi.passwordmanagerassignment.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.alpharishi.passwordmanagerassignment.PasswordEntity
import com.alpharishi.passwordmanagerassignment.ui.theme.LightGray

@Composable
fun PasswordItem(
    passwordEntity: PasswordEntity,
    onEdit: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit() }
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .background(
                color = Color(0xFFF5F4F4),
                shape = RoundedCornerShape(50.dp)
            )
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = passwordEntity.accountName,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                text = " ********",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterVertically)
                    .padding(horizontal = 4.dp)
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Edit",
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}


