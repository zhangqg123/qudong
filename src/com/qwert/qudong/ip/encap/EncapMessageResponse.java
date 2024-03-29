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

import com.qwert.qudong.base.QwertAsciiUtils;
import com.qwert.qudong.base.QwertUtils;
import com.qwert.qudong.exception.QudongTransportException;
import com.qwert.qudong.ip.IpMessageResponse;
import com.qwert.qudong.msg.QwertMessage;
import com.qwert.qudong.msg.QwertResponse;
import com.qwert.qudong.sero.util.queue.ByteQueue;

/**
 * <p>EncapMessageResponse class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
public class EncapMessageResponse extends EncapMessage implements IpMessageResponse {
    static EncapMessageResponse createEncapMessageResponse(ByteQueue queue) throws QudongTransportException {
    	if(queue.size()==0)
    	    return null;
        // Create the modbus response.
//        ByteQueue msgQueue = QwertAsciiUtils.getUnDianzongMessage(queue);
        QwertResponse response = QwertResponse.createQwertResponse(queue);

//        QwertResponse response = QwertResponse.createQwertResponse(queue);
        EncapMessageResponse encapResponse = new EncapMessageResponse(response);
        // Check the CRC
 //       QwertUtils.checkCRC(encapResponse.qwertMessage, queue);

        return encapResponse;
    }

    /**
     * <p>Constructor for EncapMessageResponse.</p>
     *
     * @param qwertResponse a {@link com.qwert.qudong.msg.QwertResponse} object.
     */
    public EncapMessageResponse(QwertResponse qwertResponse) {
        super(qwertResponse);
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
