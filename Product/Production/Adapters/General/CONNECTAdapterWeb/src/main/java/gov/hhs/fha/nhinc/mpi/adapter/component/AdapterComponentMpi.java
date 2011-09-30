/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  
 * Copyright 2010(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *  
 */
package gov.hhs.fha.nhinc.mpi.adapter.component;

import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;
import javax.annotation.Resource;
import org.hl7.v3.RespondingGatewayPRPAIN201305UV02RequestType;
import org.hl7.v3.PRPAIN201306UV02;

/**
 *
 * @author Sai Valluripalli
 */
@WebService(serviceName = "AdapterComponentMpiService", portName = "AdapterComponentMpiPort", endpointInterface = "gov.hhs.fha.nhinc.adaptercomponentmpi.AdapterComponentMpiPortType", targetNamespace = "urn:gov:hhs:fha:nhinc:adaptercomponentmpi", wsdlLocation = "WEB-INF/wsdl/AdapterComponentMpi/AdapterComponentMpi.wsdl")
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public class AdapterComponentMpi
{
    @Resource
    private WebServiceContext context;

    public org.hl7.v3.PRPAIN201306UV02 findCandidates(RespondingGatewayPRPAIN201305UV02RequestType findCandidatesRequest)
    {
        AdapterComponentMpiImpl oImpl = new AdapterComponentMpiImpl();
        PRPAIN201306UV02 oResponse = oImpl.query(findCandidatesRequest.getPRPAIN201305UV02(), findCandidatesRequest.getAssertion());
        return oResponse;
    }
}
