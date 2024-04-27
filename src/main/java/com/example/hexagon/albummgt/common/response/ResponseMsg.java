package com.example.hexagon.albummgt.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMsg {

  private int code;
  private String msg;
  private Object data;

  public static ResponseMsg success(String msg) {
    return success(0, msg, null);
  }

  public static ResponseMsg success(String msg, Object data) {
    return success(0, msg, data);
  }

  private static ResponseMsg success(int code, String msg, Object data) {
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
