package com.diasoft;

import com.diasoft.entity.ContactEntity;
import com.diasoft.entity.PersonEntity;
import com.diasoft.ws.AddContactRequest;
import com.diasoft.ws.Contact;
import com.diasoft.ws.DeleteContactRequest;
import com.diasoft.ws.GetAllContactsRequest;
import com.diasoft.ws.GetContactByIdRequest;
import com.diasoft.ws.Person;
import com.diasoft.ws.UpdateContactRequest;
import java.util.Random;

public class TestUtilsClass {
  private static final Random random = new Random();

  public static Contact getContact() {
    Contact contact = new Contact();
    Person person = new Person();

    person.setFirstName("FN_" + random.nextInt());
    person.setLastName("LN");
    person.setMiddleName("MN");
    person.setPosition("HardWorker");

    contact.setId(1L);
    contact.setNumber("123-123-123");
    contact.setType("work");
    contact.setPerson(person);

    return contact;
  }

  public static ContactEntity getContactEntity() {
    return new ContactEntity(
        "777-888-999",
        "work",
        new PersonEntity(
            "person_fn " + random.nextGaussian(), "person_ln", "person_mn", "employee"));
  }

  public static AddContactRequest getAddContactRequest() {
    AddContactRequest addContactRequest = new AddContactRequest();
    addContactRequest.setNumber("123");
    addContactRequest.setPerson(getContact().getPerson());
    addContactRequest.setType("home");
    return addContactRequest;
  }

  public static DeleteContactRequest getDeleteContactRequest(long id) {
    DeleteContactRequest deleteContactRequest = new DeleteContactRequest();
    deleteContactRequest.setId(id);
    return deleteContactRequest;
  }

  public static GetAllContactsRequest getGetAllContactRequest() {
    return new GetAllContactsRequest();
  }

  public static GetContactByIdRequest getContactByIdRequest(long id) {
    GetContactByIdRequest getContactByIdRequest = new GetContactByIdRequest();
    getContactByIdRequest.setId(id);
    return getContactByIdRequest;
  }

  public static UpdateContactRequest getUpdateContactRequest(long id) {
    UpdateContactRequest updateContactRequest = new UpdateContactRequest();
    Contact contact = getContact();
    contact.setId(id);
    updateContactRequest.setContact(contact);
    return updateContactRequest;
  }
}
