package exceptions;

import java.io.IOException;

public class ReadFileException extends IOException {
    @Override
    public String toString() {
        return "ReadFileException - can not read maze structure from file.";
    }
}
