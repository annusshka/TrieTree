package io.github.annusshka.lib;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TrieNode {
    char symbol;

    Set<TrieNode> children;

    boolean isLeaf;

    public TrieNode(char symbol, Set<TrieNode> children, boolean isLeaf) {
        this.symbol = symbol;
        this.children = children;
        this.isLeaf = isLeaf;
    }

    public TrieNode(char symbol) {
        this.symbol = symbol;
        this.children = null;
        this.isLeaf = false;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public Set<TrieNode> getChildren() {
        if (children == null) {
            children = new HashSet<>();
        }

        return children;
    }

    public void setChildren(Set<TrieNode> children) {
        this.children = Objects.requireNonNullElseGet(children, HashSet::new);
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean leaf) {
        this.isLeaf = leaf;
    }

    public void insert(String word) {
        if (word.length() == 0) {
            return;
        }

        char ch = word.charAt(0);
        TrieNode treeNode = searchBySymbol(ch);
        if (treeNode == null) {
            treeNode = new TrieNode(ch);
            children.add(treeNode);
        }

        if (word.length() == 1) {
            treeNode.setIsLeaf(true);
        }
        treeNode.insert(word.substring(1));
    }

    public TrieNode searchBySymbol(char ch) {
        for (TrieNode node: this.getChildren()) {
            if (node.getSymbol() == ch) {
                return node;
            }
        }
        return null;
    }

    public boolean find(String word) {
        TrieNode node = this;
        for (int i = 0; i < word.length(); i++) {
            node = node.searchBySymbol(word.charAt(i));
            if (node == null) {
                return false;
            }
        }
        return node.isLeaf();
    }

    public Set<String> findAllWords() {
        StringBuffer buffer = new StringBuffer();
        Set<String> actualSet = new HashSet<>();

        return findAllWords(actualSet, buffer);
    }

    private Set<String> findAllWords(Set<String> str, StringBuffer buffer) {
        buffer.append(this.getSymbol());
        if (this.isLeaf()) {
            str.add(buffer.toString().substring(1));
        }

        for (TrieNode child: this.getChildren()) {
            child.findAllWords(str, buffer);
        }
        buffer.deleteCharAt(buffer.length() - 1);

        return str;
    }

    public void delete(String word) throws Exception{
        if (word.length() == 0) {
            return;
        }
        TrieNode child = this.searchBySymbol(word.charAt(0));

        //Нет удаляемого слова в дереве
        if (child == null) {
            throw new Exception("Word not found!");
        }

        //Удаляемое слово есть, но у листа есть потомки (другое слово, начинающееся также)
        if (word.length() == 1 && child.getChildren().size() != 0) {
            child.isLeaf = false;
            return;
        }

        //По пути удаляемого слова найдены ещё листы
        int countLeaf = 0;
        if (child.isLeaf() && child.getChildren().size() != 0) {
            countLeaf++;
        }

        child.delete(word.substring(1));

        if (child.isLeaf && countLeaf == 0) {
            this.children.remove(child);
            if (this.children.size() == 0) {
                this.isLeaf = true;
                this.children = null;
            }
        }
    }
}
