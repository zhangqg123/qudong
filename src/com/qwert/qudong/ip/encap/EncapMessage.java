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
import com.qwert.qudong.ip.IpMessage;
import com.qwert.qudong.msg.*;
import com.qwert.qudong.msg.ReadM7000Request;
import com.qwert.qudong.msg.ReadM7000Response;
import com.qwert.qudong.msg.delta.ReadDeltaRequest;
import com.qwert.qudong.msg.delta.ReadDeltaResponse;
import com.qwert.qudong.msg.kstar.ReadKstarRequest;
import com.qwert.qudong.msg.kstar.ReadKstarResponse;
import com.qwert.qudong.sero.util.queue.ByteQueue;

/**
 * <p>EncapMessage class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
public class EncapMessage extends IpMessage {
    /**
     * <p>Constructor for EncapMessage.</p>
     *
     * @param qwertMessage a {@link com.qwert.qudong.msg.QwertMessage} object.
     */
    public EncapMessage(QwertMessage qwertMessage) {
        super(qwertMessage);
    }

    /**
     * <p>getMessageData.</p>
     *
     * @return an array of {@link byte} objects.
     */
    public byte[] getMessageData() {
        ByteQueue msgQueue = new ByteQueue();

        // Write the particular message.
        qwertMessage.write(msgQueue);

        // Write the CRC
//        QwertUtils.pushShort(msgQueue, QwertUtils.calculateCRC(qwertMessage));
     //   getAsciiData()
        // Return the data.
        return msgQueue.popAll();
    }


}
