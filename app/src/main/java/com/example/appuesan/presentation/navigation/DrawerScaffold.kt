package com.example.appuesan.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appuesan.data.remote.FireBaseAuthManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DraweScaffold(
    navController: NavController,
    content: @Composable () -> Unit
){
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer( drawerState = drawerState,
        drawerContent ={
            ModalDrawerSheet{
                Spacer(modifier = Modifier.height(12.dp))
                Text("Menu Principal", Modifier.padding(16.dp))
                //NavigationDrawerItem
                NavigationDrawerItem(
                    label= {Text("Permissions")},
                    selected = false,
                    onClick ={
                        navController.navigate("permissions")
                    }
                )
                NavigationDrawerItem(
                    label= {Text("Favorites")},
                    selected = false,
                    onClick ={
                        navController.navigate("favorites")
                        }
                )
                NavigationDrawerItem(
                    label= {Text("Real Time")},
                    selected = false,
                    onClick ={
                        navController.navigate("realtime")
                    }
                )
                NavigationDrawerItem(
                    label= {Text("Supabase Storage")},
                    selected = false,
                    onClick ={
                        navController.navigate("supabase")
                    }
                )
                NavigationDrawerItem(
                    label= {Text("Api Football")},
                    selected = false,
                    onClick ={
                        navController.navigate("apifootball")
                    }
                )
                NavigationDrawerItem(
                    label= {Text("Firebase logout")},
                    selected = false,
                    onClick ={
                        FireBaseAuthManager.logout()
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title ={Text("UESAN APP")},
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch{ drawerState.open() }
                            }
                        ){
                            Icon(Icons.Default.Menu, "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)){
                content()
            }
        }
    }
}