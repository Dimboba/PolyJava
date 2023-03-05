import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PrefixTreeTest {
    @Test
    public void constructor(){
        PrefixTree tree = new PrefixTree();
        assertEquals(tree.size(), 0);
    }

    @Test
    public void add(){
        PrefixTree tree = new PrefixTree();
        tree.add("a");
        assertEquals(tree.size(), 1);
        tree.add("b");
        tree.add("a");
        assertEquals(tree.size(), 2);
        tree.add("abo");
        tree.add("charm");
        tree.add("b");
        assertEquals(tree.size(), 4);
        boolean contains = tree.contains("a") && tree.contains("b")
                && tree.contains("abo") && tree.contains("charm");
        assertTrue(contains);
    }
    @Test
    public void delete(){
        PrefixTree tree = new PrefixTree();
        tree.add("a");
        tree.add("ab");
        tree.add("abc");
        tree.add("b");
        tree.add("c");
        tree.add("abcd");
        assertEquals(tree.size(), 6);

        tree.delete("a");
        tree.delete("abc");
        assertEquals(tree.size(), 4);
        assertFalse(tree.contains("a"));
        assertFalse(tree.contains("abc"));

        tree.delete("check");
        assertEquals(tree.size(), 4);
        assertFalse(tree.contains("check"));

        tree.add("abc");
        assertEquals(tree.size(), 5);
        assertTrue(tree.contains("abc"));
    }

    @Test
    public void prefix(){
        PrefixTree tree = new PrefixTree();
        tree.add("a");
        tree.add("ab");
        tree.add("abc");
        tree.add("b");
        tree.add("c");
        tree.add("abcd");
        Set<String> prefixWords = Set.of(tree.prefixWords("a"));
        Set<String> answer = Set.of("a", "ab", "abc", "abcd");
        assertEquals(answer, prefixWords);

        tree.delete("abc");
        tree.add("abck");
        prefixWords = Set.of(tree.prefixWords("abc"));
        answer = Set.of("abcd", "abck");
        assertEquals(answer, prefixWords);

        tree.add("Polytech");
        tree.add("Polykek");
        prefixWords = Set.of(tree.prefixWords("Poly"));
        answer = Set.of("Polytech", "Polykek");
        assertEquals(answer, prefixWords);
    }

}
