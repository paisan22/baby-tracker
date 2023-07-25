package nl.paisan.babytracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import nl.paisan.babytracker.ui.layout.DefaultLayout
import nl.paisan.babytracker.ui.screen.addActivity.AddActvitivyScreen
import nl.paisan.babytracker.ui.screen.addWeight.AddWeightScreen
import nl.paisan.babytracker.ui.screen.bio.BioScreen
import nl.paisan.babytracker.ui.screen.overviewActivity.OverviewActivityScreen
import nl.paisan.babytracker.ui.screen.physicalOverview.PhysicalOverviewScreen

@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Destinations.ADD_WEIGHT_ROUTE
    ) {
        composable(
            route = Destinations.BIO_ROUTE,
            content = {
                DefaultLayout(
                    screen = Destinations.BIO_ROUTE,
                    navHostController = navHostController,
                    content = {
                        BioScreen()
                    },
                )
            }
        )
        composable(
            route = Destinations.ADD_ACTIVITY_ROUTE,
            content = {
                DefaultLayout(
                    screen = Destinations.ADD_ACTIVITY_ROUTE,
                    navHostController = navHostController,
                    content = {
                        AddActvitivyScreen(navHostController = navHostController)
                    },
                )
            }
        )
        composable(
            route = Destinations.ADD_WEIGHT_ROUTE,
            content = {
                DefaultLayout(
                    screen = Destinations.ADD_WEIGHT_ROUTE,
                    navHostController = navHostController,
                    content = {
                        AddWeightScreen(navHostController = navHostController)
                    }
                )
            }
        )
        composable(
            route = Destinations.OVERVIEW_ACTIVITY_ROUTE,
            content = {
                DefaultLayout(
                    screen = Destinations.OVERVIEW_ACTIVITY_ROUTE,
                    navHostController = navHostController,
                    content = {
                        OverviewActivityScreen(navHostController = navHostController)
                    },
                )
            }
        )

        composable(
            route = Destinations.OVERVIEW_PHYSICAL_ROUTE,
            content = {
                DefaultLayout(
                    screen = Destinations.OVERVIEW_PHYSICAL_ROUTE,
                    navHostController = navHostController,
                    content = {
                        PhysicalOverviewScreen()
                    }
                )
            }
        )
    }
}