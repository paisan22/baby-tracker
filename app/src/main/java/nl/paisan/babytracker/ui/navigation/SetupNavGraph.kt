package nl.paisan.babytracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import nl.paisan.babytracker.ui.layout.DefaultLayout
import nl.paisan.babytracker.ui.screen.addActivity.AddActvitivyScreen
import nl.paisan.babytracker.ui.screen.addPhysicals.AddPhysicalsScreen
import nl.paisan.babytracker.ui.screen.addPhysicals.addWeight.AddWeightScreen
import nl.paisan.babytracker.ui.screen.bio.BioScreen
import nl.paisan.babytracker.ui.screen.overviewActivity.OverviewActivityScreen
import nl.paisan.babytracker.ui.screen.physicalOverview.PhysicalOverviewScreen

@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Destinations.BIO_ROUTE
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
            route = Destinations.ADD_PHYSICALS_ROUTE,
            content = {
                DefaultLayout(
                    screen = Destinations.ADD_PHYSICALS_ROUTE,
                    navHostController = navHostController,
                    content = {
                        AddPhysicalsScreen(navHostController = navHostController)
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