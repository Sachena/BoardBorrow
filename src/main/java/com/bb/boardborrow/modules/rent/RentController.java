package com.bb.boardborrow.modules.rent;

import com.bb.boardborrow.modules.account.Account;
import com.bb.boardborrow.modules.account.CurrentAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RentController {

    private  final RentRepository rentRepository;

    @GetMapping("/rent")
    public String rent(@CurrentAccount Account account, Model model, @PageableDefault(size = 7,page = 0) Pageable pageable){
        if(account != null){
            model.addAttribute(account);
        }

        model.addAttribute("rentPage", rentRepository.findAll(pageable));

        return "rent/list";
    }

}
