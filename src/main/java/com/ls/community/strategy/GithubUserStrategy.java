package com.ls.community.strategy;

import com.ls.community.dto.AccessTokenDTO;
import com.ls.community.provider.dto.GithubUser;
import com.ls.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GithubUserStrategy implements UserStrategy{

    @Autowired
    private GithubProvider githubProvider;

    @Override
    public LoginUserInfo getUser(String code, String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setAvatarUrl(githubUser.getAvatar_url());
        loginUserInfo.setBio(githubUser.getBio());
        loginUserInfo.setId(githubUser.getId());
        loginUserInfo.setName(githubUser.getName());
        return loginUserInfo;
    }

    @Override
    public String getSupportedType() {
        return "github";
    }
}
