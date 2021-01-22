package com.bb.boardborrow.modules.rent;

import com.bb.boardborrow.modules.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RentCommentService {
    private final RentCommentRepository rentCommentRepository;


    public void deleteComment(Account account, RentComment deleteComment) {

        rentCommentRepository.delete(deleteComment);
        account.getRentComments().remove(deleteComment);

    }
}
