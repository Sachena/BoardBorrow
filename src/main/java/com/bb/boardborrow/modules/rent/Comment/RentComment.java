package com.bb.boardborrow.modules.rent.Comment;


import com.bb.boardborrow.modules.account.Account;
import com.bb.boardborrow.modules.rent.Rent;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RentComment {

    @Id
    @GeneratedValue
    private Long id;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "rent_id")
    private Rent rent;

}
