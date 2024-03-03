package cz.kotox.auth.ui.screens.profile

import cz.kotox.feature.firebase.auth.model.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

@Suppress("FunctionNaming")
internal fun ProfileScreenPresenter(
    firebaseUserFlow: Flow<FirebaseUser>
) = combine(
    firebaseUserFlow
) { firebaseUser ->
    ProfileViewState(
        firebaseUser = firebaseUser.first()
    )
}
