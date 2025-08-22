package org.example.snsapp.domain.follow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class FollowRequest {

    @NotBlank(message = "이메일은 필수 값 입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Size(max = 40, message = "40자 이내로 입력해주세요.")
    private String targetUserEmail;
}
