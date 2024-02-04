package es.heroesfactory.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class PageDataNotFoundException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public PageDataNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
