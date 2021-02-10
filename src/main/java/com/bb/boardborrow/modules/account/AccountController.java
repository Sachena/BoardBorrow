package com.bb.boardborrow.modules.account;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        model.addAttribute("signUpForm", new SignUpForm());

        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid @ModelAttribute SignUpForm signUpForm, Errors errors){
        if(errors.hasErrors()){
            return "account/sign-up";
        }

        Account account = accountService.processNewAccount(signUpForm);
        accountService.login(account);

        return "redirect:/";
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model){

        String view = "account/checked-email";
        Account account = accountRepository.findByEmail(email);
        if(account == null){
            model.addAttribute("error", "wrong.email");
            return  view;
        }
        if(!account.getEmailCheckToken().equals(token)){
            model.addAttribute("error", "wrong.token");
            return view;
        }
        accountService.completeSignUp(account);
        model.addAttribute("numberOfUser", accountRepository.count());
        model.addAttribute("nickname",account.getNickname());
        return view;
    }

    @GetMapping("/check-email")
    public String checkEmail(@CurrentAccount Account account, Model model){
        model.addAttribute("email", account.getEmail());
        return "account/check-email";
    }

    @GetMapping("/resend-confirm-email")
    public String resendConfirmEmail(@CurrentAccount Account account, Model model){
        if(!account.canSendConfirmEmail()){
            model.addAttribute("error", "인증 이메일은 한시간에 한번만 전송 가능합니다.");
            model.addAttribute("email", account.getEmail());
            return "account/check-email";
        }
        accountService.sendSignUpConfirmEmail(account);
        return "redirect:/";
    }

    @GetMapping("/profile/{nickname}")
    public String viewProfile(@CurrentAccount Account account, Model model, @PathVariable String nickname){
        Account findAccount = accountRepository.findByNickname(nickname);
        if(nickname == null){
            throw new IllegalArgumentException(nickname + "에 해당 사용자가 없습니다.");
        }
        model.addAttribute("account",findAccount);
        model.addAttribute("isOwner", findAccount.equals(account));
        return "account/profile";
    }

}
