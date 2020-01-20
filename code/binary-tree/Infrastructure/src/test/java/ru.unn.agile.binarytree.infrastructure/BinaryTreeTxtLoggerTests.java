package ru.unn.agile.binarytree.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.junit.Assert.*;
import static ru.unn.agile.binarytree.infrastructure.RegexMatcherForBinaryTreeStructure.matchesPattern;

public class BinaryTreeTxtLoggerTests {
    private static final String FILENAME = "./TxtLogger_Tests-lab3.log";
    private BinaryTreeTxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new BinaryTreeTxtLogger(FILENAME);
    }

    @Test
    public void canCreateLoggerWithFileNameForBinaryTreeLoger() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canCreateLogFileOnDiskForBinaryTreeLogger() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test message";

        txtLogger.logOfBinaryTreeStructure(testMessage);

        String message = txtLogger.getLogOfBinaryTreeStructure().get(0);
        assertThat(message, matchesPattern(".*" + testMessage + "$"));
    }

    @Test
    public void canWriteSeveralLogMessageForBinaryTreeLogger() {
        String[] messages = {"Test message - 1", "Test message - 2"};

        txtLogger.logOfBinaryTreeStructure(messages[0]);
        txtLogger.logOfBinaryTreeStructure(messages[1]);

        List<String> actualMessages = txtLogger.getLogOfBinaryTreeStructure();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertThat(actualMessages.get(i), matchesPattern(".*" + messages[i] + "$"));
        }
    }

    @Test
    public void doesLogContainDateAndTimeForBinaryTreeLogger() {
        String testMessage = "Test message";

        txtLogger.logOfBinaryTreeStructure(testMessage);

        String message = txtLogger.getLogOfBinaryTreeStructure().get(0);
        assertThat(message, matchesPattern("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
