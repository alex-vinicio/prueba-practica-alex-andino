package com.demo.alexandino.clientes.dto;

import com.demo.alexandino.clientes.domain.Gender;
import com.demo.alexandino.clientes.domain.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import jakarta.validation.constraints.*;

public record ClientRequest(

        @NotBlank(message = "Valor no nulo")
        @Size(min = 10, max = 10, message = "El identificador debe tener exactamente 10 caracteres")
        String identifier,

        @NotBlank(message = "Valor no nulo")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String name,

        @NotNull(message = "El género es obligatorio")
        Gender gender,

        @Min(value = 0, message = "La edad no puede ser negativa")
        @Max(value = 120, message = "La edad no puede superar 120 años")
        int age,

        @NotBlank(message = "Valor no nulo")
        @Size(min = 2, max = 100, message = "La dirección debe tener entre 2 y 100 caracteres")
        String address,

        @NotBlank(message = "Valor no nulo")
        @Size(min = 7, max = 15, message = "El teléfono debe tener entre 7 y 15 caracteres")
        String phone,

        @NotBlank(message = "Valor no nulo")
        @Size(min = 6, max = 20, message = "La contraseña debe tener entre 6 y 20 caracteres")
        String password,

        @NotBlank(message = "Valor no nulo")
        @Email(message = "Debe ser un correo válido")
        String email,

        @NotNull(message = "Valor no nulo")
        Status status
) {}
