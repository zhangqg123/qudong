package com.qwert.qudong.serial;

import com.qwert.qudong.msg.QwertMessage;

/**
 * <p>Abstract SerialMessage class.</p>
 *
 * @author Matthew Lohbihler
 * @version 5.0.0
 */
abstract public class SerialMessage {
    protected final QwertMessage qwertMessage;

    /**
     * <p>Constructor for SerialMessage.</p>
     *
     * @param QwertMessage a {@link com.qwert.qudong.msg.QwertMessage} object.
     */
    public SerialMessage(QwertMessage qwertMessage) {
        this.qwertMessage = qwertMessage;
    }

    /**
     * <p>Getter for the field <code>QwertMessage</code>.</p>
     *
     * @return a {@link com.qwert.qudong.msg.QwertMessage} object.
     */
    public QwertMessage getQwertMessage() {
        return qwertMessage;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "SerialMessage [QwertMessage=" + qwertMessage + "]";
    }
}
