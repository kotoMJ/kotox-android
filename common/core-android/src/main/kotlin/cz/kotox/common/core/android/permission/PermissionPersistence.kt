package cz.kotox.common.core.android.permission

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class PermissionPersistence(private val context: Context) {

    companion object {
        private val Context.permissionRequestDataStore: DataStore<Preferences> by preferencesDataStore("permissionRequestPersistence")
    }

    suspend fun savePermissionRequest(permission: String) {
        val permissionPreferencesKey = intPreferencesKey(permission)
        context.permissionRequestDataStore.edit { preferences ->
            val existingRequestCount: Int = preferences[permissionPreferencesKey] ?: 0
            preferences[permissionPreferencesKey] = existingRequestCount + 1
        }
    }

    fun permissionNotRequestedYet(permission: String): Flow<Boolean> {
        val permissionPreferencesKey = intPreferencesKey(permission)
        return context.permissionRequestDataStore.data.map { preferences ->
            Timber.d(">>>_ PERM count for ${permission} : ${preferences[permissionPreferencesKey]}")
            (preferences[permissionPreferencesKey] ?: 0) == 0
        }
    }

    fun cleanupPermissionRequestsAsync(): Deferred<Unit> = GlobalScope.async { cleanupPermissionRequests() }

    suspend fun cleanupPermissionRequests() {
        context.permissionRequestDataStore.edit { preferences ->
            preferences.clear()
        }
    }

}