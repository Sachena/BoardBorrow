package com.bb.boardborrow.modules.rent;

import com.bb.boardborrow.modules.account.Account;
import com.bb.boardborrow.modules.request.Request;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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



    private String description;



    private LocalDateTime post;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account author;

    @ManyToOne
    @JoinColumn(name = "rent_id")
    private Rent rent;
}
