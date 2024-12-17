package com.example.lts.custom_component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.lts.utils.extainstion.kWhiteW500FS18
import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.theme.kPrimaryColor
import com.sqt.lts.ui.theme.kWhite

@Composable
fun CustomButton(btnText: String,onClick: () -> Unit,isLoading:Boolean?=null) {
    ElevatedButton(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10),
        colors = ButtonDefaults.buttonColors(containerColor = kPrimaryColor),
        onClick = onClick) {
        if(isLoading == true){
            CircularProgressIndicator(color = kWhite, modifier = Modifier.size(24.dp.scaleSize()), strokeWidth = 1.dp)
        }else{
            Text(text = btnText, style = TextStyle.Default.kWhiteW500FS18())
        }

    }
}