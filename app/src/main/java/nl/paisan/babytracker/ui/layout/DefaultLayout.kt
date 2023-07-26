package nl.paisan.babytracker.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ChildCare
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import nl.paisan.babytracker.ui.navigation.Destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultLayout(
    screen: String,
    navHostController: NavHostController,
    content: @Composable () -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val firstItems = listOf(
        Pair(Destinations.BIO_ROUTE, Icons.Outlined.ChildCare)
    )

    val addDataItems = listOf(
        Pair(Destinations.ADD_ACTIVITY_ROUTE, Icons.Outlined.Add),
        Pair(Destinations.ADD_PHYSICALS_ROUTE, Icons.Outlined.Add)
    )

    val overviewDataItems = listOf(
        Pair(Destinations.OVERVIEW_ACTIVITY_ROUTE, Icons.Outlined.List),
        Pair(Destinations.OVERVIEW_PHYSICAL_ROUTE, Icons.Outlined.List),
    )

    val selectedItem = remember { mutableStateOf(screen) }

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                renderItems(
                    items = firstItems,
                    selectedItem = selectedItem.value,
                    scope = scope,
                    drawerState = drawerState,
                    setSelectedItem = {
                        selectedItem.value = it
                        navHostController.navigate(route = it)
                    }
                )

                Spacer(Modifier.height(12.dp))
                Divider()
                itemLabelRow(label = "Add Data")

                renderItems(
                    items = addDataItems,
                    selectedItem = selectedItem.value,
                    scope = scope,
                    drawerState = drawerState,
                    setSelectedItem = {
                        selectedItem.value = it
                        navHostController.navigate(route = it)
                    }
                )

                Divider()
                itemLabelRow(label = "Overview")

                renderItems(
                    items = overviewDataItems,
                    selectedItem = selectedItem.value,
                    scope = scope,
                    drawerState = drawerState,
                    setSelectedItem = {
                        selectedItem.value = it
                        navHostController.navigate(route = it)
                    }
                )

            } },
        gesturesEnabled = true,
        content = {
            Scaffold(
                topBar = {

                    TopAppBar(title = { Text(text = "$screen >>") })
                }
            ) { contentPadding ->
                Column(modifier = Modifier.padding(contentPadding)) {
                    Box(modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                    ) {
                        content()
                    }
                }
            }
        }
    )
}

@Composable
private fun renderItems(
    items: List<Pair<String, ImageVector>>,
    selectedItem: String,
    scope: CoroutineScope,
    drawerState: DrawerState,
    setSelectedItem: (item: String) -> Unit
) {
    items.forEach { item ->
        NavigationDrawerItem(
            icon = { Icon(imageVector = item.second, contentDescription = null)},
            label = { Text(text = item.first) },
            selected = item.first == selectedItem,
            onClick = {
                scope.launch { drawerState.close() }
                setSelectedItem(item.first)
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

@Composable
private fun itemLabelRow(label: String) {
    Row(
        modifier = Modifier
            .padding(start = 12.dp, end = 24.dp, top = 12.dp, bottom = 12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
    ) {
        Text(text = label)
    }
}