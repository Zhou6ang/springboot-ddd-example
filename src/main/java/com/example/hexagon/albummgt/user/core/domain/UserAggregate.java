package com.example.hexagon.albummgt.user.core.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAggregate {

  private String id;
  private String name;
  private String gender;
  private String email;
  private String phone;
  private Timestamp updateDate;

  private final List<String> preferences = new ArrayList<>();
  private final List<WishItem> wishlists = new ArrayList<>();
  private Address address;

  public void addPreference(List<String> preference) {
    this.preferences.addAll(preference);
  }

  public void addWishlists(List<WishItem> wishItem) {
    this.wishlists.addAll(wishItem);
  }

  public void removeWishlists(List<Long> list) {
    for (int i = 0; i < this.wishlists.size(); i++) {
      WishItem item = this.wishlists.get(i);
      if (list.contains(item.getId())) {
        this.wishlists.remove(item);
      }
    }
  }

  @Builder
  @Data
  public static class Address {
    private Long id;

    @Schema(example = "No.1 RenMin Road", minLength = 1, maxLength = 50)
    private String street;

    @Schema(example = "NewYork", minLength = 1, maxLength = 50)
    private String city;

    @Schema(example = "USA", minLength = 1, maxLength = 50)
    private String county;
  }
}
