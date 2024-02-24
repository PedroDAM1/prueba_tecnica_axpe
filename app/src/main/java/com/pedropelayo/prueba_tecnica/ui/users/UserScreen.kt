package com.pedropelayo.prueba_tecnica.ui.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.pedropelayo.prueba_tecnica.R
import com.pedropelayo.prueba_tecnica.domain.model.UserModel
import com.pedropelayo.prueba_tecnica.ui.common.theme.AppColors
import com.pedropelayo.prueba_tecnica.ui.common.theme.PruebaTecnicaAxpeTheme
import com.pedropelayo.prueba_tecnica.ui.users.state.UsersPaginatedState

@Composable
fun UsersScreen(userViewModel : UsersViewModel = viewModel()){
    when(val result =  userViewModel.userResult){
        is UsersPaginatedState.Error -> TODO()
        is UsersPaginatedState.Succes -> {
            UserList(
                list = result.data,
                isLoading = userViewModel.isLoading,
                modifier = Modifier.fillMaxSize()
            ) { userViewModel.loadMoreItem() }
        }
    }
}

@Composable
fun UserList(
    list: List<UserModel>,
    modifier: Modifier = Modifier,
    isLoading : Boolean = false,
    onItemsEnd : () -> Unit
){
    if(isLoading){
        LoadingCard()
    }

    //Con este podremos mantener el scroll en la lista
    val scrollState = rememberLazyListState()
    //Este estado comporbará si hemos llegaod hasta el final de la lista
    val isLastItem by remember {
        derivedStateOf {
            scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ==
                    scrollState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(key1 = isLastItem){
        onItemsEnd()
    }

    LazyColumn(
        state = scrollState,
        modifier = modifier
    ){
        itemsIndexed(list){_, user ->
            val name = "${user.firstName} ${user.lastName}"
            UserCard(
                userName = name,
                userEmail =user.email,
                //intenta recuperar la url de la imagen del usuario y si no puede cargar un resource por defecto
                image =user.picture.thumbnail?: user.picture.defaultImage?: painterResource(id = R.drawable.user_ico)
            )
        }
    }

}

@Composable
fun LoadingCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        ProgressIndicatorDefaults.CircularIndeterminateStrokeCap
    }
}

@Composable
fun UserCard(
    userName : String,
    userEmail : String,
    image : Any,
    modifier: Modifier = Modifier,
    onClick : () -> Unit = {},
){
    ConstraintLayout(modifier = modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(8.dp)
    ) {
        val (img, name, email, detail) = createRefs()
        AsyncImage(
            model = image,
            contentDescription = "User Image",
            modifier = Modifier
                .size(64.dp)
                .shadow(4.dp, CircleShape)
                .clip(CircleShape)
                .constrainAs(img) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        UserInfoText(
            text = userName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(name){
                    start.linkTo(img.end, margin = 12.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(email.top)
                    end.linkTo(detail.start)
                    width = Dimension.fillToConstraints
                    horizontalChainWeight = 1f
                }

        )

        UserInfoText(
            text = userEmail,
            fontSize = 16.sp,
            color = AppColors.LightGray,
            modifier = Modifier
                .constrainAs(email){
                    start.linkTo(name.start)
                    end.linkTo(name.end)
                    top.linkTo(name.bottom)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
        )

        Icon(
            painter = painterResource(id = R.drawable.arrow_right_24),
            contentDescription = "",
            tint = AppColors.LightGray,
            modifier = Modifier
                .size(24.dp)
                .constrainAs(detail) {
                    start.linkTo(name.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.wrapContent
                }
        )
    }

}

@Composable
fun UserInfoText(
    modifier: Modifier = Modifier,
    text : String = "",
    fontSize : TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    color : Color = Color.Black,
){
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Start,
        color = color,
        modifier = modifier
    )
}

@Preview(showSystemUi = true)
@Composable
fun UserScreenPreview(){
    PruebaTecnicaAxpeTheme {
        UsersScreen()
    }
}