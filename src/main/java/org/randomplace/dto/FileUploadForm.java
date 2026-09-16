package org.randomplace.dto;

import jakarta.ws.rs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;
import jakarta.ws.rs.core.MediaType;
import java.io.InputStream;

public class FileUploadForm {

    @FormParam("filename")
    public String filename;

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream file;
}