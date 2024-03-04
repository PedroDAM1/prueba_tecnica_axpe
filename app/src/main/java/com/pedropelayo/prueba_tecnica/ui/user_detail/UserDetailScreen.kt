package com.pedropelayo.prueba_tecnica.ui.user_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.AsyncImage
import com.pedropelayo.prueba_tecnica.R
import com.pedropelayo.prueba_tecnica.domain.model.UserModel
import com.pedropelayo.prueba_tecnica.domain.utils.extensions.toLocalDateFormat
import com.pedropelayo.prueba_tecnica.ui.common.components.LoadingCard
import com.pedropelayo.prueba_tecnica.ui.common.theme.AppColors
import com.pedropelayo.prueba_tecnica.ui.user_detail.state.UserDetailState


class UserDetailScreen(private val userUuid : String) : Screen{
    @Composable
    override fun Content() {
        val viewModel : UserDetailViewModel = getViewModel()
        UserDetailScreenContent(viewModel)
    }


    @Composable
    private fun UserDetailScreenContent(
        userDetailViewModel : UserDetailViewModel
    ){
        userDetailViewModel.loadUser(userUuid)

        when (val state = userDetailViewModel.state) {
            is UserDetailState.Error -> Error(msg = state.message)
            UserDetailState.Loading -> Loading()
            is UserDetailState.Success -> UserSuccess(state.user)
        }
    }

    @Composable
    fun Loading(){
        Box(modifier = Modifier.fillMaxSize()) {
            LoadingCard(modifier = Modifier.align(Alignment.Center))
        }
    }

    @Composable
    fun Error(msg : String){

        Box (modifier = Modifier.fillMaxSize()){
            Text(
                text = msg,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp)
            )
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun UserSuccess(user : UserModel){
        val navigator = LocalNavigator.current

        Scaffold(
            topBar = { TopAppBar(
                title = { Text(text = "Miriam Sanchez", color = AppColors.WhiteSmoke) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    IconButton(onClick = { navigator?.popUntilRoot() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back_24),
                            contentDescription = null,
                            tint = AppColors.WhiteSmoke
                        )
                    }
                }
            )}
        ) {
            UserContent(user)
        }
    }

    @Composable
    private fun UserContent(
        user : UserModel,
        modifier: Modifier = Modifier
    ){
        Column(modifier = modifier) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxHeight(.2f)
                    .fillMaxWidth()
            )

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                val (image, camera, edit) = createRefs()

                AsyncImage(
                    model = user.picture.large?: painterResource(id = R.drawable.user_ico),
                    contentDescription = stringResource(R.string.profile_user_photo),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .offset(y = (-36).dp)
                        .size(72.dp)
                        .clip(CircleShape)
                        .border(2.dp, color = AppColors.WhiteSmoke, shape = CircleShape)
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )

                Icon(
                    painter = painterResource(id = R.drawable.camera_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .constrainAs(camera) {
                            top.linkTo(parent.top)
                            end.linkTo(edit.start, margin = 24.dp)
                            bottom.linkTo(parent.bottom)
                        }
                )

                Icon(
                    painter = painterResource(id = R.drawable.edit_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .constrainAs(edit) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                )
            }

            UserInfoContent(user)
        }
    }

    @Composable
    private fun UserInfoContent(
        user : UserModel,
        modifier: Modifier = Modifier,
    ){
        Column (
            modifier = modifier
        ) {
            val horizontalPadding = 12.dp
            val vertialPadding = 12.dp

            UserInfo(
                iconResource = painterResource(id = R.drawable.user_icon),
                fieldName = stringResource(R.string.name_and_lastname),
                fieldValue = "${user.firstName} ${user.lastName}",
                modifier = Modifier.padding(horizontal = horizontalPadding, vertical = vertialPadding)
            )
            UserInfo(
                iconResource = painterResource(id = R.drawable.email_icon),
                fieldName = stringResource(id = R.string.email),
                fieldValue = user.email,
                modifier = Modifier.padding(horizontal = horizontalPadding, vertical = vertialPadding)
            )
            UserInfo(
                iconResource = painterResource(id = user.gender.iconResource),
                fieldName = stringResource(R.string.genre),
                fieldValue = stringResource(id = user.gender.stringResource),
                modifier = Modifier.padding(horizontal = horizontalPadding, vertical = vertialPadding)
            )
            UserInfo(
                iconResource = painterResource(id = R.drawable.calendar_icon),
                fieldName = stringResource(R.string.registered_date),
                fieldValue = user.registrationDate.toLocalDateFormat(),
                modifier = Modifier.padding(horizontal = horizontalPadding, vertical = vertialPadding)
            )
            UserInfo(
                iconResource = painterResource(id = R.drawable.phone_icon),
                fieldName = stringResource(R.string.phone),
                fieldValue = user.phone,
                modifier = Modifier.padding(horizontal = horizontalPadding, vertical = vertialPadding)
            )

        }
    }

    @Composable
    private fun UserInfo(
        iconResource : Painter,
        fieldName : String,
        fieldValue : String,
        modifier: Modifier = Modifier
    ){
        ConstraintLayout (
            modifier = modifier
                .fillMaxWidth()
        ){
            val (icon, name, value, separator) = createRefs()
            val iconSize = 24.dp
            val iconSpacing = 16.dp


            Icon(
                painter = iconResource,
                contentDescription = null,
                modifier = Modifier
                    .size(iconSize)
                    .constrainAs(icon) {
                        top.linkTo(name.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(value.bottom)
                    }
            )

            Text(
                text = fieldName,
                color = AppColors.LightGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(name){
                        start.linkTo(icon.end, margin = iconSpacing)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(value.top)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = fieldValue,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .constrainAs(value){
                        top.linkTo(name.bottom, margin = 6.dp)
                        start.linkTo(name.start)
                        end.linkTo(name.end)
                        bottom.linkTo(separator.top)
                        width = Dimension.fillToConstraints
                    }
            )

            HorizontalDivider(
                color = AppColors.LightGray.copy( alpha = .4f ),
                modifier = Modifier
                    .constrainAs(separator){
                        top.linkTo(value.bottom, margin = 6.dp)
                        start.linkTo(value.start)
                        end.linkTo(value.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    })
        }
    }
}

