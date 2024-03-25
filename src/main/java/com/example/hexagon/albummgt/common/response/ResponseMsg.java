package com.example.hexagon.albummgt.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMsg {
    private int code;
    private String msg;
    private Object data;

    public static ResponseMsg succ(String msg) {
        return succ(0, msg, null);
    }

    public static ResponseMsg succ(String msg, Object data) {
        return succ(0, msg, data);
    }

    private static ResponseMsg succ(int code, String msg, Object data) {
        return ResponseMsg.builder().code(code).msg(msg).data(data).build();
    }

    public static ResponseMsg fail(String msg) {
        return fail(1, msg, null);
    }

    public static ResponseMsg fail(String msg, Object data) {
        return fail(1, msg, data);
    }

    public static ResponseMsg fail(int code, String msg, Object data) {
        return ResponseMsg.builder().code(code).msg(msg).data(data).build();
    }
}
