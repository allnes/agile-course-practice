package ru.unn.agile.bitarray.infrastructure;

import ru.unn.agile.bitarray.viewmodel.LoggerInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class SimpleLogger implements LoggerInterface {
    public SimpleLogger(final String logFileName) {
        try {
            new BufferedWriter(new FileWriter(logFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(String s) {

    }

    @Override
    public List<String> get() {
        return null;
    }
}
