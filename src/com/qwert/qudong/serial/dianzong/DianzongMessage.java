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
import com.qwert.qudong.serial.SerialMessage;
import com.qwert.qudong.sero.util.queue.ByteQueue;

/**
 * <p>Abstract DianzongMessage class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
abstract public class DianzongMessage extends SerialMessage {
//    private static final byte START = ':';
//    private static final byte[] END = { '\r', '\n' };
    private static final byte START = '~';
    private static final byte[] END = { '\r', '\n' };

    DianzongMessage(QwertMessage qwertMessage) {
        super(qwertMessage);
    }


    /**
     * <p>getMessageData.</p>
     *
     * @return an array of {@link byte} objects.
     * @throws Exception 
     */

    public byte[] getMessageData() {
        ByteQueue queue = new ByteQueue();
        qwertMessage.write(queue);
        return QwertAsciiUtils.getAsciiData(queue);
    }

}
