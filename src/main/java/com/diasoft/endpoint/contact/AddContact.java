package com.diasoft.endpoint.contact;

import com.diasoft.entity.ContactEntity;
import com.diasoft.entity.PersonEntity;
import com.diasoft.service.ContactEntityService;
import com.diasoft.ws.AddContactRequest;
import com.diasoft.ws.AddContactResponse;
import com.diasoft.ws.Contact;
import com.diasoft.ws.Person;
import com.diasoft.ws.ServiceStatus;
import org.springframework.beans.BeanUtils;

public class AddContact {

  public AddContactResponse getAddContactResponse(
      AddContactRequest request, ContactEntityService service) {

    AddContactResponse response = new AddContactResponse();
    ServiceStatus serviceStatus = new ServiceStatus();

    Contact contact = new Contact();
    Person person = request.getPerson();
    PersonEntity personEntity =
        new PersonEntity(
            person.getFirstName(),
            person.getLastName(),
            person.getMiddleName(),
            person.getPosition());

    ContactEntity contactEntity =
        new ContactEntity(request.getNumber(), request.getType(), personEntity);

    contactEntity.setPersonEntity(personEntity);

    ContactEntity contactEntityDB = service.saveEntity(contactEntity);

    if (contactEntityDB == null) {
      serviceStatus.setStatusCode("FAILURE");
      serviceStatus.setMessage("Exception while adding Entity");
    } else {
      BeanUtils.copyProperties(contactEntityDB, contact);
      BeanUtils.copyProperties(contactEntityDB.getPersonEntity(), person);
      serviceStatus.setStatusCode("SUCCESS");
      serviceStatus.setMessage("Content Added Successfully");
    }
    contact.setPerson(person);
    response.setContact(contact);
    response.setServiceStatus(serviceStatus);
    return response;
  }
}
