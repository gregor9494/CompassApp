package com.gg.compassproject.platform.permissions

import android.Manifest
import android.content.Context
import com.gun0912.tedpermission.TedPermissionResult
import com.tedpark.tedpermission.rx2.TedRx2Permission
import io.reactivex.Single

class PermissionDispatcher(
    context: Context,
    private val builder: TedRx2Permission.Builder = TedRx2Permission.with(context)
) {
    fun requestLocationPermissions() =
        request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    fun request(vararg permissions: String): Single<TedPermissionResult> {
        return builder
            .setPermissions(*permissions)
            .request()
            .map {
                if (!it.isGranted) throw SecurityException()
                it
            }
    }
}