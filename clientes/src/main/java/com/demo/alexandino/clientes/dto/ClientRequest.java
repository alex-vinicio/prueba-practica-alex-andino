package com.demo.alexandino.clientes.dto;

import com.demo.alexandino.clientes.domain.Gender;
import com.demo.alexandino.clientes.domain.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import jakarta.validation.constraints.*;

public record ClientRequest(

        @NotBlank(message = "Valor no nulo")
        String identifier,

        @NotBlank(message = "Valor no nulo")
        String name,

        @NotNull(message = "El género es obligatorio")
        Gender gender,

        @Min(value = 0, message = "La edad no puede ser negativa")
        int age,

        @NotBlank(message = "Valor no nulo")
        String address,

        @NotBlank(message = "Valor no nulo")
        String phone,

        @NotBlank(message = "Valor no nulo")
        @Size(min = 1, max = 20, message = "La contraseña debe tener entre 6 y 20 caracteres")
        String password,

        @NotBlank(message = "Valor no nulo")
        @Email(message = "Debe ser un correo válido")
        String email,

        @NotNull(message = "Valor no nulo")
        Status status
) {}
