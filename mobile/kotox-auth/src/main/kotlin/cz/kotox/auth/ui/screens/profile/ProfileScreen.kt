package cz.kotox.auth.ui.screens.profile

import androidx.compose.animation.Crossfade
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cz.kotox.feature.firebase.auth.model.FirebaseUser

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(initialValue = ProfileViewState())
    ProfileScreenContent(state = state)
}

@Composable
private fun ProfileScreenContent(
    // modifier: Modifier = Modifier,
    state: ProfileViewState
) {
    Crossfade(targetState = state.firebaseUser) {
        when (it) {
            is FirebaseUser.Anonymous -> Text(text = "Anonymous")
            is FirebaseUser.Authorized -> Text(text = it.id)
            FirebaseUser.None -> Text(text = "User not logged in.")
        }
    }
}
