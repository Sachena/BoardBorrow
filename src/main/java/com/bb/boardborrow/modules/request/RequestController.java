package com.bb.boardborrow.modules.request;


import com.bb.boardborrow.modules.account.Account;
import com.bb.boardborrow.modules.account.CurrentAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.Errors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RequestController {

    private final RequestRepository requestRepository;
    private final RequestService requestService;
    private final ModelMapper modelMapper;

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
    public String viewRequest(@CurrentAccount Account account, @PathVariable Long requestId, Model model) {
        Request request = requestRepository.findById(requestId).get();
        model.addAttribute(account);
        model.addAttribute(request);

        return "request/view";
    }

}
