package es.heroesfactory.controllers;

import es.heroesfactory.dtos.ErrorDTO;
import es.heroesfactory.exceptions.HeroeIdNotFoundException;
import es.heroesfactory.exceptions.PageDataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandleController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PageDataNotFoundException.class, HeroeIdNotFoundException.class})
    public ResponseEntity<ErrorDTO> springHandlePageNotFound(Exception ex, WebRequest request)  {
        return new ResponseEntity<>(this.buildError(ex, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler( BadCredentialsException.class )
    public ResponseEntity<ErrorDTO> springHandleBadCredentials(Exception ex, WebRequest request)  {
        return new ResponseEntity<>(this.buildError(ex, HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler( {RuntimeException.class, Exception.class })
    public ResponseEntity<ErrorDTO> springHandleRumtimes(Exception ex, WebRequest request)  {
        return new ResponseEntity<>(this.buildError(ex, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDTO buildError(Exception ex, HttpStatus status) {
        return ErrorDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(ex.getMessage()).build();
    }
}
