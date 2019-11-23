package exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
public class SaveFileException extends IOException {
    private String message;

    @Override
    public String toString() {
        String msg = "SaveFileException - can not save maze structure to file";
        if (message != null) {
            msg += ": " + message;
        }
        return msg;
    }
}
