package com.epam.edu.kh.business.scanner;

import java.io.IOException;
import org.springframework.security.acls.model.NotFoundException;

import com.epam.edu.kh.business.entity.Record;

public interface SocialScanner {

    public String parseLink(final String linkToResource)
            throws NotFoundException;

    public String getResponse(final String postId) throws IOException;

    public Record parseResponse(final String linkToResource)
            throws IOException, NullPointerException;

}
