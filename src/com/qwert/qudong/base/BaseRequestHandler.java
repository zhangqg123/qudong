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
package com.qwert.qudong.base;

import com.qwert.qudong.ProcessImage;
import com.qwert.qudong.QwertSlaveSet;
import com.qwert.qudong.exception.QudongTransportException;
import com.qwert.qudong.msg.QwertRequest;
import com.qwert.qudong.msg.QwertResponse;
import com.qwert.qudong.sero.messaging.RequestHandler;

/**
 * <p>Abstract BaseRequestHandler class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
abstract public class BaseRequestHandler implements RequestHandler {
    protected QwertSlaveSet slave;

    /**
     * <p>Constructor for BaseRequestHandler.</p>
     *
     * @param slave a {@link com.qwert.qudong.QwertSlaveSet} object.
     */
    public BaseRequestHandler(QwertSlaveSet slave) {
        this.slave = slave;
    }

    /**
     * <p>handleRequestImpl.</p>
     *
     * @param request a {@link com.qwert.qudong.msg.QwertRequest} object.
     * @return a {@link com.qwert.qudong.msg.QwertResponse} object.
     * @throws com.qwert.qudong.exception.QudongTransportException if any.
     */
    protected QwertResponse handleRequestImpl(QwertRequest request) throws QudongTransportException {
 //       request.validate(slave);

        int slaveId = request.getSlaveId();

        // Check the slave id.
        if (slaveId == 0) {
            // Broadcast message. Send to all process images.
            for (ProcessImage processImage : slave.getProcessImages())
                request.handle(processImage);
            return null;
        }

        // Find the process image to which to send.
        ProcessImage processImage = slave.getProcessImage(slaveId);
        if (processImage == null)
            return null;

        return request.handle(processImage);
    }
}
