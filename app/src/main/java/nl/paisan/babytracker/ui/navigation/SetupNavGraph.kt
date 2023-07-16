package nl.paisan.babytracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import nl.paisan.babytracker.ui.layout.DefaultLayout
import nl.paisan.babytracker.ui.screen.ActvitivyScreen
import nl.paisan.babytracker.ui.screen.bio.BioScreen

@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Destinations.BIO_ROUTE
    ) {
        composable(
            route = Destinations.BIO_ROUTE,
            content = {
                DefaultLayout(screen = Destinations.BIO_ROUTE, content = {
                    BioScreen()
                },
                navHostController = navHostController)
            }
        )
        composable(
            route = Destinations.ACTIVITY_ROUTE,
            content = {
                DefaultLayout(screen = Destinations.ACTIVITY_ROUTE, content = {
                    ActvitivyScreen(navHostController = navHostController)
                },
                navHostController = navHostController)
            }
        )
    }
}