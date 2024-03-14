package com.jtortosasen.randomuserapp.ui.screens


import android.os.Bundle
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import co.touchlab.kermit.Logger
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.jtortosasen.domain.models.Error
import com.jtortosasen.domain.models.User
import com.jtortosasen.randomuserapp.R
import com.jtortosasen.randomuserapp.ui.models.toParcelize
import com.jtortosasen.randomuserapp.ui.navigation.Screen.Detail
import com.jtortosasen.randomuserapp.ui.navigation.navigate
import com.jtortosasen.randomuserapp.ui.viewmodels.OverviewViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun OverviewScreen(navController: NavController, vm: OverviewViewModel = koinViewModel()) {
    val state by vm.state.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            if (!state.toggleSearch) {
                TopAppBar(
                    title = {
                        Text(stringResource(R.string.topbar_headline), style = MaterialTheme.typography.headlineLarge)
                    },
                    actions = {
                        if (!state.toggleSearch) {
                            IconButton(onClick = { vm.toggleSearch(true) }) {
                                Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.search_topbar_content_description))
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.topbar_navigation_content_description))
                        }
                    }
                )
            } else {
                val keyboardController = LocalSoftwareKeyboardController.current
                val searchResults by vm.searchResults.collectAsStateWithLifecycle()

                SearchBar(
                    query = vm.searchQuery,
                    onQueryChange = vm::changeSearchQuery,
                    onSearch = { keyboardController?.hide() },
                    placeholder = {
                        Text(text = stringResource(R.string.search_label), style = MaterialTheme.typography.headlineLarge)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        if (vm.searchQuery.isNotEmpty()) {
                            IconButton(onClick = { vm.changeSearchQuery("") }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    contentDescription = stringResource(R.string.clean_label_content_description)
                                )
                            }
                        }
                    },
                    active = state.toggleSearch,
                    onActiveChange = vm::toggleSearch,
                    tonalElevation = 0.dp,
                    content = {
                        if (searchResults.isEmpty()) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = stringResource(R.string.search_no_users_found),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(searchResults) { user ->
                                    ItemList(item = user, navController = navController)
                                }
                            }
                        }
                    },
                )
            }
        }
    ) { innerPadding ->

        if (state.error == null) {
            val refreshState = rememberPullRefreshState(state.refreshing, vm::refreshList)
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .pullRefresh(refreshState)
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.matchParentSize()
                ) {
                    items(state.listUsers) { user ->
                        ItemList(item = user, navController = navController)
                    }
                }
                LaunchedEffect(listState) {
                    snapshotFlow { listState.layoutInfo.visibleItemsInfo }
                        .collect { visibleItems ->
                            val lastVisible =
                                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                                    ?: return@collect
                            val threshold = state.listUsers.size - 5

                            if (lastVisible >= threshold && !state.loading) {
                                vm.paginateUserList()
                            }
                        }
                }
                PullRefreshIndicator(
                    state.refreshing,
                    refreshState,
                    Modifier.align(Alignment.TopCenter)
                )

                if (state.loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        } else {
            state.error?.let { error ->
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    when (error) {
                        is Error.Server -> {
                            Text(text = "Server error with code: ${error.code}")
                        }

                        is Error.Connectivity -> {
                            Text(text = "Connectivity error")
                        }

                        is Error.Unknown -> {
                            Text(text = "Unknown error: ${error.message}")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ItemList(item: User, navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .clickable {
                val userBundle = Bundle().apply {
                    putParcelable("userKey", item.toParcelize())
                }
                navController.navigate(Detail, userBundle)
            }
    ) {
        AsyncImage(
            model = item.image,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 8.dp, end = 16.dp)
                .size(52.dp)
                .clip(CircleShape)
        )
        Box(
            Modifier
                .fillMaxSize()
                .drawBehind {
                    val strokeWidth = 1 * density
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        Color(231, 231, 231, 255),
                        Offset(0f, y),
                        Offset(size.width, y),
                        strokeWidth
                    )
                }
                .padding(vertical = 18.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = item.email,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(142, 142, 147)
                )
            }
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Ir",
                tint = Color(196, 196, 196),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
            )
        }
    }
}