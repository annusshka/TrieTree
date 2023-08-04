package io.github.annusshka.lib;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class Tests {

    @Test
    public void insert() {
        TrieNode node = new TrieNode(' ');

        node.insert("abc");
        node.insert("abcdefgt");
        node.insert("abcdef");
        node.insert("xzs");

        Set<String> expectedSet = new HashSet<>();
        expectedSet.add("abc");
        expectedSet.add("abcdefgt");
        expectedSet.add("abcdef");
        expectedSet.add("xzs");

        Assertions.assertThat(node.findAllWords()).isEqualTo(expectedSet);
    }

    @Test
    public void delete() throws Exception {
        TrieNode node = new TrieNode(' ');

        node.insert("abc");
        node.insert("abcdef");
        node.insert("abcde");
        node.insert("xzs");
        node.insert("abcdefgt");
        node.insert("abcdefgtkl");
        node.insert("cdef");

        Set<String> expectedSet = new HashSet<>();
        expectedSet.add("abc");
        expectedSet.add("abcde");
        expectedSet.add("abcdefgt");
        expectedSet.add("cdef");


        node.delete("abcdefgtkl");
        node.delete("abcdef");
        node.delete("xzs");
        Assertions.assertThat(node.findAllWords()).isEqualTo(expectedSet);
    }

    @Test
    public void testFind() {
        TrieNode node = new TrieNode(' ');

        node.insert("abc");
        node.insert("abcdef");
        node.insert("abcde");
        node.insert("xzs");

        Assertions.assertThat(node.find("abc")).isEqualTo(true);
        Assertions.assertThat(node.find("ab")).isEqualTo(false);
        Assertions.assertThat(node.find("abcd")).isEqualTo(false);
    }
}
