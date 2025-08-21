package org.example.snsapp.domain.post.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

// 게시물 기본 요청 DTO
@Getter
@NoArgsConstructor
public class PostBaseRequest {
    @NotNull(message = "제목 입력은 필수 입니다.")
    @Length(min = 1, max = 30, message = "제목은 1~30글자 이내로 입력해주세요.")
    private String title;
    @Length(max = 255, message = "내용은 255글자 이하로 입력해주세요.")
    private String content;
}