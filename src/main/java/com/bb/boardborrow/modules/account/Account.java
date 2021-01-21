package com.bb.boardborrow.modules.account;


import com.bb.boardborrow.modules.rent.Rent;
import com.bb.boardborrow.modules.rent.RentComment;
import com.bb.boardborrow.modules.request.Request;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    private String emailCheckToken;

    private LocalDateTime emailCheckTokenGeneratedAt;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    private LocalDateTime joinedAt;

    private boolean emailVerified;

    @OneToMany(mappedBy = "author",fetch = FetchType.EAGER)
    private Set<Request> requests = new HashSet<>();

    @OneToMany(mappedBy = "author",fetch = FetchType.EAGER)
    private Set<Rent> rents = new HashSet<>();

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private Set<RentComment> rentComments = new HashSet<>();

    public void addRequests(Request request){
        this.requests.add(request);
    }

    public void addRents(Rent rent){
        this.rents.add(rent);
    }




    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public boolean canSendConfirmEmail() {
        return this.emailCheckTokenGeneratedAt.isBefore(LocalDateTime.now().minusHours(1));
    }

    public void completeSignUp(){
        this.emailVerified = true;
        this.joinedAt = LocalDateTime.now();
    }



}
