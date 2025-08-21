package org.example.snsapp.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class PostUpdateRequest {
    @Length(max = 10, message = "제목은 10글자 이하로 입력해주세요.")
    private String title;
    @Length(max = 255, message = "내용은 255글자 이하로 입력해주세요.")
    private String content;
}