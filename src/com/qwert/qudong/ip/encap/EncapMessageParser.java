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

import com.qwert.qudong.base.BaseMessageParser;
import com.qwert.qudong.sero.messaging.IncomingMessage;
import com.qwert.qudong.sero.util.queue.ByteQueue;

/**
 * <p>EncapMessageParser class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
public class EncapMessageParser extends BaseMessageParser {
    /**
     * <p>Constructor for EncapMessageParser.</p>
     *
     * @param master a boolean.
     * @param protocal
     */
    public EncapMessageParser(boolean master) {
        super(master);
    }

    /** {@inheritDoc} */
    @Override
    protected IncomingMessage parseMessageImpl(ByteQueue queue) throws Exception {
        if (master)
            return EncapMessageResponse.createEncapMessageResponse(queue);
        return EncapMessageRequest.createEncapMessageRequest(queue);
    }
}
