package com.bb.boardborrow.modules.request;


import com.bb.boardborrow.modules.account.Account;
import com.bb.boardborrow.modules.account.CurrentAccount;
import com.bb.boardborrow.modules.comment.CommentForm;
import com.bb.boardborrow.modules.rent.Rent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.Errors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RequestController {

    private final RequestRepository requestRepository;
    private final RequestService requestService;
    private final ModelMapper modelMapper;
    private final RequestCommentRepository requestCommentRepository;

    @GetMapping("/request")
    public String request(@CurrentAccount Account account, Model model, @PageableDefault(size = 7,page = 0) Pageable pageable){
        if(account != null){
            model.addAttribute(account);
        }

        model.addAttribute("requestPage", requestRepository.findAll(pageable));

        return "request/list";
    }

    @GetMapping("/request/new-request")
    public String newRequestForm(@CurrentAccount Account account, Model model)
    {

        model.addAttribute(account);
        model.addAttribute(new RequestForm());
        return "request/addRequest";
    }

    @PostMapping("/request/new-request")
    public String newRequestSubmit(@CurrentAccount Account account, Model model, @Valid RequestForm requestForm, Errors errors){

        if(errors.hasErrors()){
            model.addAttribute(account);
            return "request/addRequest";
        }

        requestService.createNewRequest(modelMapper.map(requestForm, Request.class), account);

        return "redirect:/request";

    }

    @GetMapping("/request/{requestId}")
    public String viewRequest(@CurrentAccount Account account, @PathVariable Long requestId, Model model,
                              @PageableDefault(size = 7,page = 0,sort = "post", direction = Sort.Direction.DESC) Pageable pageable) {
        Request request = requestRepository.findById(requestId).get();
        model.addAttribute(account);
        model.addAttribute(request);
        model.addAttribute("isWriter", account.equals(request.getAuthor()));
        model.addAttribute("commentPage", requestCommentRepository.findAll(pageable,requestId));

        return "request/view";
    }


    @GetMapping("/request/{requestId}/update")
    public String updateRequestForm(@CurrentAccount Account account, Model model, @PathVariable Long requestId){

        Request updateRequest = requestRepository.findById(requestId).get();

        model.addAttribute(account);
        model.addAttribute(updateRequest);
        RequestForm updateForm = new RequestForm();
        updateForm.setTitle(updateRequest.getTitle());
        updateForm.setDescription(updateRequest.getDescription());
        updateForm.setStart(updateRequest.getStart());
        updateForm.setEnd(updateRequest.getEnd());
        updateForm.setPhoto(updateRequest.getPhoto());
        model.addAttribute(updateForm);
        return "request/view-update";

    }

    @PostMapping("/request/{requestId}/update")
    public String updateRequestSubmit(@CurrentAccount Account account, Model model, @PathVariable Long requestId, @Valid RequestForm requestForm
            , Errors errors, RedirectAttributes attributes){

        Request requestToUpdate = requestService.getRequestToUpdate(account,requestId);

        if(errors.hasErrors()){
            model.addAttribute(account);
            model.addAttribute(requestToUpdate);
            return "request/view-update";
        }

        requestService.updateRequest(requestForm,requestToUpdate);
        attributes.addFlashAttribute("message", "스터디 소개를 수정했습니다.");
        return "redirect:/request/" + requestToUpdate.getId();

    }

    @PostMapping("/request/{requestId}/remove")

    public String removeRequest(@CurrentAccount Account account, @PathVariable Long requestId){

        Request removeRequest = requestRepository.findById(requestId).get();

        requestService.removeRequest(account, removeRequest);
        return "redirect:/request";

    }

    @PostMapping("/request/{requestId}")
    @ResponseBody
    public ResponseEntity addComment(@CurrentAccount Account account, @RequestBody CommentForm commentForm, @PathVariable Long requestId){
        Request commentRequest = requestRepository.findById(requestId).get();
        requestService.addComment(account,commentRequest,commentForm.getDescription());

        return ResponseEntity.ok().build();
    }


}
