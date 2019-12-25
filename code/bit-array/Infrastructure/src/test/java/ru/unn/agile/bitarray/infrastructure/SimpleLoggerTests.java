package ru.unn.agile.bitarray.infrastructure;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimpleLoggerTests {
    private static final String LOG_FILE = "./simple_logger_test.log";
    private SimpleLogger logger;

    @Before
    public void setUp() {
        logger = new SimpleLogger(LOG_FILE);
    }

    @Test
    public void canConstruct() {
        assertNotNull(logger);
    }
}
