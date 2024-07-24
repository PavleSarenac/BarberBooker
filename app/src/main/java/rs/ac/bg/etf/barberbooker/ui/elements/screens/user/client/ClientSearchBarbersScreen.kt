package rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import rs.ac.bg.etf.barberbooker.ui.stateholders.user.client.ClientSearchBarbersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientSearchBarbersScreen(
    clientEmail: String,
    clientSearchBarbersViewModel: ClientSearchBarbersViewModel = hiltViewModel()
) {
    val uiState by clientSearchBarbersViewModel.uiState.collectAsState()
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            query = uiState.query,
            onQueryChange = { clientSearchBarbersViewModel.setQuery(it) },
            onSearch = {
                clientSearchBarbersViewModel.getSearchResults()
                expanded = false
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

        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.semantics { traversalIndex = 1f },
        ) {
            items(count = uiState.searchResults.size) {
                val currentBarbershop = uiState.searchResults[it]
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