package com.jtortosasen.randomuserapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jtortosasen.randomuserapp.ui.navigation.AppNavigator
import com.jtortosasen.randomuserapp.ui.theme.RandomUserAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomUserAppTheme {
                AppNavigator()
            }
        }
    }
}