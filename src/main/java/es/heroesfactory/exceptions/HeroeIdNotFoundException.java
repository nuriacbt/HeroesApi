package es.heroesfactory.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class HeroeIdNotFoundException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public HeroeIdNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
