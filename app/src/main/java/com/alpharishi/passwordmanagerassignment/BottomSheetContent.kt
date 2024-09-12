package com.alpharishi.passwordmanagerassignment

sealed class BottomSheetContent {
    object Add : BottomSheetContent()
    data class Edit(val passwordEntity: PasswordEntity) : BottomSheetContent()
}