package com.joaoju.Users_API.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsersDTO(
    @NotBlank @Size(max = 100) String nome,
    @NotBlank @Email @Size(max = 100) String email,
    @NotBlank @Size(max = 20) String telefone
) {}
