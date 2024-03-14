package com.jtortosasen.randomuserapp.ui.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jtortosasen.randomuserapp.ui.screens.DetailScreen
import com.jtortosasen.randomuserapp.ui.screens.OverviewScreen

object Screen {
    const val Overview = "overview"
    const val Detail = "detail"
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Overview) {
        composable(Screen.Overview) {
            OverviewScreen(navController = navController)
        }
        composable(Screen.Detail) {
            DetailScreen(navController = navController)
        }
    }
}

fun NavController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    graph
        .findNode(route)
        ?.id
        ?.let { nodeId ->
            navigate(
                resId = nodeId,
                args = args,
                navOptions = navOptions,
                navigatorExtras = navigatorExtras
            )
        }
        ?: error("route $route not found")
}
