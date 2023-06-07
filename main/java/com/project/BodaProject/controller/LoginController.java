package com.project.BodaProject.controller;


import com.project.BodaProject.domain.User.UserEntity;
import com.project.BodaProject.dto.UserDto;
import com.project.BodaProject.repository.UserRepository;
import com.project.BodaProject.service.UserService;
import com.project.BodaProject.util.KakaoLoginApiUtil;
import com.project.BodaProject.util.KakaoProfileApiUtil;
import com.project.BodaProject.util.NaverLoginApiUtil;
import com.project.BodaProject.util.NaverProfileApiUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.SimpleTimeZone;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("Res_ko_KR_keys");
    private final static String NAVER_CLIENT_ID = resourceBundle.getString("naverClientId"); // naver ClientId
    private final static String KAKAO_CLIENT_ID = resourceBundle.getString("kakaoClientId"); // kakao ClientId
    private final static String REDIRECT_BASEURI = resourceBundle.getString("redirectBaseUri");

    //네이버 api
    private final NaverLoginApiUtil naverLoginApiUtil;
    private final NaverProfileApiUtil naverProfileApiUtil;

    //카카오 api
    private final KakaoLoginApiUtil kakaoLoginApiUtil;
    private final KakaoProfileApiUtil kakaoProfileApiUtil;

    private final UserService userService;
    private final UserRepository userRepository;

    // 메인 목록
    @GetMapping("/logout")
    public String logout(HttpSession session) { // redirect
        log.info("logout");
        session.invalidate();
        return "redirect:/login";
    }

    /* Login */
    @RequestMapping("/login")
    public String login(Model model, HttpSession session) throws UnsupportedEncodingException {

        log.info("login page");
        // [ 네이버 로그인
        // 네이버 콜백 URI 생성
        String naverCallBackUri = URLEncoder.encode(REDIRECT_BASEURI + "/oauth/naver", "utf-8");
        // state 랜덤생성
        SecureRandom naverRandom = new SecureRandom();
        String naverState = new BigInteger(130, naverRandom).toString();
        // 네이버 api url 생성
        String naverApiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
        naverApiURL += String.format("&client_id=%s&redirect_uri=%s&state=%s", NAVER_CLIENT_ID, naverCallBackUri, naverState);

        // 클라이언트 전달
        session.setAttribute("naverState", naverState);
        model.addAttribute("naverLoginUrl", naverApiURL);
        // ] 네이버 로그인

        // [ 카카오 로그인
        // 카카오 콜백 URI 생성
        String kakaoCallBackUri = URLEncoder.encode(REDIRECT_BASEURI + "/oauth/kakao", "utf-8");
        // state 랜덤생성
        SecureRandom kakaoRandom = new SecureRandom();
        String kakaoState = new BigInteger(130, kakaoRandom).toString();
        // 카카오 api url 생성
        String kakaoApiURL = "https://kauth.kakao.com/oauth/authorize?response_type=code";
        kakaoApiURL += String.format("&client_id=%s&redirect_uri=%s&state=%s", KAKAO_CLIENT_ID, kakaoCallBackUri, kakaoState);

        // 클라이언트 전달
        session.setAttribute("kakaoState", naverState);
        model.addAttribute("kakaoLoginUrl", kakaoApiURL);
        // 카카오 로그인

        return "login";
    }

    @RequestMapping("/oauth/naver")
    public String naverOauth(HttpSession session, HttpServletRequest request)
            throws UnsupportedEncodingException, ParseException {

        //네이버 토큰 발급
        Map<String, String> res = naverLoginApiUtil.getTokens(request);
        String access_token = res.get("access_token");
        String refresh_token = res.get("refresh_token");

        session.setAttribute("naverCurrentAT", access_token);
        session.setAttribute("naverCurrentRT", refresh_token);

        /* access token을 사용해 사용자 프로필 조회 api 호출 */
        Map<String, String> profile = naverProfileApiUtil.getProfile(access_token); // Map으로 사용자 데이터 받기
        log.info("profile : " + profile);

        //회원가입 조회
        String identifier = profile.get("id");
        String email = profile.get("email");
        String nickname = profile.get("nickname");

//        return apiUserLogin(email, pw, session);
        Optional<UserEntity> check = userRepository
                .findByEmailAndPassword(email, identifier);

//        UserEntity check2 = userRepository
//                .findByEmailAndPassword(email, identifier).orElseThrow();


        if(check.isEmpty()){
            //신규회원
            log.info("1111111111");
            UserEntity user = UserEntity.builder()
                    .password(identifier)
                    .email(email)
                    .name(nickname)
                    .createT(LocalDateTime.now())
                    .build();
            session.setAttribute("userInfo", user);
//            userService.register(user);
            userRepository.save(user);

        }else{

            session.setAttribute("userInfo", check.get());
        }



        return "redirect:/main";
    }

    @RequestMapping("/oauth/kakao")
    public String kakaoOauth(HttpSession session, HttpServletRequest request)
            throws UnsupportedEncodingException, ParseException {

        //카카오 토큰 발급
        Map<String, String> res = kakaoLoginApiUtil.getTokens(request);
        String access_token = res.get("access_token");
        String refresh_token = res.get("refresh_token");

        session.setAttribute("kakaoCurrentAT", access_token);
        session.setAttribute("kakaoCurrentRT", refresh_token);

        /* access token을 사용해 사용자 프로필 조회 api 호출 */
        Map<String, String> profile = kakaoProfileApiUtil.getProfile(access_token); // Map으로 사용자 데이터 받기
        log.info("profile : " + profile);

        //회원가입 조회
        String identifier = profile.get("id");
        String email = profile.get("email");
        String nickname = profile.get("nickname");


//        return apiUserLogin(email, identifier, session);
        Optional<UserEntity> check = userRepository
                .findByEmailAndPassword(email, identifier);

//        UserEntity check2 = userRepository
//                .findByEmailAndPassword(email, identifier).orElseThrow();

        if(check.isEmpty()){
            //신규회원
            log.info("1111111111");
            UserEntity user = UserEntity.builder()
                    .password(identifier)
                    .email(email)
                    .name(nickname)
                    .createT(LocalDateTime.now())
                    .build();
            session.setAttribute("userInfo", user);
//            userService.register(user);
            userRepository.save(user);

        }else{
            //기존회원 업데이트
            session.setAttribute("userInfo", check.get());
        }

        return "redirect:/main";

    }

    //API 로그인시 닉네임 설정
    @GetMapping("setUserNameLogin")
    public String setUserName(
            @RequestParam String Name,
            @SessionAttribute(name = "userInfo", required = false) UserDto userDto) {

        userDto.setName(Name);
        userService.update(userDto);

        return "redirect:/main";
    }

    @GetMapping("/ezlogin")
    public String ezlogin() {
        return "/page/login/ezlogin";
    }

    /*
     * 회원가입 시작.
     * */

    @GetMapping("/join")
    public String register() {
        return "/join";
    }

    @ResponseBody
    @GetMapping("/userInfoCheck")
    public boolean userInfoCheck(@RequestParam String name, @RequestParam String value) {
        return userService.isExistsUserInfo(name, value);
    }

    @PostMapping("/save")
    public String save(UserDto userDto, HttpSession session) {

        session.setAttribute("userInfo", userDto);
        userService.register(userDto);

        return "redirect:/main";
    }

    @PostMapping("/loginPost")
    public String login(@ModelAttribute UserDto userDto, HttpSession session){
        UserDto loginResult = userService.login(userDto);
        if(loginResult != null){
            session.setAttribute("userInfo", loginResult);
            return "redirect:/main";
        }else{
            return "login";
        }
    }

    //카카오, 네이버 API로 로그인 하는 경우
    public String apiUserLogin(String email, String pw, HttpSession session) {

        boolean isExistUser = userRepository.findByEmailAndPassword(email, pw).isPresent();

        //신규 회원 등록
        if (isExistUser == false) {

            var user = UserEntity.builder()
                    .email(email)
                    .password(pw)
                    .build();
            userRepository.save(user);


//            var user = User.builder()
//                    .email(email)
//                    .pw(pw)
//                    .build();
//            userRepository.save(user);
        }

        var userEntity = userRepository.findByEmailAndPassword(email, pw).get();
        session.setAttribute("userInfo", userService.entityToDto(userEntity));

        //기존 회원 닉네임 유무 판별
        if (userEntity.getName() == null) {
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}