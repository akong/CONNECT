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
package gov.hhs.fha.nhinc.admindistribution.inbound;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import gov.hhs.fha.nhinc.admindistribution.AdminDistributionAuditLogger;
import gov.hhs.fha.nhinc.admindistribution.AdminDistributionPolicyChecker;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import oasis.names.tc.emergency.edxl.de._1.EDXLDistribution;

/**
 * @author akong
 * 
 */
public class StandardInboundAdminDistribution extends AbstractInboundAdminDistribution {

    private Log log = LogFactory.getLog(StandardInboundAdminDistribution.class);
    private AdminDistributionPolicyChecker policyChecker = new AdminDistributionPolicyChecker();
    private PassthroughInboundAdminDistribution passthroughAdminDist = new PassthroughInboundAdminDistribution();

    /**
     * Constructor.
     */
    public StandardInboundAdminDistribution() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param passthroughAdminDist
     * @param policyChecker
     * @param auditLogger
     * @param log
     */
    public StandardInboundAdminDistribution(PassthroughInboundAdminDistribution passthroughAdminDist,
            AdminDistributionPolicyChecker policyChecker, AdminDistributionAuditLogger auditLogger, Log log) {
        this.passthroughAdminDist = passthroughAdminDist;
        this.policyChecker = policyChecker;
        this.auditLogger = auditLogger;
        this.log = log;
    }

    @Override
    void processAdminDistribution(EDXLDistribution body, AssertionType assertion) {
        if (isPolicyValid(body, assertion)) {
            passthroughAdminDist.processAdminDistribution(body, assertion);
        } else {
            log.warn("Invalid policy.  Will not send message to adapter.");
        }
    }

    private boolean isPolicyValid(EDXLDistribution body, AssertionType assertion) {
        boolean result = false;

        if (body != null) {
            result = policyChecker.checkIncomingPolicy(body, assertion);
        } else {
            log.warn("Admin Dist request body was null");
        }

        return result;
    }

}
