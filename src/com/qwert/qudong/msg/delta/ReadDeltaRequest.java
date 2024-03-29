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
package com.qwert.qudong.msg.delta;

import com.qwert.qudong.ProcessImage;
import com.qwert.qudong.code.FunctionCode;
import com.qwert.qudong.exception.QudongTransportException;
import com.qwert.qudong.msg.QwertResponse;
import com.qwert.qudong.msg.ReadDianzongResponse;
import com.qwert.qudong.msg.kstar.ReadKstarNumericRequest;

/**
 * <p>ReadDianzongRequest class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
public class ReadDeltaRequest extends ReadDeltaNumericRequest {
    /**
     * <p>Constructor for ReadDianzongRequest.</p>
     *
     * @param slaveId a int.
     * @throws QudongTransportException if any.
     */
    public ReadDeltaRequest(int slaveId, String command)
            throws QudongTransportException {
        super(slaveId,command);
    }

    public ReadDeltaRequest(int slaveId) throws QudongTransportException {
        super(slaveId);
    }

    @Override
    public QwertResponse handleImpl(ProcessImage processImage) throws QudongTransportException {
        return new ReadDianzongResponse(slaveId, getData(processImage));
    }

    /** {@inheritDoc} */
    @Override
    protected short getNumeric(ProcessImage processImage, int index) throws QudongTransportException {
        return processImage.getHoldingRegister(index);
    }
    @Override
    public QwertResponse getResponseInstance(int slaveId) throws QudongTransportException{
        return new ReadDianzongResponse(slaveId);
    }


    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "ReadDeltaRequest [slaveId=" + slaveId + ", toString()=" + super.toString() + "]";
    }
}
