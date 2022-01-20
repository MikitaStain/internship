package by.innowise.internship.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

public class MyErrorHandler implements ResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException, ResponseStatusException {

        try {
            if (response.getStatusCode()
                    .series() == SERVER_ERROR) {

                throw new ResponseStatusException(HttpStatus.valueOf(response.getStatusText()));


            } else if (response.getStatusCode()
                    .series() == CLIENT_ERROR) {
            }


        } catch (HttpClientErrorException e) {

            System.out.println(e.getMessage());
        }

    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (
                response.getStatusCode().series() == CLIENT_ERROR
                        || response.getStatusCode().series() == SERVER_ERROR);
    }
}




