package by.innowise.internship.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorObject {

    private Integer statusCode;

    private String message;

    private String timestamp;
}
