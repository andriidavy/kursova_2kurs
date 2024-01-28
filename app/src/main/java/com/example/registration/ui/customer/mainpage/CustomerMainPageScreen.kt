package com.example.registration.ui.customer.mainpage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.registration.ui.composeFunc.OutlinedSearchTextField
import com.example.registration.ui.theme.MyAppTheme

@Composable
fun CustomerMainPageScreen() {
    MyAppTheme {
        var searchField by rememberSaveable { mutableStateOf("") }
        val itemsList = listOf("Electronics", "Home", "Health", "Entertainment")

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 10.dp),
        ) {
            OutlinedSearchTextField(
                value = searchField,
                onChange = { userInput -> searchField = userInput },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                onSearchClick = {
                    if (searchField.isBlank()) println("Empty field!") else println(
                        searchField
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Categories",
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalItemSpacing = 8.dp

            ) {
                items(itemsList.size) { index ->
                    GridItem(itemList = itemsList, index = index)
                }
            }
        }
    }
}

@Composable
fun GridItem(itemList: List<String>, index: Int) {
    Box(
        modifier = Modifier
            .background(color = Color.White, shape = MaterialTheme.shapes.small)
            .width(100.dp)
            .height(100.dp)
            .shadow(elevation = 1.dp)
            .clickable { println(itemList[index]) },
        contentAlignment = Alignment.Center

    ) {
        Text(
            text = itemList[index],
            style = MaterialTheme.typography.subtitle2
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun TestCustomerMainPageScreen() {
    CustomerMainPageScreen()
}