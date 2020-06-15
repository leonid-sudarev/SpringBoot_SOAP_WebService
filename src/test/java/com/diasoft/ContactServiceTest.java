package com.diasoft;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import com.diasoft.entity.ContactEntity;
import com.diasoft.repository.ContactEntityRepository;
import com.diasoft.service.ContactEntityService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:/test.properties")
public class ContactServiceTest {

  @Resource private ContactEntityRepository contactEntityRepository;
  @Resource private ContactEntityService contactEntityService;

  @Resource
  public void setContactEntityRepository(ContactEntityRepository contactEntityRepository) {
    this.contactEntityRepository = contactEntityRepository;
  }

  @Resource
  public void setContactEntityService(ContactEntityService contactEntityService) {
    this.contactEntityService = contactEntityService;
  }

  private ContactEntity getEntity() {
    return TestUtilsClass.getContactEntity();
  }

  // contactEntityService.saveEntity
  @Test
  public void shouldPersistContact() {
    ContactEntity contactEntity = getEntity();
    Assert.assertEquals(contactEntity, contactEntityService.saveEntity(contactEntity));
  }

  // contactEntityService.getEntityById
  @Test
  public void shouldGetContactFromDB() {
    ContactEntity contactEntity = getEntity();
    ContactEntity savedEntity = contactEntityService.saveEntity(contactEntity);
    Assert.assertEquals(contactEntity, contactEntityService.getEntityById(savedEntity.getId()));
  }

  // contactEntityService.saveEntity
  // contactEntityService.deleteEntityById
  @Test
  public void shouldRemoveContactFromDbById() {
    ContactEntity contactEntity = getEntity();
    ContactEntity savedEntity = contactEntityService.saveEntity(contactEntity);

    Assert.assertTrue(contactEntityService.deleteEntityById(savedEntity.getId()));
    Assert.assertFalse(contactEntityRepository.existsById(savedEntity.getId()));
  }

  // contactEntityService.getAllEntities()
  @Test
  public void shouldReturnListOfContactsFromDB() {
    contactEntityRepository.deleteAll();
    List<ContactEntity> contactEntityList = new ArrayList<>();

    for (int i = 0; i < 100; i++) {
      contactEntityList.add(getEntity());
    }

    for (ContactEntity contact : contactEntityList) {
      contactEntityService.saveEntity(contact);
    }
    List<ContactEntity> contactEntityListFromDB = contactEntityService.getAllEntities();

    Assert.assertEquals(contactEntityList.size(), contactEntityListFromDB.size());
    for (int i = 0; i < contactEntityList.size(); i++) {
      assertThat(contactEntityList.get(i), equalTo(contactEntityListFromDB.get(i)));
    }
  }

  // contactEntityService.updateEntity()
  @Test
  public void shouldReturnUpdatedContactFromDB() {
    ContactEntity contactEntity = getEntity();
    ContactEntity savedEntity = contactEntityService.saveEntity(contactEntity);
    savedEntity.setType("test");
    contactEntityService.updateEntity(savedEntity);
    ContactEntity updatedEntityFromDB = contactEntityService.getEntityById(savedEntity.getId());
    Assert.assertEquals("test", updatedEntityFromDB.getType());
  }

  @Test
  public void savedUserNumberNotEmpty() {
    ContactEntity contactEntity = getEntity();
    assertThat(contactEntityService.saveEntity(contactEntity).getNumber(), not(""));
  }
}
