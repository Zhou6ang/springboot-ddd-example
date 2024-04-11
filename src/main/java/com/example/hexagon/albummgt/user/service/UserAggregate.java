package com.example.hexagon.albummgt.user.service;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserAggregate {
    private String id;
    private String name;
    private String gender;
    private String email;
    private String phone;
    private Timestamp updateDate;

    private final List<String> preferences = new ArrayList<>();
    private final List<WishItem> wishlists = new ArrayList<>();

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
}
