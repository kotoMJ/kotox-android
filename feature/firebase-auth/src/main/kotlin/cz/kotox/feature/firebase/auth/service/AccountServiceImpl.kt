package cz.kotox.feature.firebase.auth.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import cz.kotox.feature.firebase.auth.model.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
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

    @Suppress("TooGenericExceptionCaught")
    override suspend fun createAccount(
        email: String,
        password: String,
        onEmailAlreadyInUse: (email: String) -> Unit
    ): Boolean {
        if (hasUser) {
            auth.signOut()
        }
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            true
        } catch (e: FirebaseAuthUserCollisionException) {
            Timber.d(e, "Email: $email already in use.")
            onEmailAlreadyInUse(email)
            false
        } catch (t: Throwable) {
            Timber.w(t, "Unable to create an account for email: $email")
            false
        }
    }

    @Suppress("TooGenericExceptionCaught")
    override suspend fun loginUserEmail(
        email: String,
        password: String,
        onInvalidCredentials: () -> Unit
    ): Boolean {
        if (hasUser) {
            auth.signOut()
        }
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.w(e, "Invalid credentials for email: $email")
            onInvalidCredentials()
            false
        } catch (t: Throwable) {
            Timber.w(t, "Unable to login using email: $email")
            false
        }
    }

    override fun logoutCurrentUser(){
        if (hasUser) {
            auth.signOut()
        }
    }
}
