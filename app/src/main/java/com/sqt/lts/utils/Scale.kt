package com.example.lts.utils

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

var height: Dp = 0.dp
var width: Dp = 0.dp

@Composable
fun SetScreenSize() {
    val orientation = LocalConfiguration.current.orientation
    if (orientation == ORIENTATION_PORTRAIT) {
        height = LocalConfiguration.current.screenHeightDp.dp
        width = LocalConfiguration.current.screenWidthDp.dp
    } else {
        height = LocalConfiguration.current.screenWidthDp.dp
        width = LocalConfiguration.current.screenHeightDp.dp
    }
    SdpDimensions.SetSdpSize()
}

@Composable
fun Dp.scaleSize(withHeight: Boolean = false): Dp {
    return if (withHeight) {
        ((this.times(height.value)) / 926.dp).dp
    } else {
        ((this.times(width.value)) / 428.dp).dp
    }
}

object SdpDimensions {
    var sdp_1 = 1.dp
    var sdp_2 = 2.dp
    var sdp_3 = 3.dp
    var sdp_4 = 4.dp
    var sdp_5 = 5.dp
    var sdp_6 = 6.dp
    var sdp_7 = 7.dp
    var sdp_8 = 8.dp
    var sdp_9 = 9.dp
    var sdp_10 = 10.dp
    var sdp_11 = 11.dp
    var sdp_12 = 12.dp
    var sdp_13 = 13.dp
    var sdp_14 = 14.dp
    var sdp_15 = 15.dp
    var sdp_16 = 16.dp
    var sdp_17 = 17.dp
    var sdp_18 = 18.dp
    var sdp_19 = 19.dp
    var sdp_20 = 20.dp
    var sdp_21 = 21.dp
    var sdp_22 = 22.dp
    var sdp_23 = 23.dp
    var sdp_24 = 24.dp
    var sdp_25 = 25.dp
    var sdp_26 = 26.dp
    var sdp_27 = 27.dp
    var sdp_28 = 28.dp
    var sdp_29 = 29.dp
    var sdp_30 = 30.dp
    var sdp_31 = 31.dp
    var sdp_32 = 32.dp
    var sdp_33 = 33.dp
    var sdp_34 = 34.dp
    var sdp_35 = 35.dp
    var sdp_36 = 36.dp
    var sdp_37 = 37.dp
    var sdp_38 = 38.dp
    var sdp_39 = 39.dp
    var sdp_40 = 40.dp
    var sdp_41 = 41.dp
    var sdp_42 = 42.dp
    var sdp_43 = 43.dp
    var sdp_44 = 44.dp
    var sdp_45 = 45.dp
    var sdp_46 = 46.dp
    var sdp_47 = 47.dp
    var sdp_48 = 48.dp
    var sdp_49 = 49.dp
    var sdp_50 = 50.dp
    var sdp_51 = 51.dp
    var sdp_52 = 52.dp
    var sdp_53 = 53.dp
    var sdp_54 = 54.dp
    var sdp_55 = 55.dp
    var sdp_56 = 56.dp
    var sdp_57 = 57.dp
    var sdp_58 = 58.dp
    var sdp_59 = 59.dp
    var sdp_60 = 60.dp
    var sdp_61 = 61.dp
    var sdp_62 = 62.dp
    var sdp_63 = 63.dp
    var sdp_64 = 64.dp
    var sdp_65 = 65.dp
    var sdp_66 = 66.dp
    var sdp_67 = 67.dp
    var sdp_68 = 68.dp
    var sdp_69 = 69.dp
    var sdp_70 = 70.dp
    var sdp_71 = 71.dp
    var sdp_72 = 72.dp
    var sdp_73 = 73.dp
    var sdp_74 = 74.dp
    var sdp_75 = 75.dp
    var sdp_76 = 76.dp
    var sdp_77 = 77.dp
    var sdp_78 = 78.dp
    var sdp_79 = 79.dp
    var sdp_80 = 80.dp
    var sdp_81 = 81.dp
    var sdp_82 = 82.dp
    var sdp_83 = 83.dp
    var sdp_84 = 84.dp
    var sdp_85 = 85.dp
    var sdp_86 = 86.dp
    var sdp_87 = 87.dp
    var sdp_88 = 88.dp
    var sdp_89 = 89.dp
    var sdp_90 = 90.dp
    var sdp_91 = 91.dp
    var sdp_92 = 92.dp
    var sdp_93 = 93.dp
    var sdp_94 = 94.dp
    var sdp_95 = 95.dp
    var sdp_96 = 96.dp
    var sdp_97 = 97.dp
    var sdp_98 = 98.dp
    var sdp_99 = 99.dp
    var sdp_100 = 100.dp

    @Composable
    fun SetSdpSize() {
        sdp_1 = 1.dp.scaleSize()
        sdp_2 = 2.dp.scaleSize()
        sdp_3 = 3.dp.scaleSize()
        sdp_4 = 4.dp.scaleSize()
        sdp_5 = 5.dp.scaleSize()
        sdp_6 = 6.dp.scaleSize()
        sdp_7 = 7.dp.scaleSize()
        sdp_8 = 8.dp.scaleSize()
        sdp_9 = 9.dp.scaleSize()
        sdp_10 = 10.dp.scaleSize()
        sdp_11 = 11.dp.scaleSize()
        sdp_12 = 12.dp.scaleSize()
        sdp_13 = 13.dp.scaleSize()
        sdp_14 = 14.dp.scaleSize()
        sdp_15 = 15.dp.scaleSize()
        sdp_16 = 16.dp.scaleSize()
        sdp_17 = 17.dp.scaleSize()
        sdp_18 = 18.dp.scaleSize()
        sdp_19 = 19.dp.scaleSize()
        sdp_20 = 20.dp.scaleSize()
        sdp_21 = 21.dp.scaleSize()
        sdp_22 = 22.dp.scaleSize()
        sdp_23 = 23.dp.scaleSize()
        sdp_24 = 24.dp.scaleSize()
        sdp_25 = 25.dp.scaleSize()
        sdp_26 = 26.dp.scaleSize()
        sdp_27 = 27.dp.scaleSize()
        sdp_28 = 28.dp.scaleSize()
        sdp_29 = 29.dp.scaleSize()
        sdp_30 = 30.dp.scaleSize()
        sdp_31 = 31.dp.scaleSize()
        sdp_32 = 32.dp.scaleSize()
        sdp_33 = 33.dp.scaleSize()
        sdp_34 = 34.dp.scaleSize()
        sdp_35 = 35.dp.scaleSize()
        sdp_36 = 36.dp.scaleSize()
        sdp_37 = 37.dp.scaleSize()
        sdp_38 = 38.dp.scaleSize()
        sdp_39 = 39.dp.scaleSize()
        sdp_40 = 40.dp.scaleSize()
        sdp_41 = 41.dp.scaleSize()
        sdp_42 = 42.dp.scaleSize()
        sdp_43 = 43.dp.scaleSize()
        sdp_44 = 44.dp.scaleSize()
        sdp_45 = 45.dp.scaleSize()
        sdp_46 = 46.dp.scaleSize()
        sdp_47 = 47.dp.scaleSize()
        sdp_48 = 48.dp.scaleSize()
        sdp_49 = 49.dp.scaleSize()
        sdp_50 = 50.dp.scaleSize()
        sdp_51 = 51.dp.scaleSize()
        sdp_52 = 52.dp.scaleSize()
        sdp_53 = 53.dp.scaleSize()
        sdp_54 = 54.dp.scaleSize()
        sdp_55 = 55.dp.scaleSize()
        sdp_56 = 56.dp.scaleSize()
        sdp_57 = 57.dp.scaleSize()
        sdp_58 = 58.dp.scaleSize()
        sdp_59 = 59.dp.scaleSize()
        sdp_60 = 60.dp.scaleSize()
        sdp_61 = 61.dp.scaleSize()
        sdp_62 = 62.dp.scaleSize()
        sdp_63 = 63.dp.scaleSize()
        sdp_64 = 64.dp.scaleSize()
        sdp_65 = 65.dp.scaleSize()
        sdp_66 = 66.dp.scaleSize()
        sdp_67 = 67.dp.scaleSize()
        sdp_68 = 68.dp.scaleSize()
        sdp_69 = 69.dp.scaleSize()
        sdp_70 = 70.dp.scaleSize()
        sdp_71 = 71.dp.scaleSize()
        sdp_72 = 72.dp.scaleSize()
        sdp_73 = 73.dp.scaleSize()
        sdp_74 = 74.dp.scaleSize()
        sdp_75 = 75.dp.scaleSize()
        sdp_76 = 76.dp.scaleSize()
        sdp_77 = 77.dp.scaleSize()
        sdp_78 = 78.dp.scaleSize()
        sdp_79 = 79.dp.scaleSize()
        sdp_80 = 80.dp.scaleSize()
        sdp_81 = 81.dp.scaleSize()
        sdp_82 = 82.dp.scaleSize()
        sdp_83 = 83.dp.scaleSize()
        sdp_84 = 84.dp.scaleSize()
        sdp_85 = 85.dp.scaleSize()
        sdp_86 = 86.dp.scaleSize()
        sdp_87 = 87.dp.scaleSize()
        sdp_88 = 88.dp.scaleSize()
        sdp_89 = 89.dp.scaleSize()
        sdp_90 = 90.dp.scaleSize()
        sdp_91 = 91.dp.scaleSize()
        sdp_92 = 92.dp.scaleSize()
        sdp_93 = 93.dp.scaleSize()
        sdp_94 = 94.dp.scaleSize()
        sdp_95 = 95.dp.scaleSize()
        sdp_96 = 96.dp.scaleSize()
        sdp_97 = 97.dp.scaleSize()
        sdp_98 = 98.dp.scaleSize()
        sdp_99 = 99.dp.scaleSize()
        sdp_100 = 100.dp.scaleSize()
    }
}

@Composable
fun TextUnit.scaleFont(withHeight: Boolean = false): TextUnit {
    return if (withHeight) {
        ((this.times(height.value)).value / 926.dp.value).sp
    } else {
        ((this.times(width.value)).value / 428.dp.value).sp
    }
}