package org.example.snsapp.domain.auth.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class AuthSignUpRequest {

    @NotBlank(message = "이름, 나이, 아이디와 비밀번호는 필수 입력 항목입니다.")
    @Length(min = 1, max = 4)
    private String username;
    @NotBlank(message = "이름, 나이, 아이디와 비밀번호는 필수 입력 항목입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Length(min = 1, max = 40)
    private String email;
    @NotNull(message = "이름, 나이, 아이디와 비밀번호는 필수 입력 항목입니다.")
    @Min(value = 1, message = "나이는 1 이상이어야 합니다")
    private int age;
    @NotBlank(message = "이름, 나이, 아이디와 비밀번호는 필수 입력 항목입니다.")
    @Length(min = 1, max = 255)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,255}$", message = "비밀번호는 8글자 이상, 대소문자, 수자, 특수문자를 각각 1글자 이상 포함해야 합니다.")
    private String password;

    private String profileImage;
}