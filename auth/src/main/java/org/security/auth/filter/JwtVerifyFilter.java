package org.security.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.security.auth.config.RsaKeyProperties;
import org.security.auth.entity.User;
import org.security.common.common.JwtUtils;
import org.security.common.exception.Code;
import org.security.common.exception.ExceptionHandlerClass;
import org.security.common.exception.HttpResult;
import org.security.common.domain.Payload;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class JwtVerifyFilter extends BasicAuthenticationFilter {

    private RsaKeyProperties rsaKeyProperties;

    public JwtVerifyFilter(AuthenticationManager authenticationManager, RsaKeyProperties rsaKeyProperties) {
        super(authenticationManager);
        this.rsaKeyProperties = rsaKeyProperties;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("x-admin ")) {
            chain.doFilter(request, response);
            String contentType = "application/json;charset=utf-8";
            response.setContentType(contentType);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            //携带格式合法的token
        } else {
            String token = header.replace("x-admin ", "");
            Payload<User> payload = null;
            try {
                payload = JwtUtils.getInfoFromToken(token, rsaKeyProperties.getPublicKey(), User.class);
            } catch (Exception e) {
               /* response.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);
                PrintWriter out = response.getWriter();
                out.write(new ObjectMapper().writeValueAsString(HttpResult.failed(Code.privilege_grant_failed)));
                out.flush();
                out.close();*/
                throw new RuntimeException("token解析失败！");
            }
            User user = payload.getUserInfo();
            if (!Objects.isNull(user)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getRoles());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                chain.doFilter(request, response);
            }
        }
    }

    /**
     *  通过token获取用户信息
     * @param request
     * @return
     */
    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            //通过token解析出载荷信息
            Payload<User> payload = JwtUtils.getInfoFromToken(token.replace("x-admin ", ""),
                    rsaKeyProperties.getPublicKey(), User.class);
            User user = payload.getUserInfo();
            //不为null，返回
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, user.getRoles());
            }
            throw new ExceptionHandlerClass(Code.privilege_grant_failed);
        }
         throw new ExceptionHandlerClass(Code.NO_LOGIN) ;
    }
}

