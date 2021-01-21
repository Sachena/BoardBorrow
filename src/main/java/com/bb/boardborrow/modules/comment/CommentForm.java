package com.bb.boardborrow.modules.comment;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
public class CommentForm {


    @NotBlank
    private String description;




}
