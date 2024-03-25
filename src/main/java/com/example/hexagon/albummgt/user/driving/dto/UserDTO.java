package com.example.hexagon.albummgt.user.driving.dto;

import com.example.hexagon.albummgt.user.core.domain.WishItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class UserDTO {
    private String id;
    private String name;
    private String gender;
    private String email;
    private String phone;
    private String[] preferences;
    private List<WishItem> wishlists;
    @JsonProperty("update_date")
    private Timestamp updateDate;

}
