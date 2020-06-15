package com.diasoft;

import com.diasoft.entity.ContactEntity;
import com.diasoft.repository.ContactEntityRepository;
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
public class ContactRepositoryTest {
  static long id;
  private ContactEntityRepository contactEntityRepository;

  @Resource
  public void setContactEntityRepository(ContactEntityRepository contactEntityRepository) {
    this.contactEntityRepository = contactEntityRepository;
  }

  private ContactEntity getEntity() {
    return TestUtilsClass.getContactEntity();
  }

  @Test
  public void shouldReturnContactById() {
    ContactEntity contactEntity = getEntity();

    ContactEntity saved = contactEntityRepository.save(contactEntity);
    long id = saved.getId();
    Assert.assertNotNull(contactEntityRepository.findById(id));
    Assert.assertEquals(saved, contactEntity);
  }

  @Test
  public void shouldReturnTrueIfPersisted() {
    ContactEntity contactEntity = getEntity();
    ContactEntity saved = contactEntityRepository.save(contactEntity);
    long id = saved.getId();
    Assert.assertTrue(contactEntityRepository.existsById(id));
  }

  @Test
  public void findByIdShouldReturnNonNullValue() {
    Assert.assertNotNull(contactEntityRepository.findById(id));
  }
}
