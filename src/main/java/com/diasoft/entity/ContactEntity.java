package com.diasoft.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "contacts")
@SecondaryTable(name = "contact_type")
public class ContactEntity implements Serializable {

  @Id
  @Column(name = "contacts_id", updatable = false, nullable = false)
  private Long id;

  private String number;

  @Column(table = "contact_type")
  private String type;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private PersonEntity personEntity;

  public ContactEntity(String number, String type, PersonEntity personEntity) {
    this.number = number;
    this.type = type;
    this.personEntity = personEntity;
  }
}
