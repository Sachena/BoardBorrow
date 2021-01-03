package com.bb.boardborrow.modules.request;

import com.bb.boardborrow.modules.account.Account;
import com.bb.boardborrow.modules.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestService {

    private final RequestRepository requestRepository;
    private final AccountService accountService;

    public Request createNewRequest(Request request, Account account) {

        Request newRequest = requestRepository.save(request);
        newRequest.setAuthor(account);
        newRequest.setPost(LocalDateTime.now());
        accountService.addRequest(account,newRequest);
        return newRequest;

    }
}
