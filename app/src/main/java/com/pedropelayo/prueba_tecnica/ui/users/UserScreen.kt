package com.pedropelayo.prueba_tecnica.ui.users

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.pedropelayo.prueba_tecnica.ui.common.theme.AppColors
import com.pedropelayo.prueba_tecnica.ui.common.theme.PruebaTecnicaAxpeTheme

@Composable
fun UsersScreen(userViewModel : UsersViewModel = viewModel()){

}

@Composable
fun UserCard(
    userName : String,
    userEmail : String,
    imageUrl : String,
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
            model = imageUrl,
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
                .constrainAs(detail){
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