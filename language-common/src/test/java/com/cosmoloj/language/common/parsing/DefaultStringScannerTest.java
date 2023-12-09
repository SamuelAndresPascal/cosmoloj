package com.cosmoloj.language.common.parsing;

import com.cosmoloj.language.api.parsing.Scanner;
import com.cosmoloj.language.common.impl.parsing.DefaultStringScanner;
import com.cosmoloj.language.api.exception.LanguageException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultStringScannerTest {

    private static class StringScanner extends DefaultStringScanner {

        StringScanner(final String text) {
            super(text);
        }
    }

    @Test
    public void getChar_1() throws LanguageException {

        final String input = "   1568   ";

        final Scanner scanner = new DefaultStringScanner(input);

        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals(' ', scanner.peek(1));
        Assertions.assertEquals(' ', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals(' ', scanner.peek(1));
        Assertions.assertEquals(' ', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals('1', scanner.peek(1));
        Assertions.assertEquals(' ', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals('5', scanner.peek(1));
        Assertions.assertEquals('1', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals('6', scanner.peek(1));
        Assertions.assertEquals('5', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals('8', scanner.peek(1));
        Assertions.assertEquals('6', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals(' ', scanner.peek(1));
        Assertions.assertEquals('8', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals(' ', scanner.peek(1));
        Assertions.assertEquals(' ', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals(' ', scanner.peek(1));
        Assertions.assertEquals(' ', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals(' ', scanner.nextInt());
        Assertions.assertFalse(scanner.hasNext());
    }

    @Test
    public void getChar_2() throws LanguageException {
        Assertions.assertThrows(LanguageException.class, () -> {
        final String input = "   1568   ";

        final StringScanner scanner = new StringScanner(input);

        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals(' ', scanner.peek(1));
        Assertions.assertEquals(' ', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals(' ', scanner.peek(1));
        Assertions.assertEquals(' ', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals('1', scanner.peek(1));
        Assertions.assertEquals(' ', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals('5', scanner.peek(1));
        Assertions.assertEquals('1', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals('6', scanner.peek(1));
        Assertions.assertEquals('5', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals('8', scanner.peek(1));
        Assertions.assertEquals('6', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals(' ', scanner.peek(1));
        Assertions.assertEquals('8', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals(' ', scanner.peek(1));
        Assertions.assertEquals(' ', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals(' ', scanner.peek(1));
        Assertions.assertEquals(' ', scanner.nextInt());
        Assertions.assertTrue(scanner.hasNext());
        scanner.peek(1); // LanguageException !
        Assertions.assertEquals(' ', scanner.nextInt());
        Assertions.assertFalse(scanner.hasNext());
        });
    }
}
