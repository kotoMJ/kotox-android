package cz.kotox.i18n.ui.country

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.kotox.core.ui.theme.LocalColors
import cz.kotox.i18n.ui.phone.PhoneTextField


@Composable
fun PhoneCountryCodeContent(
    modifier: Modifier,
    state: State<PhoneCountryCodeFeedState>,
    listener: PhoneCountryCodeListener
) {


    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PhoneTextField(
                inputValue = state.value.phoneInput,
                onSubmit = {},
                onValueChange = { listener.onPhoneValueChange(it) },
                countryUiModel = state.value.countryCodeModel
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(text = state.value.countryCodeModel.flagEmoji)
        }

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
}