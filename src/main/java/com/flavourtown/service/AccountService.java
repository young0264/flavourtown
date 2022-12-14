package com.flavourtown.service;

import com.flavourtown.domain.account.Account;
import com.flavourtown.domain.account.AccountRepository;
import com.flavourtown.domain.account.AccountRole;
import com.flavourtown.domain.account.LoginType;
import com.flavourtown.domain.member.Member;
import com.flavourtown.domain.member.MemberRepository;
import com.flavourtown.infra.security.SecurityUser;
import com.flavourtown.web.dto.account.AccountSignUpDto;
import com.flavourtown.web.dto.member.MemberInfoDto;
import com.flavourtown.web.vo.MemberVo;
import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 새로운 Account 객체를 생성하여 DB에 저장하는 메소드
     * @param accountSignUpDto 회원가입 폼에서 받아온 DTO가 들어옴
     * @returns 들어온 DTO를 바탕으로 새로운 Account 객체를 생성하여 반환
     */
    public Account saveNewAccount(AccountSignUpDto accountSignUpDto, LoginType type) {
        // 회원가입한 Member를 저장하는 로직
        Account newAccount = Account.builder()
                .username(accountSignUpDto.getUsername())
                .password(passwordEncoder.encode(accountSignUpDto.getPassword()))
                .email(accountSignUpDto.getEmail())
                .accountRole(AccountRole.ROLE_USER)
                .loginType(type)
                .build();
        MemberInfoDto emptyDto = memberService.createEmptyMemberInfoDto();
        memberService.createNewMember(newAccount, emptyDto);
        return accountRepository.save(newAccount);
    }

    /**
     * Kakao 로그인으로 들어온 계정을 회원가입 혹은 로그인 시켜줌
     * @param token social 로그인을 통해 받아온 token이 들어옴
     * @returns 새로운 Account를 반환해줌
     */
    public Account createKakaoUser(String token) {

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();

            log.info("responseCode = {}", responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            log.info("response body = {}", result);

            //Gson 라이브러리로 JSON파싱
            JsonElement element = JsonParser.parseString(result);

            JsonObject asJsonObject = element.getAsJsonObject();
            log.info("kakao object : " + asJsonObject);

            Long kakao_id = element.getAsJsonObject().get("id").getAsLong();
            log.info("kakao id : " + kakao_id);

            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
            log.info("has_email={}", hasEmail);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();
            log.info("test3");

            int idx = 5;
            if(hasEmail){
                log.info("success login");
                idx = email.indexOf("@");
                log.info("email : " + email);
                AccountSignUpDto newDto = new AccountSignUpDto();
                newDto.setUsername(nickname);
                newDto.setEmail(email);
                newDto.setPassword(String.valueOf(UUID.randomUUID()));
                br.close();

                if (!existMemberCheck(newDto)) {
                    log.info("fail login 1");
                    return saveNewAccount(newDto, LoginType.KAKAO);
                } else {
                    log.info("fail login 2");
                    return findAccountByUsername(nickname);
                }
            }
        } catch (IOException e) {
            log.info("fail login 3");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Account 객체를 받아 로그인을 시켜주는 메소드
     * @param account 회원가입 시 생긴 Account 객체가 들어옴
     *  들어온 Account 객체를 Security를 이용하여 로그인 시켜줌
     */
    public void login(Account account) {
        // Security를 이용하여 member를 로그인 시켜줌(회원가입 시에만 사용)
        System.out.println("token : " );
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new SecurityUser(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }


    /**
     * username을 통해 Account 객체를 찾는 메소드
     */
    public Account findAccountByUsername(String username){
        Optional<Account> currentMember = accountRepository.findByUsername(username);
        return currentMember.orElse(null);
    }


    /**
     * user ID 중복 확인
     */
    public boolean checkDuplicatedAccount(String username) {
        return accountRepository.existsFindByUsername(username);
    }

    /**
     * 회원가입한 정보를 받아서 username 혹은 email이 중복으로 존재하는지 확인하는 메소드
     * @param accountSignUpDto 회원가입 시 폼을 받아 전달하는 DTO
     * @returns username 혹은 email 둘 중에 하나라도 존재하는지 반환
     */
    public boolean existMemberCheck(AccountSignUpDto accountSignUpDto) {
        // 이미 존재하는 username 혹은 email 인지 확인하는 폼
        boolean existUsername = accountRepository.existsFindByUsername(accountSignUpDto.getUsername());
        boolean existEmail = accountRepository.existsByEmail(accountSignUpDto.getEmail());
        return existUsername || existEmail;
    }

    /**
     * username을 받아 MemberVO 객체로 반환하는 메소드
     * @param username 로그인 id가 들어옴
     * @returns Member가 아닌 VO 객체를 반환함
     */
    public MemberVo getReadOnlyMember(String username) {
        Member currentMember = memberService.findMemberByUsername(username);
        return MemberVo.builder()
                .nickname(currentMember.getNickname())
                .introduce(currentMember.getIntroduce())
                .birth(currentMember.getBirth())
                .gender(currentMember.getGender())
                .postList(currentMember.getPostList())
                .replyList(currentMember.getReplyList())
                .favoriteList(currentMember.getFavoriteList())
                .build();
    }

    public boolean checkAccountPassword(String password, Account currentUser) {
        return passwordEncoder.matches(password, currentUser.getPassword());
    }

    public void changeAccountPassword(String password, Account currentUser) {
        currentUser.setPassword(passwordEncoder.encode(password));
        accountRepository.save(currentUser);
    }

    /**
     * Spring Security에서만 사용하는 메소드
     * @param username 로그인 id가 들어옴
     * @returns 새로운 SecurityUser를 생성해줌
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 로그인을 하기 위해 가입된 member 정보를 조회
        // UserDetailsService 구현

        Optional<Account> account = accountRepository.findByUsername(username);

        if (account.isEmpty()) {
            throw new UsernameNotFoundException(username);
        } else {
            return new SecurityUser(account.get());
        }
    }
}
