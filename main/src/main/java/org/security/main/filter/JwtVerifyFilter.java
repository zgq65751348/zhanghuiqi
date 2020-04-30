package org.security.main.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.security.common.common.JwtUtils;
import org.security.common.domain.Payload;
import org.security.common.exception.Code;
import org.security.common.exception.HttpResult;
import org.security.main.config.RsaKeyProperties;
import org.security.auth.entity.User;
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

public class JwtVerifyFilter extends BasicAuthenticationFilter {

    private RsaKeyProperties jwtRsaKeyProperties;

    public JwtVerifyFilter(AuthenticationManager authenticationManager, RsaKeyProperties jwtRsaKeyProperties) {
        super(authenticationManager);
        this.jwtRsaKeyProperties = jwtRsaKeyProperties;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        // 未认证
        String contentType = "application/json;charset=utf-8";
        if (header == null || !header.startsWith("x-admin ")) {
            chain.doFilter(request, response);
            response.setContentType(contentType);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            //携带格式合法的token
        }else {
            String token = header.replace("x-admin ","");
            System.out.println("---->请求头中的参数为:"+token);
            Payload<User>  payload = JwtUtils.getInfoFromToken(token,jwtRsaKeyProperties.getPublicKey(), User.class);
            User user = payload.getUserInfo();
            if(null != user){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                chain.doFilter(request, response);
            }else{
                response.setContentType(contentType);
                PrintWriter out = response.getWriter();
                out.write(new ObjectMapper().writeValueAsString(HttpResult.failed(Code.USER_NOT_FOUND)));
                out.flush();
                out.close();
            }

        }
    }

    /**
     * 通过token，获取用户信息
     * @param request
     * @return
     */
    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            //通过token解析出载荷信息
            Payload<User> payload = JwtUtils.getInfoFromToken(token.replace("x-admin  ", ""),
                    jwtRsaKeyProperties.getPublicKey(), User.class);
            User user = payload.getUserInfo();
            //不为null，返回
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
