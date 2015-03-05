package com.epam.edu.kh.business.scanner;

import java.io.IOException;
import org.springframework.security.acls.model.NotFoundException;

import com.epam.edu.kh.business.entity.Record;

public interface JsonScannerOfResponse {

    public String parseLink(final String linkToResource)
            throws NotFoundException;

    public String getJsonOfResponse(final String postId) throws IOException;

    public Record parseJsonOfResponse(final String linkToResource)
            throws IOException, NullPointerException;

}
