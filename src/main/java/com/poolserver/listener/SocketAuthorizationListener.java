/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.poolserver.listener;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import io.netty.handler.codec.http.HttpHeaders;

import java.security.Key;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author messenger
 */
public class SocketAuthorizationListener implements AuthorizationListener {

    @Override
    public boolean isAuthorized(HandshakeData hd) {
        Map<String, List<String>> params = hd.getUrlParams();
        if (params.containsKey("accessToken")) {
            List<String> accessTokenParams = params.get("accessToken");
            if (accessTokenParams.isEmpty() == false) {
                String accessToken = accessTokenParams.get(0);
                accessToken = accessToken.substring(0, accessToken.lastIndexOf('.') + 1);
                // token validate implements
                return true;
            }
        }
        return false;
    }
}
