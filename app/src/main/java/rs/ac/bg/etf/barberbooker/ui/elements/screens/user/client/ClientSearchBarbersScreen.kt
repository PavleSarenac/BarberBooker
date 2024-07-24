package rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import rs.ac.bg.etf.barberbooker.ui.stateholders.user.client.ClientSearchBarbersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientSearchBarbersScreen(
    clientEmail: String,
    clientSearchBarbersViewModel: ClientSearchBarbersViewModel = hiltViewModel()
) {
    val uiState by clientSearchBarbersViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    var areSearchResultsFetched by rememberSaveable { mutableStateOf(false) }

    var showSortingDialog by rememberSaveable { mutableStateOf(false) }

    val (sortingByNameState, onSortingByNameStateChange) = rememberSaveable { mutableStateOf(false) }
    val (sortingByNameAscendingState, onSortingByNameAscendingStateChange) = rememberSaveable { mutableStateOf(true) }
    val (sortingByNameDescendingState, onSortingByNameDescendingStateChange) = rememberSaveable { mutableStateOf(false) }

    val (sortingByPriceState, onSortingByPriceStateChange) = rememberSaveable { mutableStateOf(false) }
    val (sortingByPriceAscendingState, onSortingByPriceAscendingStateChange) = rememberSaveable { mutableStateOf(true) }
    val (sortingByPriceDescendingState, onSortingByPriceDescendingStateChange) = rememberSaveable { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            query = uiState.query,
            onQueryChange = { clientSearchBarbersViewModel.setQuery(it) },
            onSearch = {
                coroutineScope.launch {
                    val job = clientSearchBarbersViewModel.getSearchResults()
                    job.join()
                    expanded = false
                    areSearchResultsFetched = true
                }
                       },
            active = expanded,
            onActiveChange = { expanded = it },
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.secondary,
                dividerColor = MaterialTheme.colorScheme.onSecondary,
                inputFieldColors = inputFieldColors(
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSecondary,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSecondary
                )
            ),
            leadingIcon = {
                if (!expanded) {
                    Icon(
                        imageVector = Icons.Filled.ContentCut,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.clickable {
                            clientSearchBarbersViewModel.setQuery("")
                            expanded = false
                        }
                    )
                }
            },
            trailingIcon = {
                if (expanded && uiState.query != "") {
                    Icon(
                        imageVector = Icons.Filled.Cancel,
                        contentDescription = "Delete query",
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.clickable {
                            clientSearchBarbersViewModel.setQuery("")
                        }
                    )
                }
                if (!expanded && areSearchResultsFetched && uiState.searchResults.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.clickable {
                            showSortingDialog = true
                        }
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            placeholder = { Text(text = "Search here") }
        ) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                repeat(4) { idx ->
                    val resultText = "Suggestion $idx"
                    ListItem(
                        headlineContent = { Text(resultText) },
                        supportingContent = {
                            Text(
                                text = "Additional info",
                                color = Color.Black
                            )
                                            },
                        leadingContent = { Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color.Black
                        ) },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        modifier =
                        Modifier
                            .clickable {
                                text = resultText
                                expanded = false
                            }
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                    Divider(
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }

        if (!areSearchResultsFetched) return@Box

        if (uiState.searchResults.isEmpty()) {
            Text(
                text = "No results found on BarberBooker",
                modifier = Modifier
                    .padding(start = 16.dp, top = 80.dp, end = 16.dp, bottom = 16.dp)
            )
        } else {
            val sortedSearchResults = clientSearchBarbersViewModel.getSortedSearchResults(
                sortingByNameState, sortingByNameAscendingState, sortingByNameDescendingState,
                sortingByPriceState, sortingByPriceAscendingState, sortingByPriceDescendingState
            )
            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.semantics { traversalIndex = 1f },
            ) {
                items(count = uiState.searchResults.size) {
                    val currentBarbershop = sortedSearchResults[it]
                    ListItem(
                        headlineContent = {
                            Text(currentBarbershop.barbershopName)
                        },
                        supportingContent = {
                            Text(
                                text = "${clientSearchBarbersViewModel.decimalFormat.format(currentBarbershop.price)} RSD",
                                color = MaterialTheme.colorScheme.secondary
                            )

                        },
                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                    Divider(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }

    if (showSortingDialog) {
        AlertDialog(
            onDismissRequest = { showSortingDialog = false },
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(color = MaterialTheme.colorScheme.secondary)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row {
                    IconButton(onClick = { showSortingDialog = false }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close dialog",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .toggleable(
                            value = sortingByPriceState,
                            onValueChange = { onSortingByPriceStateChange(!sortingByPriceState) },
                            role = Role.Checkbox
                        )
                ) {
                    Checkbox(
                        checked = sortingByPriceState,
                        onCheckedChange = null
                    )
                    Text(
                        text = "Sort by price"
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 48.dp, vertical = 8.dp)
                        .toggleable(
                            value = sortingByPriceAscendingState,
                            onValueChange = {
                                if (sortingByPriceState) {
                                    onSortingByPriceDescendingStateChange(false)
                                    onSortingByPriceAscendingStateChange(true)
                                }
                            },
                            role = Role.RadioButton
                        )
                ) {
                    RadioButton(
                        selected = sortingByPriceAscendingState,
                        onClick = null,
                        enabled = sortingByPriceState
                    )
                    Text(
                        text = "Ascending"
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 48.dp, vertical = 8.dp)
                        .toggleable(
                            value = sortingByPriceDescendingState,
                            onValueChange = {
                                if (sortingByPriceState) {
                                    onSortingByPriceAscendingStateChange(false)
                                    onSortingByPriceDescendingStateChange(true)
                                }
                            },
                            role = Role.RadioButton
                        )
                ) {
                    RadioButton(
                        selected = sortingByPriceDescendingState,
                        onClick = null,
                        enabled = sortingByPriceState
                    )
                    Text(
                        text = "Descending"
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .toggleable(
                            value = sortingByNameState,
                            onValueChange = { onSortingByNameStateChange(!sortingByNameState) },
                            role = Role.Checkbox
                        )
                ) {
                    Checkbox(
                        checked = sortingByNameState,
                        onCheckedChange = null
                    )
                    Text(
                        text = "Sort by name"
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 48.dp, vertical = 8.dp)
                        .toggleable(
                            value = sortingByNameAscendingState,
                            onValueChange = {
                                if (sortingByNameState) {
                                    onSortingByNameDescendingStateChange(false)
                                    onSortingByNameAscendingStateChange(true)
                                }
                            },
                            role = Role.RadioButton
                        )
                ) {
                    RadioButton(
                        selected = sortingByNameAscendingState,
                        onClick = null,
                        enabled = sortingByNameState
                    )
                    Text(
                        text = "Ascending"
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 48.dp, vertical = 8.dp)
                        .toggleable(
                            value = sortingByNameDescendingState,
                            onValueChange = {
                                if (sortingByNameState) {
                                    onSortingByNameAscendingStateChange(false)
                                    onSortingByNameDescendingStateChange(true)
                                }
                            },
                            role = Role.RadioButton
                        )
                ) {
                    RadioButton(
                        selected = sortingByNameDescendingState,
                        onClick = null,
                        enabled = sortingByNameState
                    )
                    Text(
                        text = "Descending"
                    )
                }
            }
        }
    }
}