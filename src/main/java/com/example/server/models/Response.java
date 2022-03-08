package com.example.server.models;

import java.time.LocalDate;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
// Inclue suelement les valeurs qui ne sont pas null
@JsonInclude(NON_NULL)
public class Response {

    protected LocalDate timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    // Si la requete est un succes alors reason = null
    protected String reason;
    protected String message;
    // Si la requete est un succes alors devMessage = null
    protected String developperMessage;
    protected Map<?, ?> data;
    
}
