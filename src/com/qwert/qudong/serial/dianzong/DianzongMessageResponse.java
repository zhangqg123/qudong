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
import com.qwert.qudong.msg.QwertResponse;
import com.qwert.qudong.sero.messaging.IncomingResponseMessage;
import com.qwert.qudong.sero.messaging.OutgoingResponseMessage;
import com.qwert.qudong.sero.util.queue.ByteQueue;

/**
 * <p>DianzongMessageResponse class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
public class DianzongMessageResponse extends DianzongMessage implements OutgoingResponseMessage, IncomingResponseMessage {
    static DianzongMessageResponse createDianzongMessageResponse(ByteQueue queue) throws QudongTransportException {
        ByteQueue msgQueue = QwertAsciiUtils.getUnDianzongMessage(queue);
        QwertResponse response = QwertResponse.createQwertResponse(msgQueue);
        DianzongMessageResponse dianzongResponse = new DianzongMessageResponse(response);

        // Return the data.
        return dianzongResponse;
    }

    /**
     * <p>Constructor for DianzongMessageResponse.</p>
     *
     * @param QwertMessage a {@link com.qwert.qudong.msg.QwertMessage} object.
     */
    public DianzongMessageResponse(QwertMessage QwertMessage) {
        super(QwertMessage);
    }

    /**
     * <p>getQwertResponse.</p>
     *
     * @return a {@link com.qwert.qudong.msg.QwertResponse} object.
     */
    public QwertResponse getQwertResponse() {
        return (QwertResponse) qwertMessage;
    }
}
