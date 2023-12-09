package com.cosmoloj.language.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Samuel Andrés
 */
public class LanguageUtilTest {

    @Test
    public void equalCharacterIgnoreCase() {
        Assertions.assertTrue(LanguageUtil.equalCharacterIgnoreCase('A', 'a'));
        Assertions.assertTrue(LanguageUtil.equalCharacterIgnoreCase('A', 'A'));
        Assertions.assertTrue(LanguageUtil.equalCharacterIgnoreCase('a', 'A'));
        Assertions.assertTrue(LanguageUtil.equalCharacterIgnoreCase('a', 'a'));
        Assertions.assertFalse(LanguageUtil.equalCharacterIgnoreCase('A', 'b'));
        Assertions.assertFalse(LanguageUtil.equalCharacterIgnoreCase('a', 'b'));
        Assertions.assertFalse(LanguageUtil.equalCharacterIgnoreCase('A', 'B'));
        Assertions.assertFalse(LanguageUtil.equalCharacterIgnoreCase('a', 'B'));
        Assertions.assertFalse(LanguageUtil.equalCharacterIgnoreCase('B', 'a'));
        Assertions.assertFalse(LanguageUtil.equalCharacterIgnoreCase('B', 'A'));
        Assertions.assertFalse(LanguageUtil.equalCharacterIgnoreCase('b', 'a'));
        Assertions.assertFalse(LanguageUtil.equalCharacterIgnoreCase('b', 'A'));
    }
}
