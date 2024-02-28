package com.pedropelayo.prueba_tecnica.ui.users

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.pedropelayo.prueba_tecnica.ui.common.components.InputDialog
import com.pedropelayo.prueba_tecnica.ui.common.theme.AppColors
import com.pedropelayo.prueba_tecnica.ui.users.state.UsersPaginatedState

object UserScreen{
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UsersScreen(userViewModel : UsersViewModel = viewModel()){
        var showDialogName by remember { mutableStateOf(false) }
        var showDialogEmail by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.contacts), fontWeight = FontWeight.Bold)
                    },
                    actions = {
                        UserMenu(
                            onNameClick = { showDialogName = true },
                            onEmailClick = { showDialogEmail = true }
                        )
                    }
                )
            }
        ) { innerPadding ->
            UserScreenContent(userViewModel = userViewModel, modifier =  Modifier.padding(innerPadding))
        }

        InputDialog(
            title = stringResource(id = R.string.find_by_name),
            showDialog = showDialogName,
            label = stringResource(R.string.name),
            onDismiss = { showDialogName = false },
            onConfirm = { value ->
                showDialogName = false
                userViewModel.searchByName(value)
            })

        InputDialog(
            title = stringResource(id = R.string.find_by_email),
            showDialog = showDialogEmail,
            label = stringResource(R.string.email),
            onDismiss = { showDialogEmail = false },
            onConfirm = { value ->
                showDialogEmail = false
                userViewModel.searchByEmail(value)
            })
    }

    @Composable
    private fun UserMenu(
        onNameClick : () -> Unit,
        onEmailClick : () -> Unit,
    ){
        var expanded by remember { mutableStateOf(false) }


        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Filled.MoreVert, contentDescription = stringResource(R.string.drop_down_menu))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.find_by_email)) },
                onClick = onEmailClick
            )

            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.find_by_name)) },
                onClick = onNameClick
            )
        }


    }

    @Composable
    private fun UserScreenContent(
        modifier: Modifier = Modifier,
        userViewModel: UsersViewModel,
    ){
        Box (modifier = modifier.fillMaxSize()){
            if(userViewModel.isLoading){
                LoadingCard(
                    modifier = Modifier
                        .align(
                            Alignment.Center
                        )
                )
            }

            when(val result =  userViewModel.userResult){
                is UsersPaginatedState.Error -> {
                    DisplayError(error = result.message, Modifier.align(Alignment.Center))
                }
                is UsersPaginatedState.Succes -> {
                    UserList(
                        list = result.data,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    ) {
                        if(!userViewModel.isFiltering)
                            userViewModel.loadMoreItem()
                    }
                }
            }
        }
    }

    @Composable
    fun DisplayError(error : String, modifier: Modifier = Modifier){
        Text(text = error, modifier)
    }

    @Composable
    private fun UserList(
        list: List<UserModel>,
        modifier: Modifier = Modifier,
        onItemsEnd : () -> Unit
    ){
        //Si la lista llega vacia, pero sin estado de error es por que es la primera vez que estamos cargando
        if(list.isEmpty()) onItemsEnd()

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
            //El launched Effect se llama cuando cambia el estado, tanto para true como para false, por lo que deberemos hacer una segunda comprobacion
            if(isLastItem) onItemsEnd()
        }

        LazyColumn(
            state = scrollState,
            modifier = modifier
        ){
            itemsIndexed(list){_, user ->
                Column {
                    val name = "${user.firstName} ${user.lastName}"
                    UserCard(
                        userName = name,
                        userEmail =user.email,
                        //intenta recuperar la url de la imagen del usuario y si no puede cargar un resource por defecto
                        image =user.picture.thumbnail?: user.picture.defaultImage?: painterResource(id = R.drawable.user_ico)
                    )
                    Spacer(modifier = Modifier.size(12.dp))

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth(.8f)
                            .align(Alignment.End)
                    )

                    Spacer(modifier = Modifier.size(12.dp))

                }

            }
        }

    }

    @Composable
    private fun LoadingCard(
        modifier: Modifier = Modifier
    ) {
        Card(modifier = modifier) {
            CircularProgressIndicator(
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .padding(24.dp)
            )
        }
    }
    @Composable
    private fun UserCard(
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
                contentDescription = stringResource(R.string.user_image),
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
                    .constrainAs(name) {
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
            contentDescription = stringResource(R.string.show_user_detail),
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
    private fun UserInfoText(
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
}

@Preview(showSystemUi = true)
@Composable
fun UserScreenPreview(){
//    PruebaTecnicaAxpeTheme {
        UserScreen.UsersScreen()
//    }
}
