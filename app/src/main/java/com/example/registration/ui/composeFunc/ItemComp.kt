package com.example.registration.ui.composeFunc

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.registration.R

@Composable
fun ItemComp() {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.xiaomi_mi_11_lite),
            contentDescription = "itemPhoto",
            modifier = Modifier.size(150.dp)
        )
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = "Product Template",
                style = MaterialTheme.typography.h6)

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "In stock: 10",
                style = MaterialTheme.typography.subtitle1)

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Price: 1000$",
                style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun TestItemComp() {
    ItemComp()
}