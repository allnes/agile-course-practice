package ru.unn.agile.interpolationsearch.viewmodel;

import java.util.List;

public interface ILogger {
    void log(String logMessage);

    List<String> getLogList();
}
