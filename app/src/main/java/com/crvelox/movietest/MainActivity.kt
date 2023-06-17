package com.crvelox.movietest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.crvelox.movietest.data.remote.res.SearchItem
import com.crvelox.movietest.ui.theme.MovieTestTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //https://www.omdbapi.com/?s=batman&apikey=4aba8386

        setContent {
            MovieTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val searchText = remember {
        mutableStateOf("")
    }
    val scrollState = rememberLazyListState()
    val current = remember { derivedStateOf { scrollState.firstVisibleItemIndex } }

    LaunchedEffect(key1 = Unit) {
        viewModel.itemList.value.clear()
        viewModel.getSearchUser(
            pageNo = viewModel.currentPage.value,
            type = "Batman"
        )
    }
    LaunchedEffect(key1 = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ){
        val last = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?:0
//        Toast.makeText(context, "$last  -- ${viewModel.itemList.value.size}", Toast.LENGTH_SHORT).show()

//        if (last == viewModel.itemList.value.size){
//            viewModel.getSearchUser(
//                pageNo = viewModel.currentPage.value +1,
//                type = searchText.value
//            )
//            viewModel.currentPage.value = viewModel.currentPage.value + 1
//        }

    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(value = searchText.value,
            onValueChange = {
                searchText.value = it
            }, trailingIcon = {
                Icon(painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "search",
                    modifier = Modifier.clickable {
                        viewModel.itemList.value.clear()
                        search(viewModel, 1, searchText.value)
                    }
                )
            }, label = {
                Text(text = "Search")
            },
            modifier = Modifier.fillMaxWidth()
        )



        if (!viewModel.loading.value) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(), state = scrollState
            ) {
                viewModel.itemList.value.forEach {
                    item {
                        MovieListItem(searchItem = it)
                        Spacer(Modifier.height(8.dp))
                    }
                }

            }
        } else {
            CircularProgressIndicator(
                Modifier.align(CenterHorizontally)
            )
        }


    }
}

@Composable
fun MovieListItem(searchItem: SearchItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), colors = CardDefaults.cardColors(

        ), elevation = CardDefaults.cardElevation(2.dp), shape = RoundedCornerShape(5)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Image(
                painter = rememberAsyncImagePainter(searchItem.poster),
                contentDescription = "",
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(10.dp)
                    .size(100.dp)
            )
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = "Movie Name: ${searchItem.title}",
                    color = Color.Black,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Year: ${searchItem.year}",
                    color = Color.Black,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }

    }
}

fun search(viewModel: SearchViewModel, pageNo: Int, type: String) {
    viewModel.getSearchUser(
        pageNo, type
    )
}