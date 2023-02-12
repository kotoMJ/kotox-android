package cz.kotox.i18n.ui.country

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.kotox.core.ui.theme.LocalColors

@Composable
fun PhoneCountryCodeContent(
    modifier: Modifier,
    state: State<PhoneCountryCodeFeedState>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        itemsIndexed(
            items = state.value.countries,
            itemContent = { index, searchItem ->
                PhoneNumberPrefixSearchItem(
                    item = searchItem,
                    modifier = Modifier.clickable {
                        //TODO
                    }
                )
                if (index < state.value.countries.lastIndex) {
                    Divider(
                        color = LocalColors.current.divider,
                        thickness = 0.5.dp
                    )
                }
            }
        )
    }
}