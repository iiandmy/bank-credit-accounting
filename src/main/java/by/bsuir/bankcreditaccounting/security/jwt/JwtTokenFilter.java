package by.bsuir.bankcreditaccounting.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {

        if (!((HttpServletResponse) res).containsHeader("Access-Control-Allow-Origin"))
            ((HttpServletResponse) res).addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        if (((HttpServletRequest) req).getMethod().equals("OPTIONS")) {
            System.out.println("OPTIONS");
            ((HttpServletResponse) res).addHeader("Access-Control-Allow-Methods", "*");
            ((HttpServletResponse) res).addHeader("Access-Control-Allow-Headers", "*");
            ((HttpServletResponse) res).addHeader("Access-Control-Allow-Credentials", "true");
            ((HttpServletResponse) res).setStatus(200);
        }

        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);

            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(req, res);
    }

}