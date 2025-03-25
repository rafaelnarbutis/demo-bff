package com.br.customer_manager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @AllArgsConstructor
@NoArgsConstructor

public class CustomerDTO {
    private Long id;
    private String name;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String birthDate;

    private String telephone;

    @SerializedName("logradouro")
    private String street;

    @SerializedName("bairro")
    private String neighborhood;

    private String number;

    @SerializedName("localidade")
    private String city;

    @SerializedName("uf")
    private String state;

    private String postalCode;

}