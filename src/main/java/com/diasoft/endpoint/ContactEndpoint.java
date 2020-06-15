package com.diasoft.endpoint;

import com.diasoft.endpoint.contact.AddContact;
import com.diasoft.endpoint.contact.DeleteContact;
import com.diasoft.endpoint.contact.GetContact;
import com.diasoft.endpoint.contact.UpdateContact;
import com.diasoft.service.ContactEntityService;
import com.diasoft.ws.AddContactRequest;
import com.diasoft.ws.AddContactResponse;
import com.diasoft.ws.DeleteContactRequest;
import com.diasoft.ws.DeleteContactResponse;
import com.diasoft.ws.GetAllContactsRequest;
import com.diasoft.ws.GetAllContactsResponse;
import com.diasoft.ws.GetContactByIdRequest;
import com.diasoft.ws.GetContactByIdResponse;
import com.diasoft.ws.UpdateContactRequest;
import com.diasoft.ws.UpdateContactResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/** https://docs.oracle.com/javaee/5/tutorial/doc/bnayn.html */
@Endpoint
public class ContactEndpoint {

  public static final String NAMESPACE_URI = "http://www.diasoft.com/contacts-ws";

  private ContactEntityService service;

  // различные операции вынесены в отдельные вспомогательные классы
  final GetContact getContactPayload = new GetContact();
  final AddContact addContactPayload = new AddContact();
  final UpdateContact updateContactPayLoad = new UpdateContact();
  final DeleteContact deleteContactPayLoad = new DeleteContact();

  @Autowired
  public ContactEndpoint(ContactEntityService service) {
    this.service = service;
  }

  // get payload
  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getContactByIdRequest")
  @ResponsePayload
  public GetContactByIdResponse getContactById(@RequestPayload GetContactByIdRequest request) {
    return getContactPayload.getGetContactByIdResponse(request, service);
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllContactsRequest")
  @ResponsePayload
  public GetAllContactsResponse getAllContacts(@RequestPayload GetAllContactsRequest request) {
    return getContactPayload.getGetAllContactsResponse(service);
  }

  // add payload
  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addContactRequest")
  @ResponsePayload
  public AddContactResponse addContact(@RequestPayload AddContactRequest request) {
    return addContactPayload.getAddContactResponse(request, service);
  }

  // update payload
  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateContactRequest")
  @ResponsePayload
  public UpdateContactResponse updateContact(@RequestPayload UpdateContactRequest request) {
    return updateContactPayLoad.getUpdateContactResponse(request, service);
  }

  // delete payload
  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteContactRequest")
  @ResponsePayload
  public DeleteContactResponse deleteContact(@RequestPayload DeleteContactRequest request) {
    return deleteContactPayLoad.getDeleteContactResponse(request, service);
  }

  public ContactEndpoint() {}
}
