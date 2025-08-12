package by.AntonDemchuk.blog.config;

import by.AntonDemchuk.blog.dto.ErrorDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;

@RestControllerAdvice
public class HandlerAdviceController {

    @ApiResponse(responseCode = "400", description = "Validation failure")
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorDto> handleValidationErrors(HandlerMethodValidationException ex) {
        List<String> errors = ex.getAllErrors().stream().map(MessageSourceResolvable::getDefaultMessage).toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDto.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .errors(errors)
                        .build());
    }

    @ApiResponse(responseCode = "401", description = "User is not Authorized")
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDto> handleUnauthorizedErrors(AuthenticationException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorDto.builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .build());
    }

    @ApiResponse(responseCode = "403", description = "Access Denied")
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDeniedErrors(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorDto.builder()
                        .code(HttpStatus.FORBIDDEN.value())
                        .message(HttpStatus.FORBIDDEN.getReasonPhrase())
                        .build());
    }

    @ApiResponse(responseCode = "404", description = "Requested Entity not found")
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDto.builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(ex.getMessage())
                        .build());
    }
}
