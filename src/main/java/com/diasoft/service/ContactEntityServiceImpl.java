package com.diasoft.service;

import com.diasoft.entity.ContactEntity;
import com.diasoft.entity.PersonEntity;
import com.diasoft.repository.ContactEntityRepository;
import com.diasoft.repository.PersonEntityRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class ContactEntityServiceImpl implements ContactEntityService {

  // @RequiredArgsConstructor генерирует конструктор вместо @Autowired
  private final PersonEntityRepository personEntityRepository;
  private final ContactEntityRepository contactEntityRepository;

  /**
   * // т.к PersonEntity FetchType.LAZY то получаю ошибку //
   * org.hibernate.LazyInitializationException: could not initialize proxy //
   * [com.diasoft.entity.PersonEntity#1002] - no Session // вариант 1 как в коде
   *
   * <p>вариант2 FetchType.EAGER и не нужны запросы к personEntityRepository // его вообще тогда
   * можно удалить
   *
   * <p>Вариант 3 application.properties ->
   * spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true smell)
   */
  @Override
  @Transactional
  public ContactEntity getEntityById(long id) {
    ContactEntity contactEntity = contactEntityRepository.findById(id).orElse(null);
    if (contactEntity == null) {
      return null;
    }
    contactEntity.setPersonEntity(personEntityRepository.findById(id).orElse(new PersonEntity()));
    return contactEntity;
  }

  @Override
  public List<ContactEntity> getAllEntities() {
    // Аналогично
    List<ContactEntity> contactEntities = new ArrayList<>();
    List<PersonEntity> personEntities = new ArrayList<>();

    personEntityRepository.findAll().forEach(personEntities::add);
    contactEntityRepository.findAll().forEach(contactEntities::add);

    if (contactEntities.isEmpty()) {
      return Collections.emptyList();
    } else {
      for (int i = 0; i < contactEntities.size(); i++) {
        contactEntities.get(i).setPersonEntity(personEntities.get(i));
      }
      return contactEntities;
    }
  }

  @Override
  public ContactEntity saveEntity(ContactEntity entity) {
    try {
      return contactEntityRepository.save(entity);
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }
  }

  @Override
  public boolean updateEntity(ContactEntity entity) {
    try {
      contactEntityRepository.save(entity);
      return true;
    } catch (Exception e) {
      log.error(e.getMessage());
      return false;
    }
  }

  @Override
  public boolean deleteEntityById(long id) {
    if (contactEntityRepository.existsById(id)) {
      try {
        contactEntityRepository.deleteById(id);
        return true;
      } catch (Exception e) {
        log.error(e.getMessage());
        return false;
      }
    } else {
      return false;
    }
  }
}
