package com.sistemi.informativi.exception;

import java.util.Date;

/*
 * Costruzione di un Oggetto che venga successivamente convertito dal JOM in un JSON contenente come proprietà:
 * statusCode dell'errore
 * data in cui si verifica l'errore
 * messaggio customdell'errore
 * descrizione errore
 * */

public class ErrorMessage {
	
	private int statusCode;

    private Date date;

    private String message;

    private String Description;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    protected ErrorMessage() {

    }

    public ErrorMessage(int statusCode, Date date, String message, String description) {

        this.statusCode = statusCode;
        this.date = date;
        this.message = message;
        Description = description;
    }
}