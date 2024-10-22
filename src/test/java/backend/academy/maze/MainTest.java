package backend.academy.maze;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    public void testHandleError() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        Exception testException = new Exception("Test error");

        Main.handleError(printStream, testException);

        String actualOutput = outputStream.toString().trim();

        System.out.println("Фактический результат: '" + actualOutput + "'");

        String expectedOutput = "Произошла ошибка: Test error\nПопробуйте снова.";

        assertEquals(normalizeLineEndings(expectedOutput), normalizeLineEndings(actualOutput));
    }

    private String normalizeLineEndings(String str) {
        return str.replace("\r\n", "\n").replace("\r", "\n");
    }
}

