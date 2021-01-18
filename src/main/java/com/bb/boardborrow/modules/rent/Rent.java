package com.bb.boardborrow.modules.rent;

import com.bb.boardborrow.modules.account.Account;
import com.bb.boardborrow.modules.rent.Comment.RentComment;
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
public class Rent {

    @Id
    @GeneratedValue
    private Long id;

    private String title;


    private String description;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String photo;
    //private int pay;


    private LocalDateTime post;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account author;

    @OneToMany(mappedBy = "comment",fetch = FetchType.EAGER)
    private Set<RentComment> comments = new HashSet<>();




}
