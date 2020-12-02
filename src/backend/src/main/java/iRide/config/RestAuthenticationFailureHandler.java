package iRide.config;


import iRide.utils.exception.AccountBlocked;
import iRide.utils.exception.EmailConfirmationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationFailureHandler
        extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {
        String errorCode;
        if (exception.getClass() == BadCredentialsException.class){
            errorCode = "101";
        }
        else if (exception.getClass() == AccountBlocked.class){
            errorCode = "102";
        }
        else if (exception.getClass() == EmailConfirmationException.class){
            errorCode = "103";
        }
        else{
            errorCode = "104";
        }
        response.setHeader("Error-Code", "101");
        response.addHeader("Error-Code", "101");
        getRedirectStrategy().sendRedirect(request, response, "/login");
    }
}
