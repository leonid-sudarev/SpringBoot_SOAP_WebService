package com.diasoft.endpoint.contact;

import com.diasoft.entity.ContactEntity;
import com.diasoft.entity.PersonEntity;
import com.diasoft.service.ContactEntityService;
import com.diasoft.ws.Contact;
import com.diasoft.ws.Person;
import com.diasoft.ws.ServiceStatus;
import com.diasoft.ws.UpdateContactRequest;
import com.diasoft.ws.UpdateContactResponse;

public class UpdateContact {

  public UpdateContactResponse getUpdateContactResponse(
      UpdateContactRequest request, ContactEntityService service) {
    UpdateContactResponse response = new UpdateContactResponse();
    ServiceStatus serviceStatus = new ServiceStatus();
    // 1. Find if  available
    Contact contact = request.getContact();
    long id = contact.getId();

    ContactEntity contactEntity = service.getEntityById(id);
    if (contactEntity == null) {
      serviceStatus.setStatusCode("NOT FOUND");
      serviceStatus.setMessage("Contact = " + id + " not found");
    } else {

      // 2. Get updated  information from the request
      Person person = request.getContact().getPerson();
      PersonEntity personEntity =
          new PersonEntity(
              person.getFirstName(),
              person.getLastName(),
              person.getMiddleName(),
              person.getPosition());

      personEntity.setId(contactEntity.getId());

      contactEntity.setNumber(contact.getNumber());
      contactEntity.setType(contact.getType());
      contactEntity.setPersonEntity(personEntity);

      boolean flag = service.updateEntity(contactEntity);

      // 3. update contact in DB
      if (!flag) {
        serviceStatus.setStatusCode("CONFLICT");
        serviceStatus.setMessage("Exception while updating Entity= " + id);

      } else {
        serviceStatus.setStatusCode("SUCCESS");
        serviceStatus.setMessage("Contact id= " + id + " was updated Successfully");
      }
    }

    response.setServiceStatus(serviceStatus);
    return response;
  }
}
