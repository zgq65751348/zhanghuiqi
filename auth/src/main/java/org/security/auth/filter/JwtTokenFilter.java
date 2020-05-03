package org.security.auth.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.security.auth.config.RsaKeyProperties;
import org.security.auth.entity.Role;
import org.security.auth.entity.User;
import org.security.common.common.JwtUtils;
import org.security.common.exception.Code;
import org.security.common.exception.ExceptionHandlerClass;
import org.security.common.exception.HttpResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class JwtTokenFilter extends UsernamePasswordAuthenticationFilter {

    private  final  String ContentType = "application/json;charset=utf-8";

    private AuthenticationManager authenticationManager;

    private RsaKeyProperties rsaKeyProperties;

    public JwtTokenFilter(AuthenticationManager authenticationManager, RsaKeyProperties rsaKeyProperties) {
        this.authenticationManager = authenticationManager;
        this.rsaKeyProperties = rsaKeyProperties;
    }

    /**
     *  重写SpringSecurity获取用户名和密码操作
     * @param request
     * @param response
     * @return Authentication
     * @throws AuthenticationException
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
            return authenticationManager.authenticate(authRequest);
        } catch (IOException e) {
            throw new ExceptionHandlerClass(Code. SYS_ERROR);
        }
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return super.obtainPassword(request);
    }

    /**
     *  密码用户名匹配失败处理
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        response.setContentType(ContentType);
        if(failed.getMessage().equals("User is disabled")){
            out.write(new ObjectMapper().writeValueAsString( HttpResult.failed(Code.USER_IS_DISABLED)));
        }else{
            out.write(new ObjectMapper().writeValueAsString(HttpResult.failed(Code.PASSWORD_ERROR)));
        }
        System.out.println("AuthenticationException   ===>"+failed.getMessage());
        out.flush();
        out.close();
    }

    /**
     *  用户认证成功返回token
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = new User();
        user.setUsername(authResult.getName());
        user.setId(((User)authResult.getPrincipal()).getId());
        user.setRoles((List<Role>)authResult.getAuthorities());
        String token = JwtUtils.generateTokenExpireInSeconds(user,rsaKeyProperties.getPrivateKey(),24*3600*7);
        response.addHeader("Authorization","x-admin " +token);
        Map<String,String> map = new HashMap<>();
        map.put("Authorization","x-admin " +token);
        try {
            response.setContentType(ContentType);
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            out.write(new ObjectMapper().writeValueAsString(HttpResult.success(map)));
            out.flush();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
