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
package com.qwert.qudong.msg;

import com.qwert.qudong.base.QwertUtils;
import com.qwert.qudong.code.FunctionCode;
import com.qwert.qudong.exception.QudongTransportException;
import com.qwert.qudong.sero.util.queue.ByteQueue;

/**
 * <p>ReportSlaveIdResponse class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
public class ReportSlaveIdResponse extends QwertResponse {
    private byte[] data;

    ReportSlaveIdResponse(int slaveId) throws QudongTransportException {
        super(slaveId);
    }

    ReportSlaveIdResponse(int slaveId, byte[] data) throws QudongTransportException {
        super(slaveId);
        this.data = data;
    }


    /** {@inheritDoc} */
    @Override
    protected void readResponse(ByteQueue queue) {
        int numberOfBytes = QwertUtils.popUnsignedByte(queue);
        if (queue.size() < numberOfBytes)
            throw new ArrayIndexOutOfBoundsException();

        data = new byte[numberOfBytes];
        queue.pop(data);
    }

    /** {@inheritDoc} */
    @Override
    protected void writeResponse(ByteQueue queue) {
        QwertUtils.pushByte(queue, data.length);
        queue.push(data);
    }

    /**
     * <p>Getter for the field <code>data</code>.</p>
     *
     * @return an array of {@link byte} objects.
     */
    public byte[] getData() {
        return data;
    }
}
