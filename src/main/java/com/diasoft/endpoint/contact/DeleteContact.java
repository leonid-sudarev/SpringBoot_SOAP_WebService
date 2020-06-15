package com.diasoft.endpoint.contact;

import com.diasoft.service.ContactEntityService;
import com.diasoft.ws.DeleteContactRequest;
import com.diasoft.ws.DeleteContactResponse;
import com.diasoft.ws.ServiceStatus;

public class DeleteContact {

  public DeleteContactResponse getDeleteContactResponse(
      DeleteContactRequest request, ContactEntityService service) {

    DeleteContactResponse response = new DeleteContactResponse();
    ServiceStatus serviceStatus = new ServiceStatus();

    boolean flag = service.deleteEntityById(request.getId());

    if (!flag) {
      serviceStatus.setStatusCode("FAIL");
      serviceStatus.setMessage("Problem while deleting Entity id = " + request.getId());
    } else {
      serviceStatus.setStatusCode("SUCCESS");
      serviceStatus.setMessage("Deleted Successfully");
    }

    response.setServiceStatus(serviceStatus);
    return response;
  }
}
