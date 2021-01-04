package com.bb.boardborrow.modules.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RequestForm {

    @NotBlank
    private String title;

    @NotBlank
    private String description;


    private String photo;

}
