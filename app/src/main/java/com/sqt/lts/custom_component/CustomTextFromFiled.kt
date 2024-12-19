package com.example.lts.custom_component



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField


import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.lts.utils.extainstion.kSecondaryTextColorW500FS15
import com.example.lts.utils.scaleSize
import com.sqt.lts.ui.theme.kCardBackgroundColor
import com.sqt.lts.ui.theme.kWhite


@Composable
fun CustomTextFromFiled(
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    onValueChange: (String) -> Unit,
    value: String?="",
    enabled: Boolean?=true,
    titleText: String?=null, prefix: (@Composable () -> Unit) ?= null, suffix: (@Composable () -> Unit) ?= null, singleLine: Boolean?=true) {

//    var value by remember{ mutableStateOf<String>("") }

    Column() {
        if(titleText != null){
            Text(text = titleText,style = TextStyle.Default.kSecondaryTextColorW500FS15())
        }
        Spacer(modifier = Modifier.height(10.dp.scaleSize()))
        Row(modifier = Modifier.fillMaxWidth().background(kCardBackgroundColor).padding(vertical = 15.dp.scaleSize(), horizontal = 10.dp.scaleSize())) {
            prefix?.invoke()
            Box(modifier = Modifier.weight(1F)) {
                if(value?.isEmpty() == true){ Text(text = titleText?:"", style = TextStyle.Default.kSecondaryTextColorW500FS15()) }
                if (value != null) {
                    BasicTextField(
                        singleLine = singleLine?:true,
                        enabled = enabled?:true,
                        visualTransformation = visualTransformation,
                        keyboardOptions = KeyboardOptions(
                            imeAction = imeAction,
                            keyboardType = keyboardType
                        ),

                        modifier = Modifier.fillMaxWidth(),value = value, onValueChange = { onValueChange(it) },
                        textStyle = TextStyle.Default.kSecondaryTextColorW500FS15(), cursorBrush = SolidColor(kWhite))
                }
            }
            suffix?.invoke()
        }
    }


}