package com.diasoft.endpoit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.diasoft.TestUtilsClass;
import com.diasoft.endpoint.contact.AddContact;
import com.diasoft.endpoint.contact.DeleteContact;
import com.diasoft.endpoint.contact.GetContact;
import com.diasoft.endpoint.contact.UpdateContact;
import com.diasoft.repository.ContactEntityRepository;
import com.diasoft.repository.PersonEntityRepository;
import com.diasoft.service.ContactEntityService;
import com.diasoft.service.ContactEntityServiceImpl;
import com.diasoft.ws.AddContactRequest;
import com.diasoft.ws.AddContactResponse;
import com.diasoft.ws.Contact;
import com.diasoft.ws.UpdateContactRequest;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:/test.properties")
public class EndpointTests {
  @Resource private ContactEntityService contactEntityService;
  @Resource private ContactEntityRepository contactEntityRepository;
  @Resource private PersonEntityRepository personEntityRepository;

  @BeforeEach
  void initUseCase() {
    contactEntityService =
        new ContactEntityServiceImpl(personEntityRepository, contactEntityRepository);
    contactEntityRepository.deleteAll();
    personEntityRepository.deleteAll();
  }

  @Test
  public void shouldAddContactToDb() {
    AddContactRequest addContactRequest = TestUtilsClass.getAddContactRequest();
    AddContact addContactTest = new AddContact();
    AddContactResponse addContactResponse =
        addContactTest.getAddContactResponse(addContactRequest, contactEntityService);
    assertThat(addContactResponse.getServiceStatus().getStatusCode(), equalTo("SUCCESS"));
    assertThat(addContactRequest.getPerson(), equalTo(addContactResponse.getContact().getPerson()));
  }

  @Test
  public void shouldDeleteContactFromDb() {
    addContactToDb(2);
    DeleteContact deleteContact = new DeleteContact();
    deleteContact.getDeleteContactResponse(
        TestUtilsClass.getDeleteContactRequest(1L), contactEntityService);
    assertThat(contactEntityService.getEntityById(1L), equalTo(null));
  }

  @Test
  public void shouldUpdateContact() {
    addContactToDb(110);
    UpdateContact updateContact = new UpdateContact();
    UpdateContactRequest updateContactRequest = TestUtilsClass.getUpdateContactRequest(108L);

    Assert.assertFalse(
        contactEntityService
            .getEntityById(108L)
            .getPersonEntity()
            .getFirstName()
            .matches(updateContactRequest.getContact().getPerson().getFirstName()));

    updateContact.getUpdateContactResponse(updateContactRequest, contactEntityService);
    assertThat(
        contactEntityService.getEntityById(108L).getPersonEntity().getFirstName(),
        equalTo(updateContactRequest.getContact().getPerson().getFirstName()));
  }

  @Test
  public void shouldReturnContactList() {
    clearDb();
    GetContact getContact = new GetContact();
    List<Contact> contactList =
        getContact.getGetAllContactsResponse(contactEntityService).getContact();
    Assert.assertTrue(contactList.isEmpty());
    addContactToDb(5);
    contactList = getContact.getGetAllContactsResponse(contactEntityService).getContact();
    assertThat(contactList.size(), equalTo(5));
  }

  private void addContactToDb(int number) {
    for (int i = 0; i < number; i++) {
      contactEntityService.saveEntity(TestUtilsClass.getContactEntity());
    }
  }

  private void clearDb() {
    contactEntityRepository.deleteAll();
  }
}
