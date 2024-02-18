package cz.kotox.feature.firebase.auth.service

import cz.kotox.feature.firebase.auth.model.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AccountService {

    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<FirebaseUser>

    suspend fun createAccount(email: String, password: String)
}