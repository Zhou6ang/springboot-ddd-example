package com.example.hexagon.albummgt.user.driven.persistent;

import com.example.hexagon.albummgt.user.core.domain.UserAggregate.Address;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Data
@Entity(name = "addresses")
@Builder
@AllArgsConstructor
public class AddressEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String street;
  private String city;
  private String county;

  @Column(name = "created_date", insertable = false, updatable = false)
  private Timestamp createdDate;

  @Column(name = "updated_date", insertable = false, updatable = false)
  private Timestamp updatedDate;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private UserEntity user;

  public AddressEntity() {}

  public static AddressEntity toAddressEntity(Address address, UserEntity user) {
    return AddressEntity.builder()
        .id(address.getId())
        .street(address.getStreet())
        .city(address.getCity())
        .county(address.getCounty())
        .user(user)
        .build();
  }

  public static Address toAddress(AddressEntity address) {
    return Address.builder()
        .id(address.getId())
        .street(address.getStreet())
        .city(address.getCity())
        .county(address.getCounty())
        .build();
  }
}
