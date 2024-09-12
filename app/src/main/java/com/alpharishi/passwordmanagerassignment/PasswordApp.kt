package com.alpharishi.passwordmanagerassignment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alpharishi.passwordmanagerassignment.ui.BottomSheetLayout
import com.alpharishi.passwordmanagerassignment.ui.PasswordItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordApp() {
    val viewModel: PasswordViewModel = viewModel()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    )
    val scope = rememberCoroutineScope()

    var bottomSheetContent by remember { mutableStateOf<BottomSheetContent>(BottomSheetContent.Add) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheetLayout(
                bottomSheetContent = bottomSheetContent,
                onSubmit = { accountName, email, password ->
                    if (bottomSheetContent is BottomSheetContent.Edit) {
                        val editingPassword = (bottomSheetContent as BottomSheetContent.Edit).passwordEntity
                        viewModel.editPassword(editingPassword.id, accountName, email, password)
                    } else {
                        viewModel.addPassword(accountName, email, password)
                    }
                    scope.launch { scaffoldState.bottomSheetState.hide() }
                },
                onDelete = {
                    if (bottomSheetContent is BottomSheetContent.Edit) {
                        val editingPassword = (bottomSheetContent as BottomSheetContent.Edit).passwordEntity
                        viewModel.deletePassword(editingPassword)
                    }
                    scope.launch { scaffoldState.bottomSheetState.hide() }
                }
            )
        },
        sheetPeekHeight = 0.dp,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Password Manager", color = Color.Black) },
                    modifier = Modifier.background(Color.White),
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color.White
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    bottomSheetContent = BottomSheetContent.Add
                    scope.launch { scaffoldState.bottomSheetState.expand() }
                }) {
                    Text("+")
                }
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                // Divider with 1.dp width and gray color
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(viewModel.passwordList.size) { index ->
                        val passwordEntity = viewModel.passwordList[index]
                        PasswordItem(
                            passwordEntity,
                            onEdit = {
                                bottomSheetContent = BottomSheetContent.Edit(passwordEntity)
                                scope.launch { scaffoldState.bottomSheetState.expand() }
                            }
                        )
                    }
                }
            }
        }
    }
}