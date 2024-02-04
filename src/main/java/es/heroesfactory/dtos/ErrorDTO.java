package es.heroesfactory.dtos;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private String error;

}
