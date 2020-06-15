package com.diasoft.config;

import com.diasoft.endpoint.ContactEndpoint;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Bean
  public ServletRegistrationBean messageDispatcherServlet(ApplicationContext appContext) {
    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
    servlet.setApplicationContext(appContext);
    servlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean(servlet, "/ws/*");
  }

  // http://localhost:8080/ws/contacts.wsdl
  @Bean(name = "contacts")
  public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("ContactsPort");
    wsdl11Definition.setLocationUri("/ws");
    wsdl11Definition.setTargetNamespace(ContactEndpoint.NAMESPACE_URI);
    wsdl11Definition.setSchema(schema);
    return wsdl11Definition;
  }

  @Bean
  public XsdSchema contactsSchema() {
    return new SimpleXsdSchema(new ClassPathResource("/xsd/contacts.xsd"));
  }
}
