package com.example.hexagon.albummgt.common.exception;

import com.example.hexagon.albummgt.common.response.ResponseMsg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	private ObjectMapper mapper = new ObjectMapper();
	@ExceptionHandler
	public ResponseEntity<ResponseMsg> handleHttpRequestException(HttpServletRequest request, Exception ex) throws JsonProcessingException {

		if(ex instanceof ClientAbortException) {
			log.error("Exception:", ex.getMessage());
		}else {
			log.info(mapper.writeValueAsString(request.getParameterMap()));
			log.error("Exception:", ex);
		}
		return new ResponseEntity<ResponseMsg>(ResponseMsg.fail(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

}
