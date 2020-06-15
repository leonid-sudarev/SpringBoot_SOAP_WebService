package com.diasoft.repository;

import com.diasoft.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;

public interface PersonEntityRepository extends CrudRepository<PersonEntity, Long> {}
