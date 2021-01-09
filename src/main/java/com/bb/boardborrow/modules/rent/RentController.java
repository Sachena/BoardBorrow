package com.bb.boardborrow.modules.rent;

import com.bb.boardborrow.modules.account.Account;
import com.bb.boardborrow.modules.account.CurrentAccount;
import com.bb.boardborrow.modules.request.Request;
import com.bb.boardborrow.modules.request.RequestForm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.Errors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RentController {

    private  final RentRepository rentRepository;
    private final RentService rentService;
    private final ModelMapper modelMapper;

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

}
