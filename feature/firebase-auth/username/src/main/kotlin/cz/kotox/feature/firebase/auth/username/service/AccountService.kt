package cz.kotox.auth.domain.service

import cz.kotox.auth.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {

    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<User>
}
