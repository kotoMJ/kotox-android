package cz.kotox.common.i18n.ui.phone.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.kotox.common.designsystem.theme.shiraz.KotoxBasicTheme
import cz.kotox.common.designsystem.theme.shiraz.LocalColors
import cz.kotox.common.designsystem.theme.shiraz.LocalTypography
import cz.kotox.common.i18n.domain.model.CountryUiModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhoneTextField(
    modifier: Modifier = Modifier,
    inputValue: String = "42011",
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    enabled: Boolean = true,
    countryUiModel: cz.kotox.common.i18n.domain.model.CountryUiModel,
) {
    val colors: TextFieldColors = TextFieldDefaults.textFieldColors()
    val shape: Shape =
        MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize)
    val isError: Boolean = false
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

    val startFixedPart = "+"


    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(
            text = inputValue.trimStart(),
            selection = TextRange(inputValue.length)
        ),
        keyboardActions = KeyboardActions(onSend = { onSubmit() }),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Go,
        ),
        onValueChange = { textFieldValue ->
            if (textFieldValue.text.filterNot {
                    it.isDigit() || it.isWhitespace()
                }.isEmpty()) {

                val numberLengthLimit: Int? = if (countryUiModel is cz.kotox.common.i18n.domain.model.CountryUiModelValueItem) {
                    if (countryUiModel.expectedLength == null) {
                        null
                    } else {
                        (countryUiModel.expectedLength
                            ?: 0) + (textFieldValue.text.filter { it.isWhitespace() }.length)
                    }
                } else null

                if ((numberLengthLimit == null) ||
                    (numberLengthLimit >= textFieldValue.text.length)
                ) {
                    onValueChange(textFieldValue.text)
                }
            }
        },
        visualTransformation = PrefixTransformation(startFixedPart),
        textStyle = LocalTextStyle.current,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .defaultMinSize(
                        minWidth = TextFieldDefaults.MinWidth,
                        minHeight = TextFieldDefaults.MinHeight
                    )
                    .background(colors.backgroundColor(enabled).value, shape)
                    .indicatorLine(enabled, isError, interactionSource, colors)
                    .padding(horizontal = 16.dp, vertical = 12.dp), // inner padding

            ) {
                if (countryUiModel is cz.kotox.common.i18n.domain.model.CountryUiModelValueItem) {

                    if (countryUiModel.countryCode.toString().contains(inputValue)) {
                        val hint = buildAnnotatedString {
                            val completePhoneNumberString =
                                "${startFixedPart}${inputValue}${countryUiModel.numberHintWithoutCountryCode}"
                            append(completePhoneNumberString)
                            addStyle(
                                style = SpanStyle(
                                    fontStyle = LocalTypography.current.body1Regular.fontStyle,
                                    color = Color.Red
                                ),
                                start = 0,
                                end = startFixedPart.length + inputValue.length
                            )
                            addStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Thin,
                                    color = LocalColors.current.textWeak
                                ),
                                start = startFixedPart.length + inputValue.length,
                                end = completePhoneNumberString.length
                            )
                        }

                        Text(
                            text = hint,
                        )
                    }
                }
                innerTextField()
            }
        }
    )
}

internal class PrefixTransformation(val prefix: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return prefixFilter(text, prefix)
    }
}

internal fun prefixFilter(number: AnnotatedString, prefix: String): TransformedText {

    val out = prefix + number.text
    val prefixOffset = prefix.length

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return offset + prefixOffset
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset < prefixOffset) return 0
            return offset - prefixOffset
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}

@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    name = "Light Mode"
)
@Preview(
    device = Devices.PIXEL,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PhoneTextFieldPreview() {

    val inputValue = "420"
    val expectedCountryCode = 420
    val hint = "601123456"

    KotoxBasicTheme {
        Column {
            PhoneTextField(
                inputValue = inputValue,
                onValueChange = {},
                onSubmit = { },
                countryUiModel = if (inputValue.startsWith(expectedCountryCode.toString())) {
                    CountryUiModel.CountryUiModelItem(
                        isoCode = "CZ",
                        countryName = "Czech Republic",
                        countryCode = expectedCountryCode,
                        flagEmoji = "🇨🇿",
                        numberHintWithoutCountryCode = hint,
                        expectedLength = expectedCountryCode.toString().length + hint.length
                    )
                } else {
                    CountryUiModel.CountryUiModelEmptyItem()
                }
            )
            PhoneTextFieldComparisonForPreview(
                inputValue = inputValue,
                onValueChange = {},
                onSubmit = { },
                hint = "601123456"
            )
        }
    }
}

@Composable
fun PhoneTextFieldComparisonForPreview(
    modifier: Modifier = Modifier,
    inputValue: String,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    hint: String,
) {

    TextField(
        value = inputValue,
        onValueChange = { onValueChange(it) },
        visualTransformation = PrefixTransformation("+"),
        placeholder = {
            Text(
                text = hint,
                style = LocalTypography.current.body1Regular,
                color = LocalColors.current.textWeak,
            )
        },
    )
}