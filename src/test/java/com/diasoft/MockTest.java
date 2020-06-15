package com.diasoft;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.diasoft.entity.ContactEntity;
import com.diasoft.entity.PersonEntity;
import com.diasoft.repository.ContactEntityRepository;
import com.diasoft.repository.PersonEntityRepository;
import com.diasoft.service.ContactEntityServiceImpl;
import java.util.Random;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MockTest {
  Random random;
  @Mock private ContactEntityRepository contactEntityRepository;
  @Mock private PersonEntityRepository personEntityRepository;

  private ContactEntityServiceImpl contactEntityService;

  @BeforeEach
  void initUseCase() {
    contactEntityService =
        new ContactEntityServiceImpl(personEntityRepository, contactEntityRepository);
  }

  // contactEntityService.saveEntity
  @Test
  public void shouldPersistContact() {
    ContactEntity contactEntity = getEntity();
    when(contactEntityService.saveEntity(any(ContactEntity.class))).then(returnsFirstArg());
    assertThat(contactEntity, equalTo(contactEntityService.saveEntity(contactEntity)));
  }

  // contactEntityService.getEntityById
  @Test
  public void shouldGetContactFromDB() {
    ContactEntity contactEntity = getEntity();
    when(contactEntityService.saveEntity(any(ContactEntity.class))).then(returnsFirstArg());
    assertThat(contactEntityService.saveEntity(contactEntity), equalTo(contactEntity));
  }

  @Test
  public void findByIdShouldReturnNonNullValue() {
    ContactEntity contactEntity = getEntity();
    when(contactEntityRepository.save(any(ContactEntity.class))).then(returnsFirstArg());
    ContactEntity saved = contactEntityRepository.save(contactEntity);
    Long id = saved.getId();
    Assert.assertNotNull(contactEntityRepository.findById(id));
  }

  private ContactEntity getEntity() {
    random = new Random();
    PersonEntity personEntity =
        new PersonEntity("person_fn " + random.nextInt(), "person_ln", "person_mn", "employee");
    return new ContactEntity("777-888-999", "work", personEntity);
  }

  @Test
  public void savedUserHasNotNullNumber() {
    ContactEntity contactEntity = getEntity();
    when(contactEntityService.saveEntity(any(ContactEntity.class))).then(returnsFirstArg());
    assertThat(contactEntityService.saveEntity(contactEntity).getNumber(), not(""));
  }
}
