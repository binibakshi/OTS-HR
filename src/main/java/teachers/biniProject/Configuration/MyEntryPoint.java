package teachers.biniProject.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyEntryPoint implements AuthenticationEntryPoint {
    private final HttpStatus httpStatus;
    private final Object responseBody;

    public MyEntryPoint(HttpStatus httpStatus, Object responseBody) {
        Assert.notNull(httpStatus, "httpStatus cannot be null");
        Assert.notNull(responseBody, "responseBody cannot be null");
        this.httpStatus = httpStatus;
        this.responseBody = responseBody;
    }

    @Override
    public final void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(httpStatus.value());

        try (PrintWriter writer = response.getWriter()) {
            writer.print(new ObjectMapper().writeValueAsString(responseBody));
        }
    }
}


