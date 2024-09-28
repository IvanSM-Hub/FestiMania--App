package com.festimania.exceptions;

import com.festimania.entities.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de validación de los atributos de los objetos.
     * @param request Petición HTTP.
     * @param exception Excepción lanzada.
     * @return ResponseEntity con el error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerGenericException(HttpServletRequest request, Exception exception) {
        ApiError apiError = ApiError.builder()
                .backendMessage(exception.getLocalizedMessage())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .message("Error interno en el servidor, vuelva a intentarlo")
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    /**
     * Maneja las excepciones de validación de los atributos de los objetos.
     * @param request Petición HTTP.
     * @param exception Excepción lanzada.
     * @return ResponseEntity con el error.
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(HttpServletRequest request, ObjectNotFoundException exception) {
        ApiError apiError = ApiError.builder()
                .backendMessage(exception.getLocalizedMessage())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .message("Objeto no encontrado")
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    /**
     * Maneja las excepciones de validación de los atributos de los objetos.
     * @param request Petición HTTP.
     * @param exception Excepción lanzada.
     * @return ResponseEntity con el error.
     */
    @ExceptionHandler(AttributeException.class)
    public ResponseEntity<?> handleAttributeException(HttpServletRequest request, AttributeException exception) {
        ApiError apiError = ApiError.builder()
                .backendMessage(exception.getLocalizedMessage())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .message("Atributo no permitido")
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    /**
     * Maneja las excepciones de validación de los atributos de los objetos.
     * @param request Petición HTTP.
     * @param exception Excepción lanzada.
     * @return ResponseEntity con el error.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleBadRequest(HttpServletRequest request,
                                              HttpRequestMethodNotSupportedException exception) {
        ApiError apiError = ApiError.builder()
                .backendMessage(exception.getLocalizedMessage())
                .url(request.getRequestURL().toString())
                .method(request.getMethod())
                .message("Método no permitido para esta URL")
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(apiError);
    }

}
