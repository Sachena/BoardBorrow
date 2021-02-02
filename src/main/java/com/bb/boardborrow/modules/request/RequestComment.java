package com.bb.boardborrow.modules.request;

import com.bb.boardborrow.modules.account.Account;
import com.bb.boardborrow.modules.rent.Rent;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;



@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestComment {

    @Id
    @GeneratedValue
    private Long id;



    private String description;



    private LocalDateTime post;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account author;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;
}
