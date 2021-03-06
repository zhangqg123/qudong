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
package com.qwert.qudong.serial.dianzong;

import com.qwert.qudong.base.QwertAsciiUtils;
import com.qwert.qudong.exception.QudongTransportException;
import com.qwert.qudong.msg.QwertMessage;
import com.qwert.qudong.msg.QwertRequest;
import com.qwert.qudong.sero.messaging.IncomingRequestMessage;
import com.qwert.qudong.sero.messaging.OutgoingRequestMessage;
import com.qwert.qudong.sero.util.queue.ByteQueue;

/**
 * <p>DianzongMessageRequest class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
public class DianzongMessageRequest extends DianzongMessage implements OutgoingRequestMessage, IncomingRequestMessage {
    static DianzongMessageRequest createDianzongMessageRequest(ByteQueue queue) throws QudongTransportException {
        ByteQueue msgQueue = QwertAsciiUtils.getUnDianzongMessage(queue);
        QwertRequest request = QwertRequest.createQwertRequest(msgQueue);
        DianzongMessageRequest dianzongRequest = new DianzongMessageRequest(request);

        // Return the data.
        return dianzongRequest;
    }

    /**
     * <p>Constructor for DianzongMessageRequest.</p>
     *
     * @param QwertMessage a {@link com.qwert.qudong.msg.QwertMessage} object.
     */
    public DianzongMessageRequest(QwertMessage qwertMessage) {
        super(qwertMessage);
    }

    /** {@inheritDoc} */
    @Override
    public boolean expectsResponse() {
        return qwertMessage.getSlaveId() != 0;
    }

    /**
     * <p>getQwertRequest.</p>
     *
     * @return a {@link com.qwert.qudong.msg.QwertRequest} object.
     */
    public QwertRequest getQwertRequest() {
        return (QwertRequest) qwertMessage;
    }
}
