package com.example.crudrapido.dto;
 
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
 
public record StudentDto(
        Long studentId,
 
        @NotBlank(message = "El nombre es obligatorio")
        String firstName,
 
        @NotBlank(message = "El apellido es obligatorio")
        String lastName,
 
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no es válido")
        String email,
 
        String description) {
}