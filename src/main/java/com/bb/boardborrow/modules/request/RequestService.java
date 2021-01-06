package com.bb.boardborrow.modules.request;

import com.bb.boardborrow.modules.account.Account;
import com.bb.boardborrow.modules.account.AccountRepository;
import com.bb.boardborrow.modules.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestService {

    private final RequestRepository requestRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;

    public Request createNewRequest(Request request, Account account) {

        Request newRequest = requestRepository.save(request);
        newRequest.setAuthor(account);
        newRequest.setPost(LocalDateTime.now());
        accountService.addRequest(account,newRequest);
        return newRequest;

    }




    public Optional<Request> getRequest(Long requestId) {


        Optional<Request> request = this.requestRepository.findById(requestId);
        return request;


    }


    public Request getRequestToUpdate(Account account, Long requestId) {
        Request updateRequest = this.requestRepository.findById(requestId).get();
        return updateRequest;
    }

    public void updateRequest(RequestForm requestForm, Request requestToUpdate) {

            modelMapper.map(requestForm,requestToUpdate);
            requestRepository.save(requestToUpdate);

    }

    public void removeRequest(Account account, Request removeRequest) {
        requestRepository.delete(removeRequest);
        account.getRequests().remove(removeRequest);
    }
}
