package com.community.controller;

import com.community.Service.UserService;
import com.community.dto.AccessTokenDTO;
import com.community.dto.GithubUserDTO;
import com.community.mapper.UserMapper;
import com.community.model.User;
import com.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse httpResponse,
                           HttpSession session
    ) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUserDTO userDTO = githubProvider.getUser(accessToken);
        System.out.println(userDTO.getName());

        if (userDTO != null && userDTO.getId() != null) {

            User user = new User();
            user.setAccount_id(String.valueOf(userDTO.getId()));
            user.setName(userDTO.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAvatar_url(userDTO.getAvatarUrl());
            userService.createrOrUpdate(user);

            httpResponse.addCookie(new Cookie("token", token));
            //session.setAttribute("user",user);
            return "redirect:/";
        } else {
            return "index";
        }

    }
    @GetMapping("/logout")
    public String logout(HttpServletResponse httpResponse,
                         HttpSession session){
        session.removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        httpResponse.addCookie(cookie);
        return "redirect:/";
    }
}
