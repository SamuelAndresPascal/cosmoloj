package com.cosmoloj.language.common.impl.semantic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class EnumCaseTest {

    @Test
    public void equalCharacterIgnoreCase() {
        Assertions.assertTrue(EnumCase.equalCharacterIgnoreCase('A', 'a'));
        Assertions.assertTrue(EnumCase.equalCharacterIgnoreCase('A', 'A'));
        Assertions.assertTrue(EnumCase.equalCharacterIgnoreCase('a', 'A'));
        Assertions.assertTrue(EnumCase.equalCharacterIgnoreCase('a', 'a'));
        Assertions.assertFalse(EnumCase.equalCharacterIgnoreCase('A', 'b'));
        Assertions.assertFalse(EnumCase.equalCharacterIgnoreCase('a', 'b'));
        Assertions.assertFalse(EnumCase.equalCharacterIgnoreCase('A', 'B'));
        Assertions.assertFalse(EnumCase.equalCharacterIgnoreCase('a', 'B'));
        Assertions.assertFalse(EnumCase.equalCharacterIgnoreCase('B', 'a'));
        Assertions.assertFalse(EnumCase.equalCharacterIgnoreCase('B', 'A'));
        Assertions.assertFalse(EnumCase.equalCharacterIgnoreCase('b', 'a'));
        Assertions.assertFalse(EnumCase.equalCharacterIgnoreCase('b', 'A'));
    }
}
