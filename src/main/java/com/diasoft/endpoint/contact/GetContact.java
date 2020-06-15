package com.diasoft.endpoint.contact;

import com.diasoft.entity.ContactEntity;
import com.diasoft.service.ContactEntityService;
import com.diasoft.ws.Contact;
import com.diasoft.ws.GetAllContactsResponse;
import com.diasoft.ws.GetContactByIdRequest;
import com.diasoft.ws.GetContactByIdResponse;
import com.diasoft.ws.Person;
import com.diasoft.ws.ServiceStatus;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;

public class GetContact {

  public GetContactByIdResponse getGetContactByIdResponse(
      GetContactByIdRequest request, ContactEntityService service) {

    GetContactByIdResponse response = new GetContactByIdResponse();
    ServiceStatus serviceStatus = new ServiceStatus();

    ContactEntity contactEntity = service.getEntityById(request.getId());
    if (contactEntity == null) {
      serviceStatus.setStatusCode("NOT FOUND");
      serviceStatus.setMessage("Contact not found");
      response.setServiceStatus(serviceStatus);
      return response;
    } else {
      Contact contact = new Contact();
      Person person = new Person();

      BeanUtils.copyProperties(contactEntity.getPersonEntity(), person);
      BeanUtils.copyProperties(contactEntity, contact);

      contact.setPerson(person);
      response.setContact(contact);

      return response;
    }
  }

  public GetAllContactsResponse getGetAllContactsResponse(ContactEntityService service) {
    GetAllContactsResponse response = new GetAllContactsResponse();
    List<Contact> contactsList = new ArrayList<>();
    List<ContactEntity> contactEntityList = service.getAllEntities();

    for (ContactEntity entity : contactEntityList) {
      Person person = new Person();
      Contact contact = new Contact();
      BeanUtils.copyProperties(entity.getPersonEntity(), person);
      BeanUtils.copyProperties(entity, contact);
      contact.setPerson(person);
      contactsList.add(contact);
    }
    response.getContact().addAll(contactsList);
    return response;
  }
}
