package org.example.snsapp.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserPasswordRequest {

    @NotBlank(message = "현재 비밀번호는 필수 입력 항목 입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,255}$",message = "비밀번호는 8글자 이상, 대소문자, 숫자, 특수문자를 각각 1글자 이상 포함해야 합니다.")
    private String currentPassword;

    @NotBlank(message = "새 비밀번호는 필수 입력 항목 입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,255}$",message = "비밀번호는 8글자 이상, 대소문자, 숫자, 특수문자를 각각 1글자 이상 포함해야 합니다.")
    private String newPassword;
}
