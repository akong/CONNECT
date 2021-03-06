/*
 * Copyright (c) 2012, United States Government, as represented by the Secretary of Health and Human Services.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above
 *       copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the United States Government nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.hhs.fha.nhinc.docsubmission._20.nhin.deferred.request;


import ihe.iti.xdr._2007.XDRDeferredRequest20PortType;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequestType;
import gov.hhs.fha.nhinc.aspect.InboundMessageEvent;
import gov.hhs.fha.nhinc.docsubmission.aspect.DocSubmissionBaseEventDescriptionBuilder;
import gov.hhs.fha.nhinc.docsubmission.inbound.deferred.request.InboundDocSubmissionDeferredRequest;

import javax.annotation.Resource;
import javax.xml.ws.BindingType;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.Addressing;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;


/**
 * 
 * @author JHOPPESC
 */
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
@Addressing(enabled = true)
public class NhinXDRRequest20 implements XDRDeferredRequest20PortType {

    private WebServiceContext context;

    private InboundDocSubmissionDeferredRequest inboundDocSubmissionRequest;

    @InboundMessageEvent(serviceType = "Document Submission Deferred Request", version = "2.0", 
            beforeBuilder = DocSubmissionBaseEventDescriptionBuilder.class, 
            afterReturningBuilder = DocSubmissionBaseEventDescriptionBuilder.class)
    public RegistryResponseType provideAndRegisterDocumentSetBDeferredRequest(
            ProvideAndRegisterDocumentSetRequestType body) {
        return new NhinDocSubmissionDeferredRequestImpl20(inboundDocSubmissionRequest)
                .provideAndRegisterDocumentSetBRequest(body, context);
    }

    public void setInboundDocSubmissionRequest(InboundDocSubmissionDeferredRequest inboundDocSubmissionRequest) {
        this.inboundDocSubmissionRequest = inboundDocSubmissionRequest;
    }

    @Resource
    public void setContext(WebServiceContext context) {
        this.context = context;
    }
}
