package com.example.lts.custom_component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lts.ui.auth.data.response.CountryData

import com.example.lts.utils.extainstion.kBlackW400FS15
import com.example.lts.utils.extainstion.kSecondaryTextColorW500FS15
import com.example.lts.utils.extainstion.kWhiteW400FS13
import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.theme.LtsTheme
import com.sqt.lts.ui.theme.kCardBackgroundColor
import com.sqt.lts.ui.theme.kWhite

@Composable
fun CustomCountryDropdown(
    onItemSelected: ((CountryData?) -> Unit),
    hintText:String,
    title: String,
    value: CountryData?=null,
    isLoading: Boolean?=null,
    countryList: ArrayList<CountryData>?= arrayListOf()) {

    var isDropDown by remember { mutableStateOf<Boolean>(false) }
    var selectedItem by remember { mutableStateOf<CountryData?>(value) }


    var countryData by remember { mutableStateOf<CountryData?>(null) }

    LaunchedEffect(value,countryList) {
        snapshotFlow { value }.collect{
            if(it != null){
                selectedItem  = countryList?.find { data -> data.countryid == it.countryid }
            }
        }

    }

    Box {
        Column {
            Text(text = title,style = TextStyle.Default.kSecondaryTextColorW500FS15())
            Spacer(modifier = Modifier.height(10.dp.scaleSize()))
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(kCardBackgroundColor)
                .padding(horizontal = 10.dp.scaleSize(), vertical = 15.dp.scaleSize())
                .clickable {
                    if (isLoading == true) return@clickable
                    isDropDown = true
                }) {
                Text(
                    text = if (selectedItem == null) hintText else selectedItem?.countryname ?: "",
                    style = TextStyle.Default.kWhiteW400FS13(),
                    modifier = Modifier.weight(1F)
                )
                if(isLoading == true){
                    CircularProgressIndicator(
                         strokeWidth = 1.dp,
                        modifier = Modifier.size(15.dp),
                        color = kWhite
                    )
                }else{
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "icon",
                        tint = kWhite,
                        modifier = Modifier.size(20.dp.scaleSize())
                    )
                }

            }
        }
        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = (LocalConfiguration.current.screenHeightDp / 2).dp.scaleSize()),
            expanded = isDropDown,
            onDismissRequest = { isDropDown = false }
        ) {
            countryList?.map {
                DropdownMenuItem(text = { Text(text = it.countryname ?: "", style = TextStyle.Default.kBlackW400FS15())}, onClick = {
                    onItemSelected(it)
                    selectedItem = it
                    isDropDown = false
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomCountryDropdownPreview() {
    LtsTheme {
        CustomCountryDropdown(hintText = "Select User", title = "Country", onItemSelected = {})
    }
}

