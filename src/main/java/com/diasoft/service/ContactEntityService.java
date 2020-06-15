package com.diasoft.service;

import com.diasoft.entity.ContactEntity;
import java.util.List;

public interface ContactEntityService {

  ContactEntity saveEntity(ContactEntity entity);

  ContactEntity getEntityById(long id);

  List<ContactEntity> getAllEntities();

  boolean updateEntity(ContactEntity entity);

  boolean deleteEntityById(long id);
}
