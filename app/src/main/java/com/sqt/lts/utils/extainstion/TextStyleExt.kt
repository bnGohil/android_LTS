package com.example.lts.utils.extainstion

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sqt.lts.ui.theme.kBlackColor
import com.sqt.lts.ui.theme.kLightTextColor
import com.sqt.lts.ui.theme.kPrimaryColor
import com.sqt.lts.ui.theme.kSecondaryTextColor
import com.sqt.lts.ui.theme.kWhite
import com.example.lts.utils.scaleFont
import com.sqt.lts.ui.theme.kWhite
import com.sqt.lts.utils.extainstion.appFontFamily


@Composable
fun TextStyle.kWhiteW500FS17(): TextStyle {
    return TextStyle(color = kWhite, fontFamily = appFontFamily, fontSize = 17.sp.scaleFont(), fontWeight = FontWeight.W500)
}

@Composable
fun TextStyle.kWhiteW500FS18(): TextStyle {
    return TextStyle(color = kWhite, fontFamily = appFontFamily, fontSize = 18.sp.scaleFont(), fontWeight = FontWeight.W500)
}


@Composable
fun TextStyle.kLightTextColorW600FS20(): TextStyle {
    return TextStyle(color = kLightTextColor, fontFamily = appFontFamily, fontSize = 20.sp.scaleFont(), fontWeight = FontWeight.W600)
}

@Composable
fun TextStyle.kLightTextColorW500FS15(): TextStyle {
    return TextStyle(color = kLightTextColor, fontFamily = appFontFamily, fontSize = 15.sp.scaleFont(), fontWeight = FontWeight.W600)
}

@Composable
fun TextStyle.kWhiteW600FS17(): TextStyle {
    return TextStyle(color = kWhite, fontFamily = appFontFamily, fontSize = 17.sp.scaleFont(), fontWeight = FontWeight.W600)
}

@Composable
fun TextStyle.kWhiteW400FS13(): TextStyle {
    return TextStyle(color = kWhite, fontFamily = appFontFamily, fontSize = 13.sp.scaleFont(), fontWeight = FontWeight.W400)
}

@Composable
fun TextStyle.kWhiteW300FS12(): TextStyle {
    return TextStyle(color = kWhite, fontFamily = appFontFamily, fontSize = 12.sp.scaleFont(), fontWeight = FontWeight.W300)
}

@Composable
fun TextStyle.kBlackW400FS15(): TextStyle {
    return TextStyle(color = kBlackColor, fontFamily = appFontFamily, fontSize = 15.sp.scaleFont(), fontWeight = FontWeight.W400)
}

@Composable
fun TextStyle.kBlackW700FS18(): TextStyle {
    return TextStyle(color = kBlackColor, fontFamily = appFontFamily, fontSize = 18.sp.scaleFont(), fontWeight = FontWeight.W700)
}

@Composable
fun TextStyle.kSecondaryTextColorW500FS15(): TextStyle {
    return TextStyle(color = kSecondaryTextColor, fontFamily = appFontFamily, fontSize = 15.sp.scaleFont(), fontWeight = FontWeight.W500)
}


@Composable
fun TextStyle.kPrimaryColorW400FS15(): TextStyle {
    return TextStyle(color = kPrimaryColor, fontFamily = appFontFamily, fontSize = 15.sp.scaleFont(), fontWeight = FontWeight.W400)
}

@Composable
fun TextStyle.kPrimaryColorW500FS15(): TextStyle {
    return TextStyle(color = kPrimaryColor, fontFamily = appFontFamily, fontSize = 15.sp.scaleFont(), fontWeight = FontWeight.W500)
}


