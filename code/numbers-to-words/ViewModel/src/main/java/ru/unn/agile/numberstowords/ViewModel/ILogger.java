package ru.unn.agile.numberstowords.ViewModel;

import java.util.List;

public interface ILogger {
    void log(String s);

    List<String> getLog();
}
