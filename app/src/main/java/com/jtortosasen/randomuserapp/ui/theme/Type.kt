package com.jtortosasen.randomuserapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jtortosasen.randomuserapp.R

// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.oswald_medium, FontWeight.W500)),
        fontWeight = FontWeight.W500,
        fontSize = 16.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_text_semibold, FontWeight.W600)),
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    labelLarge =  TextStyle(
        fontFamily = FontFamily(Font(R.font.sf_pro_text_regular, FontWeight.W400)),
        fontWeight = FontWeight.W400,
        fontSize = 14.sp
    )
)