package com.bb.boardborrow.modules.rent;

import com.bb.boardborrow.modules.account.Account;
import com.bb.boardborrow.modules.account.CurrentAccount;
import com.bb.boardborrow.modules.comment.CommentForm;
import com.bb.boardborrow.modules.comment.DeleteForm;
import com.bb.boardborrow.modules.request.Request;
import com.bb.boardborrow.modules.request.RequestForm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.Errors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RentController {

    private  final RentRepository rentRepository;
    private final RentService rentService;
    private final ModelMapper modelMapper;
    private final RentCommentRepository rentCommentRepository;


    @GetMapping("/rent")
    public String rent(@CurrentAccount Account account, Model model, @PageableDefault(size = 7,page = 0) Pageable pageable){
        if(account != null){
            model.addAttribute(account);
        }

        model.addAttribute("rentPage", rentRepository.findAll(pageable));

        return "rent/list";
    }

    @GetMapping("/rent/new-rent")
    public String newRentForm(@CurrentAccount Account account, Model model)
    {

        model.addAttribute(account);
        model.addAttribute(new RentForm());
        return "rent/new-rent";
    }

    @PostMapping("/rent/new-rent")
    public String newRentSubmit(@CurrentAccount Account account, Model model, @Valid RentForm rentForm, Errors errors){

        if(errors.hasErrors()){
            model.addAttribute(account);
            return "rent/new-rent";
        }

        rentService.createNewRent(modelMapper.map(rentForm, Rent.class), account);

        return "redirect:/rent";

    }

    @GetMapping("/rent/{rentId}")
    public String viewRent(@CurrentAccount Account account, @PathVariable Long rentId, Model model,@PageableDefault(size = 7,page = 0,sort = "post", direction = Sort.Direction.DESC) Pageable pageable) {
        Rent rent = rentRepository.findById(rentId).get();
        model.addAttribute(account);
        model.addAttribute(rent);
        model.addAttribute("isWriter", account.equals(rent.getAuthor()));
        model.addAttribute("commentPage", rentCommentRepository.findAll(pageable,rentId));


        return "rent/view";
    }

    @GetMapping("/rent/{rentId}/update")
    public String updateRentForm(@CurrentAccount Account account, Model model, @PathVariable Long rentId){


        Rent updateRent = rentRepository.findById(rentId).get();

        model.addAttribute(account);
        model.addAttribute(updateRent);
        RentForm updateForm = new RentForm();
        updateForm.setTitle(updateRent.getTitle());
        updateForm.setDescription(updateRent.getDescription());
        updateForm.setPhoto(updateRent.getPhoto());
        model.addAttribute(updateForm);
        return "rent/view-update";

    }

    @PostMapping("/rent/{rentId}/update")
    public String updateRentSubmit(@CurrentAccount Account account, Model model, @PathVariable Long rentId, @Valid RentForm rentForm
            , Errors errors, RedirectAttributes attributes){


        Rent rentToUpdate = rentService.getRentToUpdate(account,rentId);
        if(errors.hasErrors()){
            model.addAttribute(account);
            model.addAttribute(rentToUpdate);
            return "rent/view-update";
        }

        rentService.updateRequest(rentForm,rentToUpdate);
        attributes.addFlashAttribute("message", "스터디 소개를 수정했습니다.");
        return "redirect:/rent/" + rentToUpdate.getId();

    }

    @PostMapping("/rent/{rentId}/remove")

    public String removeRent(@CurrentAccount Account account, @PathVariable Long rentId){


        Rent removeRent = rentRepository.findById(rentId).get();

        rentService.removeRent(account, removeRent);
        return "redirect:/rent";

    }

    @PostMapping("/rent/{rentId}")
    @ResponseBody
    public ResponseEntity addComment(@CurrentAccount Account account, @RequestBody CommentForm commentForm,@PathVariable Long rentId){
        Rent commentRent = rentRepository.findById(rentId).get();
        rentService.addComment(account,commentRent,commentForm.getDescription());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/rent/{rentId}")
    @ResponseBody
    public ResponseEntity deleteComment(@CurrentAccount Account account, @RequestBody DeleteForm deleteForm, @PathVariable Long rentId ){
        RentComment deleteComment = rentCommentRepository.findById(deleteForm.getDeleteId()).get();
        System.out.println(deleteComment.getId());
        Rent nowRent = rentRepository.findById(rentId).get();
        rentService.deleteComment(account,nowRent,deleteComment);

        return ResponseEntity.ok().build();
    }



}
