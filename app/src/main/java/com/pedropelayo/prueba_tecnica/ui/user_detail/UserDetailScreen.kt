package com.pedropelayo.prueba_tecnica.ui.user_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.FontScaling
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.pedropelayo.prueba_tecnica.R
import com.pedropelayo.prueba_tecnica.ui.common.theme.AppColors

@Composable
fun UserDetailScreen(){
    Column {
        //UserInfo()
    }
}

@Composable
fun UserInfo(
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

        Icon(
            painter = iconResource,
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .constrainAs(icon) {
                    top.linkTo(name.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(value.bottom)
                }
        )

        Text(
            text = fieldName,
            color = AppColors.LightGray,
            modifier = Modifier
                .constrainAs(name){
                    start.linkTo(icon.end, margin = 12.dp)
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

@Preview(showSystemUi = true)
@Composable
fun UserDetailScreenPreview(){
    UserDetailScreen()
}