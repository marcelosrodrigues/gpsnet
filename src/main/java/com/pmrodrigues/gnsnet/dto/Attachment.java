package com.pmrodrigues.gnsnet.dto;

import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by Marceloo on 03/10/2014.
 */
public class Attachment implements Serializable {
    private final String filename;
    private final InputStream attachment;

    public Attachment(String filename, InputStream attachment) {
        this.filename = filename;
        this.attachment = attachment;
    }

    public InputStream getInputStream() {
        return attachment;
    }

    public String getFileName() {
        return filename;
    }
}
