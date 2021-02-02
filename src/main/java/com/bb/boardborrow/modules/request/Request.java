package com.bb.boardborrow.modules.request;


import com.bb.boardborrow.modules.account.Account;
import lombok.*;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private LocalDateTime start;
    private LocalDateTime end;


    private String description;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String photo;
    //private int pay;

    private LocalDateTime post;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account author;

    @OneToMany(mappedBy = "request", fetch = FetchType.EAGER)
    private Set<RequestComment> requestComments = new HashSet<>();


}
