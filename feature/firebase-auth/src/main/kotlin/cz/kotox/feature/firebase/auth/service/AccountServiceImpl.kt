package cz.kotox.feature.firebase.auth.service

import com.google.firebase.auth.FirebaseAuth
import cz.kotox.feature.firebase.auth.model.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) : AccountService {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null
    override val currentUser: Flow<FirebaseUser>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(
                        auth.currentUser?.let {
                            if (it.isAnonymous) FirebaseUser.Anonymous(it.uid) else FirebaseUser.Authorized(it.uid)
                        } ?: FirebaseUser.None
                    )
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }
}
