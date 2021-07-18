package com.bb.boardborrow.modules.rent;


import com.bb.boardborrow.modules.account.Account;
import com.bb.boardborrow.modules.account.AccountService;
import com.bb.boardborrow.modules.request.Request;
import com.bb.boardborrow.modules.request.RequestForm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class RentService {

    private final AccountService accountService;
    private final RentRepository rentRepository;
    private final ModelMapper modelMapper;
    private final RentCommentRepository rentCommentRepository;

    public Rent createNewRent(Rent rent, Account account) {



        Rent newRent = rentRepository.save(rent);
        newRent.setAuthor(account);
        newRent.setPost(LocalDateTime.now());
        accountService.addRent(account,newRent);
        return newRent;

    }

    public Rent getRentToUpdate(Account account, Long rentId) {

        Rent updateRent = this.rentRepository.findById(rentId).get();
        return updateRent;
    }

    public void updateRequest(RentForm rentForm, Rent rentToUpdate) {

        modelMapper.map(rentForm,rentToUpdate);
        rentRepository.save(rentToUpdate);

    }

    public void removeRent(Account account, Rent removeRent) {
        rentRepository.delete(removeRent);
        account.getRents().remove(removeRent);
    }

    public void addComment(Account account, Rent rent, String description) {
        RentComment newRentComment = new RentComment();
        newRentComment.setDescription(description);
        newRentComment.setPost(LocalDateTime.now());
        newRentComment.setAuthor(account);
        newRentComment.setRent(rent);
        rentCommentRepository.save(newRentComment);
        account.getRentComments().add(newRentComment);
        rent.getRentComments().add(newRentComment);
    }

    public void deleteComment(Account account,Rent rent, RentComment deleteComment) {

        rentCommentRepository.delete(deleteComment);
        account.getRentComments().remove(deleteComment);
        rent.getRentComments().remove(deleteComment);

    }
}
