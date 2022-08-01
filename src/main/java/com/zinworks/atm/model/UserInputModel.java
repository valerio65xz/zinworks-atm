package com.zinworks.atm.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class UserInputModel {

    @NotBlank
    private String accountNumber;
    @NotNull
    private Integer pin;

}