package com.cosmoloj.language.common.semantic;

import com.cosmoloj.language.api.semantic.Token;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class TokenTest {

    private record ComparableToken(int first, int last) implements Token {

        @Override
        public int order() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Test
    public void testComparator() {
        final List<Token> tokens = new ArrayList<>();

        final Token token1 = new ComparableToken(0, 2);
        final Token token2 = new ComparableToken(3, 4);
        final Token token3 = new ComparableToken(7, 11);
        final Token token4 = new ComparableToken(12, 20);
        final Token token5 = new ComparableToken(0, 4);
        final Token token6 = new ComparableToken(12, 25);


        tokens.add(token6);
        tokens.add(token3);
        tokens.add(token2);
        tokens.add(token5);
        tokens.add(token1);
        tokens.add(token4);

        Collections.sort(tokens, Token.FIRST_OPEN_FIRT_CLOSED);

        Assertions.assertEquals(0, tokens.indexOf(token1));
        Assertions.assertEquals(1, tokens.indexOf(token5));
        Assertions.assertEquals(2, tokens.indexOf(token2));
        Assertions.assertEquals(3, tokens.indexOf(token3));
        Assertions.assertEquals(4, tokens.indexOf(token4));
        Assertions.assertEquals(5, tokens.indexOf(token6));
    }

    @Test
    public void testComparator_reversed() {
        final List<Token> tokens = new ArrayList<>();

        final Token token1 = new ComparableToken(0, 2);
        final Token token2 = new ComparableToken(3, 4);
        final Token token3 = new ComparableToken(7, 11);
        final Token token4 = new ComparableToken(12, 20);
        final Token token5 = new ComparableToken(0, 4);
        final Token token6 = new ComparableToken(12, 25);


        tokens.add(token6);
        tokens.add(token3);
        tokens.add(token2);
        tokens.add(token5);
        tokens.add(token1);
        tokens.add(token4);

        Collections.sort(tokens, Token.FIRST_OPEN_LAST_CLOSED);

        Assertions.assertEquals(0, tokens.indexOf(token5));
        Assertions.assertEquals(1, tokens.indexOf(token1));
        Assertions.assertEquals(2, tokens.indexOf(token2));
        Assertions.assertEquals(3, tokens.indexOf(token3));
        Assertions.assertEquals(4, tokens.indexOf(token6));
        Assertions.assertEquals(5, tokens.indexOf(token4));
    }
}
