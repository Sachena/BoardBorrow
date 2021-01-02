package com.bb.boardborrow.modules.request;


import com.bb.boardborrow.modules.account.Account;
import com.bb.boardborrow.modules.account.CurrentAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RequestController {

    private final RequestRepository requestRepository;

    @GetMapping("/request")
    public String request(@CurrentAccount Account account, Model model, @PageableDefault(size = 7,page = 0) Pageable pageable){
        if(account != null){
            model.addAttribute(account);
        }

        model.addAttribute("requestPage", requestRepository.findAll(pageable));

        return "request/list";
    }

    @GetMapping("/request/add")
    public String addRequest(){
        return "request/addRequest";
    }

}
