package ru.unn.agile.interpolationsearch.viewmodel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ILogger {
    void log(String logMessage) throws IOException;

    List<String> getLogList() throws IOException;
}
