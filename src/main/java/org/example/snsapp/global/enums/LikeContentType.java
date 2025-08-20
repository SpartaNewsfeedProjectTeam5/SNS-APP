package org.example.snsapp.global.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
enum LikeContentType {
    POST("게시글"),
    COMMENT("댓글");

    private final String name;
}
