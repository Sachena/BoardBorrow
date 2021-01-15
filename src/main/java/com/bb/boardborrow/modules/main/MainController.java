package com.bb.boardborrow.modules.main;

import com.bb.boardborrow.modules.account.Account;
import com.bb.boardborrow.modules.account.CurrentAccount;
import com.bb.boardborrow.modules.rent.RentRepository;
import com.bb.boardborrow.modules.request.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {


    private final RentRepository rentRepository;
    private final RequestRepository requestRepository;

    @GetMapping("/")
    public String rent(@CurrentAccount Account account, Model model, @PageableDefault(size = 7,page = 0) Pageable pageable){
        if(account != null){
            model.addAttribute(account);
        }

        model.addAttribute("rentPage", rentRepository.findAll(pageable));
        model.addAttribute("requestPage",requestRepository.findAll(pageable));

        return "index";
    }




    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
