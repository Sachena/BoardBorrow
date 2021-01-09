package com.bb.boardborrow.modules.rent;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RentForm {
    @NotBlank
    private String title;

    @NotBlank
    private String description;


    private String photo;

}
