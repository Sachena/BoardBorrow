package com.bb.boardborrow.modules.main;

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
public class MainController {




    @GetMapping("/")
    public String home(@CurrentAccount Account account, Model model, @PageableDefault(size = 7,page = 0,sort = "likeCount", direction = Sort.Direction.DESC) Pageable pageable){
        if(account != null){
            model.addAttribute(account);
        }


        return "index";

    }




    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
