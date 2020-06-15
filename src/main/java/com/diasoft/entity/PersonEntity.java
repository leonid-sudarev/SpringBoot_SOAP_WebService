package com.diasoft.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PersonEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  private String firstName;
  private String lastName;
  private String middleName;
  private String position;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "person_id")
  Long id;

  public PersonEntity(String firstName, String lastName, String middleName, String position) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.middleName = middleName;
    this.position = position;
  }
}
