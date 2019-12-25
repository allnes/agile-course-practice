package ru.unn.agile.bitarray.viewmodel;

import java.util.List;

public interface LoggerInterface {
    void add(final String s);

    List<String> get();
}
