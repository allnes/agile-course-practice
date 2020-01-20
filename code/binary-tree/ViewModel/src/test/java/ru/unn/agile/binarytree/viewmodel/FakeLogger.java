package ru.unn.agile.binarytree.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private final ArrayList<String> log = new ArrayList<>();

    @Override
    public void logOfBinaryTreeStructure(final String s) {
        log.add(s);
    }

    @Override
    public List<String> getLogOfBinaryTreeStructure() {
        return log;
    }
}
