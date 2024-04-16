package com.example.hexagon.albummgt.user.driving.dto;

import com.example.hexagon.albummgt.user.core.domain.WishItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@Schema(name = "User")
public class UserDTO {

  private String id;
  @Schema(maxLength = 40, minLength = 1, description = "user name", example = "Jack")
  private String name;
  @Schema(allowableValues = "MALE,FEMALE,OTHER", description = "user gender", example = "MALE")
  private String gender;
  @Schema(maxLength = 30, description = "user email", example = "jack@gmail.com")
  private String email;
  @Schema(maxLength = 30, description = "user phone number", example = "+010-12345678")
  private String phone;
  @Hidden
  private String[] preferences;
  @ArraySchema(minItems = 1)
  private List<WishItem> wishlists;
  @Schema(hidden = true)
  @JsonProperty("update_date")
  private Timestamp updateDate;

}
