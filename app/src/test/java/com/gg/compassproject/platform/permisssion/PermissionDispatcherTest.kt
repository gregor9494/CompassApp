package com.gg.compassproject.platform.permisssion

import android.content.Context
import com.gg.compassproject.UnitTest
import com.gg.compassproject.platform.permissions.PermissionDispatcher
import com.gun0912.tedpermission.TedPermissionResult
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.tedpark.tedpermission.rx2.TedRx2Permission
import io.reactivex.Single
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

/**
 * Created by ggaworowski on 13.04.2019.
 */
class PermissionDispatcherTest : UnitTest() {

    lateinit var permissionDispatcher: PermissionDispatcher

    @Mock
    private lateinit var permissionBuilder: TedRx2Permission.Builder

    @Before
    fun setup() {
        permissionDispatcher = PermissionDispatcher(mock(Context::class), permissionBuilder)
    }

    @Test
    fun `test will throw exception when permission danied`() {
        whenever(permissionBuilder.setPermissions(any())).thenReturn(permissionBuilder)
        whenever(permissionBuilder.request()).thenReturn(Single.just(TedPermissionResult(listOf("daniedPermission"))))
        permissionDispatcher.request("test").test().assertError { it is SecurityException }
    }

    @Test
    fun `test will throw exception when no permission`() {
        whenever(permissionBuilder.setPermissions(any())).thenReturn(permissionBuilder)
        whenever(permissionBuilder.request()).thenReturn(Single.just(TedPermissionResult(emptyList())))
        permissionDispatcher.request("test").test().assertValue { it.isGranted }.assertComplete()
    }

}