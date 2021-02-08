package com.bb.boardborrow.modules.request;

import com.bb.boardborrow.modules.account.Account;
import com.bb.boardborrow.modules.account.AccountRepository;
import com.bb.boardborrow.modules.account.AccountService;
import com.bb.boardborrow.modules.rent.Rent;
import com.bb.boardborrow.modules.rent.RentComment;
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
    private final RequestCommentRepository requestCommentRepository;

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

    public void addComment(Account account, Request request, String description) {

        RequestComment newRequestComment = new RequestComment();
        newRequestComment.setDescription(description);
        newRequestComment.setPost(LocalDateTime.now());
        newRequestComment.setAuthor(account);
        newRequestComment.setRequest(request);
        requestCommentRepository.save(newRequestComment);
        account.getRequestComments().add(newRequestComment);
        request.getRequestComments().add(newRequestComment);
    }

    public void deleteComment(Account account, Request request, RequestComment deleteComment) {

        requestCommentRepository.delete(deleteComment);
        account.getRequestComments().remove(deleteComment);
        request.getRequestComments().remove(deleteComment);

    }
}
