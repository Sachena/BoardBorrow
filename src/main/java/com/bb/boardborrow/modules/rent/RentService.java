package com.bb.boardborrow.modules.rent;


import com.bb.boardborrow.modules.account.Account;
import com.bb.boardborrow.modules.account.AccountService;
import com.bb.boardborrow.modules.request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class RentService {

    private final AccountService accountService;
    private final RentRepository rentRepository;

    public Rent createNewRent(Rent rent, Account account) {

        Rent newRent = rentRepository.save(rent);
        newRent.setAuthor(account);
        newRent.setPost(LocalDateTime.now());
        accountService.addRent(account,newRent);
        return newRent;

    }
}
