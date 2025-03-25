package com.br.customer_manager.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Email(message = "E-mail invalido") @NotNull @Column(unique = true)
    private String email;
    @NotNull @NotBlank
    private String password;
    @Column(name = "birth_date")
    private String birthDate;
    @Column(unique = true)
    @Pattern(regexp = "^(\\d{2})?\\s?\\d{5}-?\\d{4}$", message = "Número de telefone inválido, o número deve ter 11 dígitos.")
    private String telephone;
    private String street;
    @NotNull(message = "Número nao pode ser nulo")
    private String number;
    private String neighborhood;
    @NotNull(message = "CEP nao pode ser nulo")
    @Pattern(regexp = "^[0-9]{5}-?[0-9]{3}$", message = "CEP deve ser válido")
    @Column(name = "postal_code")
    private String postalCode;
    private String state;
    private String city;

}
