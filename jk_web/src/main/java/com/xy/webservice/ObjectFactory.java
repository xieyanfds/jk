
package com.xy.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xy.webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ExportE_QNAME = new QName("http://webservice.xy.com/", "exportE");
    private final static QName _ExportEResponse_QNAME = new QName("http://webservice.xy.com/", "exportEResponse");
    private final static QName _Exception_QNAME = new QName("http://webservice.xy.com/", "Exception");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xy.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link ExportEResponse }
     * 
     */
    public ExportEResponse createExportEResponse() {
        return new ExportEResponse();
    }

    /**
     * Create an instance of {@link ExportE }
     * 
     */
    public ExportE createExportE() {
        return new ExportE();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExportE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.xy.com/", name = "exportE")
    public JAXBElement<ExportE> createExportE(ExportE value) {
        return new JAXBElement<ExportE>(_ExportE_QNAME, ExportE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExportEResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.xy.com/", name = "exportEResponse")
    public JAXBElement<ExportEResponse> createExportEResponse(ExportEResponse value) {
        return new JAXBElement<ExportEResponse>(_ExportEResponse_QNAME, ExportEResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.xy.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

}
