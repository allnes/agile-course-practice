package ru.unn.agile.binarytree.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RegexMatcherForBinaryTreeStructure extends BaseMatcher {
    private final String regex;

    public RegexMatcherForBinaryTreeStructure(final String regex) {
        this.regex = regex;
    }

    public boolean matches(final Object o) {
        String obj = (String) o;
        return obj.matches(regex);
    }

    public void describeTo(final Description description) {
        description.appendText("matches regex = ");
        description.appendText(regex);
    }

    public static Matcher<? super String> matchesPattern(final String regex) {
        RegexMatcherForBinaryTreeStructure matcher = new RegexMatcherForBinaryTreeStructure(regex);
        //NOTE: this ugly cast is needed to workaround 'unchecked' Java warning
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>)   matcher;
        return castedMatcher;
    }
}
