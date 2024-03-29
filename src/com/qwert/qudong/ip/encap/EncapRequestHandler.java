/*
 * ============================================================================
 * GNU General Public License
 * ============================================================================
 *
 * Copyright (C) 2006-2011 Serotonin Software Technologies Inc. http://serotoninsoftware.com
 * @author Matthew Lohbihler
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.qwert.qudong.ip.encap;

import com.qwert.qudong.QwertSlaveSet;
import com.qwert.qudong.base.BaseRequestHandler;
import com.qwert.qudong.msg.QwertRequest;
import com.qwert.qudong.msg.QwertResponse;
import com.qwert.qudong.sero.messaging.IncomingRequestMessage;
import com.qwert.qudong.sero.messaging.OutgoingResponseMessage;

/**
 * <p>EncapRequestHandler class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
public class EncapRequestHandler extends BaseRequestHandler {
    /**
     * <p>Constructor for EncapRequestHandler.</p>
     *
     * @param slave a {@link com.qwert.qudong.QwertSlaveSet} object.
     */
    public EncapRequestHandler(QwertSlaveSet slave) {
        super(slave);
    }

    /** {@inheritDoc} */
    public OutgoingResponseMessage handleRequest(IncomingRequestMessage req) throws Exception {
        EncapMessageRequest tcpRequest = (EncapMessageRequest) req;
        QwertRequest request = tcpRequest.getQwertRequest();
        QwertResponse response = handleRequestImpl(request);
        if (response == null)
            return null;
        return new EncapMessageResponse(response);
    }
}
