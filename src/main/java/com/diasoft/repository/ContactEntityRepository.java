package com.diasoft.repository;

import com.diasoft.entity.ContactEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactEntityRepository extends CrudRepository<ContactEntity, Long> {}
