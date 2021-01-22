package com.bb.boardborrow.modules.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeleteForm {

    @NotBlank
    private Long deleteId;

}
