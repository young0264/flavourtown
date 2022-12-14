package com.flavourtown.web.controller.account;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AuthUser;
import com.flavourtown.domain.account.LoginType;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberAge;
import com.flavourtown.service.AccountService;
import com.flavourtown.service.MemberService;
import com.flavourtown.service.SocialLoginApiService;
import com.flavourtown.web.dto.account.AccountLoginDto;
import com.flavourtown.web.dto.account.AccountSignUpDto;
import com.flavourtown.web.dto.member.MemberInfoDto;
import com.flavourtown.web.dto.profile.ProfileIntroduceDto;
import com.flavourtown.web.dto.profile.ProfilePasswordDto;
import com.flavourtown.web.dto.profile.ProfileWithdrawalDto;
import com.flavourtown.web.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;
    private final MemberService memberService;
    private final SocialLoginApiService loginApiService;


    @GetMapping("/login")
    public String showLoginPage(Model model) {
        // 로그인할 때 사용할 Dto 전달
        model.addAttribute("accountLoginDto", new AccountLoginDto());
        return "account/account-login";
    }

    @GetMapping("/account/kakao")
    public String kakaoLogin(@RequestParam String code) {
        log.info("kakaoLoginCode={}", code);
        String access_Token = loginApiService.getKakaoAccessToken(code);
        log.info("access_Token={}", access_Token);
        Account currentAccount = accountService.createKakaoUser(access_Token);
        accountService.login(currentAccount);
        if (currentAccount.getMember().getIntroduce() == null) {
            return "redirect:/info-init";
        }
        return "redirect:/";
    }


    @GetMapping("/signup")
    public String showSignUpPage(Model model) {
        model.addAttribute("accountSignUpDto", new AccountSignUpDto());
        return "account/account-signup";
    }

    @PostMapping("/signup")
    public String createNewMember(@Valid AccountSignUpDto accountSignUpDto,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || accountService.existMemberCheck(accountSignUpDto)) {
            // DTO에 작성한 Valid에 맞지 않거나, 이미 존재하는 username 혹은 email일 경우
            // 해당 내용을 다시 dto에 담아서 회원가입 폼으로 돌려줌
            model.addAttribute("accountSignUpDto", accountSignUpDto);
            return "account/account-signup";
        }
        // 아무 이상 없다면 로그인을 진행하고, 유저 프로필 작성 폼으로 넘어간다.
        log.info("accountSignUpDto={}", accountSignUpDto);
        Account account = accountService.saveNewAccount(accountSignUpDto, LoginType.LOCAL);
        accountService.login(account);
        return "redirect:/info-init";
    }

    @ResponseBody
    @RequestMapping(value = "/account/idCheck")
    public boolean overlappedID(@RequestParam(value = "username") String username) {
        boolean flag = false;
        if (accountService.checkDuplicatedAccount(username)) {
            flag = true;
        }
        return flag;
    }

    @GetMapping("/info-init")
    public String memberInformationInit(Principal principal, Model model) {
        Account principalAccount = accountService.findAccountByUsername(principal.getName());
        if (principal == null) {
            return "redirect:/";
        }
        model.addAttribute("ages", MemberAge.values());
        model.addAttribute("memberInfoDto", new MemberInfoDto());
        return "account/member-info-init";
    }

    @PostMapping("/info-init")
    public String createNewMember(MemberInfoDto memberInfoDto,
                                  Principal principal) {
        Account account = accountService.findAccountByUsername(principal.getName());
        Member currentMember = account.getMember();
        memberService.updateCurrentMember(currentMember, memberInfoDto);
        return "redirect:/";
    }

    /**
     * 회원탈퇴
     */
    @PostMapping("/withdrawal")
    public String accountWithdrawal(Principal principal, ProfileWithdrawalDto dto, Model model) {
        Member member = memberService.findMemberByUsername(principal.getName());
        Account account = accountService.findAccountByUsername(principal.getName());

        if (principal==null || !accountService.checkAccountPassword(dto.getPassword(),account)) {
            model.addAttribute("settingMessageError", "비밀번호를 잘못 입력하셨습니다.");
            model.addAttribute("member", member);
            model.addAttribute("profileWithdrawalDto", dto);
            model.addAttribute("profilePasswordDto", new ProfilePasswordDto());
            model.addAttribute("profileIntroduceDto", new ProfileIntroduceDto());
            return "profile/profile-setting";
        }

        log.info("drawal1 : " + member.getNickname());
        log.info("drawal2 : " + account.getUsername());
        memberService.withdrawalMember(member.getId());
        return "redirect:/logout";
    }

}
