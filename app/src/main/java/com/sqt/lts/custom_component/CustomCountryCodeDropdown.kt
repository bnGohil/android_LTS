package com.sqt.lts.custom_component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lts.dummy.models.CountryListObject
import com.example.lts.dummy.models.CountryModel
import com.example.lts.utils.extainstion.kBlackW400FS15
import com.example.lts.utils.extainstion.kWhiteW400FS13
import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.theme.kWhite

@Composable
fun CustomCountryCodeDropdown(countryCode: CountryModel?= null) {

    var selectedCountryCode by remember { mutableStateOf<CountryModel?>(countryCode) }

    var isDropDown  by remember { mutableStateOf<Boolean>(false) }

    Box {
        Row(modifier = Modifier.clickable {
            isDropDown = true
        }) {
            Text(
                text = selectedCountryCode?.countryName ?: "",
                style = TextStyle.Default.kWhiteW400FS13(),
            )
            Spacer(modifier = Modifier.width(3.dp.scaleSize()))
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "icon",
                tint = kWhite,
                modifier = Modifier.size(20.dp.scaleSize())
            )
            Spacer(
                modifier = Modifier
                    .padding(end = 8.dp.scaleSize())
                    .height(20.dp.scaleSize())
                    .width(1.dp.scaleSize())
                    .background(kWhite)
            )
        }

        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = isDropDown,
            onDismissRequest = { isDropDown = false }
        ) {
            CountryListObject.countryCode.map {
                DropdownMenuItem(text = { Text(text = it.countryName ?: "", style = TextStyle.Default.kBlackW400FS15())}, onClick = {
                    selectedCountryCode = it
                    isDropDown = false
                })
            }
        }

    }



}

@Preview
@Composable
private fun CustomCountryCodeDropdownPreview() {
    CustomCountryCodeDropdown(countryCode = CountryModel(countryName = "+91"))
}