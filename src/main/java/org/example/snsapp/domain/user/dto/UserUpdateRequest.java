package org.example.snsapp.domain.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(max = 4, message = "이름은 4자 이하로 입력해주세요.")
    private String username;

    @Min(value = 0, message = "나이는 0 이상으로 입력해주세요.")
    @Max(value = 150, message = "나이는 150 이하로 입력해주세요.")
    private int age;

    @Size(max = 225, message = "링크의 길이가 너무 깁니다.")
    private String profileImage;
}
