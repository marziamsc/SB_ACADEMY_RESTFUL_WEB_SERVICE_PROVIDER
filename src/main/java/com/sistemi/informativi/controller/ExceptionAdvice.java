package com.sistemi.informativi.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.sistemi.informativi.exception.ErrorMessage;

/*
 * Grazie all'annotation @RestControllerAdvice questa Classe a runtime sta in ascolto su eventuali eccezioni che
 * si verificano nel RestController e possiamo chiedere che, al verificarsi di una o più eccezioni, venga
 * restituito un JSON custom
 */
@RestControllerAdvice
public class ExceptionAdvice {
	
	/*
	 * Tramite l'annotation @ExceptionHandler chiediamo che, al verificarsi dell'eccezione MethodArgumentNotValidException,
	 * venga eseguito il metodo notValidExceptionHandler
	 * Nel caso specifico, il metodo si occupa di restituire un Oggetto ErroreMessage che verrà convertito in
	 * un JSON con messaggio custom
	 */

	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ErrorMessage notValidExceptionHandler(Exception ex, WebRequest request) {

        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(),
                "code must of be of 4 characters", request.getDescription(false));

        return message;

    }
}
