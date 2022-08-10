package com.hzw.wan.domain.mapper

import com.hzw.wan.data.dto.LoginDto
import com.hzw.wan.domain.model.UserInfo
import com.hzw.wan.extend.ifNullOrBlank

fun LoginDto.toUserInfo(): UserInfo {
    return UserInfo(
        admin = admin,
        chapterTops = chapterTops ?: emptyList(),
        collectIds = collectIds ?: emptyList(),
        email = email.ifNullOrBlank(),
        icon = icon.ifNullOrBlank(),
        id = id,
        nickname = nickname.ifNullOrBlank(),
        password = password.ifNullOrBlank(),
        publicName = publicName.ifNullOrBlank(),
        token = token.ifNullOrBlank(),
        type = type,
        username = username.ifNullOrBlank()
    )
}