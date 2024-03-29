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
package com.qwert.qudong.ip;

import com.qwert.qudong.msg.QwertMessage;

/**
 * <p>Abstract IpMessage class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
abstract public class IpMessage {
    protected final QwertMessage qwertMessage;

    /**
     * <p>Constructor for IpMessage.</p>
     *
     * @param qwertMessage a {@link com.qwert.qudong.msg.QwertMessage} object.
     */
    public IpMessage(QwertMessage qwertMessage) {
        this.qwertMessage = qwertMessage;
    }

    /**
     * <p>Getter for the field <code>qwertMessage</code>.</p>
     *
     * @return a {@link com.qwert.qudong.msg.QwertMessage} object.
     */
    public QwertMessage getQwertMessage() {
        return qwertMessage;
    }
}
