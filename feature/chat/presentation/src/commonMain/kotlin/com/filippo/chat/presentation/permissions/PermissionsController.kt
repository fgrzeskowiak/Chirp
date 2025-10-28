package com.filippo.chat.presentation.permissions

expect class PermissionsController {
    suspend fun requestPermissions(permission: Permission): PermissionState
}


