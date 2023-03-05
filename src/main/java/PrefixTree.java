
import java.util.*;
import java.lang.StringBuilder;

public class PrefixTree {
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        PrefixTree that = (PrefixTree) object;
        return numberOfWords == that.numberOfWords && root.equals(that.root);
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), numberOfWords, root);
    }

    private static class Dot {
        private char symbol;
        private boolean word = false;
        private ArrayList<Dot> sons = new ArrayList<>();
        private Dot parent;

        public Dot(char sym, Dot par) {
            this.symbol = sym;
            this.parent = par;
        }

        public ArrayList<Dot> getSons(){
            return sons;
        }
        public void setWord(boolean b) {
            word = b;
        }
        public boolean getWord() {
            return word;
        }

        public char getSymbol() {
            return symbol;
        }

        public Dot getSon(int ind) {
            return sons.get(ind);
        }
        public int findSon(char sym) {
            for (int i = 0; i < sons.size(); i++) {
                if (sons.get(i).getSymbol() == sym) return i;
            }
            return -1;
        }
        public void removeSon(char sym) {
            int sonInd = this.findSon(sym);
            if (sonInd == -1) return;
            this.sons.remove(sonInd);
        }
        public void removeSon(int sonInd) {
            this.sons.remove(sonInd);
        }
        public void addSon(char sym) {
            if (this.findSon(sym) != -1) return;
            Dot newSon = new Dot(sym, this);
            sons.add(newSon);
        }
    }
    private int numberOfWords = 0;
    private final Dot root;

    public PrefixTree(){
        numberOfWords = 0;
        root = new Dot(' ', null);
    }

    public void add(String s) {
        Dot curr = root;
        for (int i = 0; i < s.length(); i++) {
            int sonInd = curr.findSon(s.charAt(i));
            if (sonInd == -1) {
                curr.addSon(s.charAt(i));
                sonInd = curr.findSon(s.charAt(i));
            }
            curr = curr.getSon(sonInd);
        }
        if(!curr.getWord()) numberOfWords++;
        curr.setWord(true);
    }

    private int[] find(String s) {
        Dot curr = root;
        int[] indexes = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            int sonInd = curr.findSon(s.charAt(i));
            if (sonInd == -1) {
                indexes = new int[0];
                return indexes;
            }
            indexes[i] = sonInd;
            curr = curr.getSon(sonInd);
        }
        if (!curr.getWord()) {
            indexes = new int[0];
            return indexes;
        }
        return indexes;
    }

    public void delete(String s) {
        int[] indexes = this.find(s);
        if (indexes.length == 0) return;
        Dot curr = root;
        Dot lastImportant = root; // самая "низкая" точка являющаяся словом, или имеющая другого сына
        int nextInd = indexes[0];
        for (int i = 0; i < indexes.length; i++) {
            if (curr.getWord() || curr.getSons().size() > 1) {
                lastImportant = curr;
                nextInd = indexes[i];
            }
            curr = curr.getSon(indexes[i]);
        }
        curr.setWord(false);
        if(curr.getSons().size() == 0)
            lastImportant.removeSon(nextInd);
        numberOfWords--;
    }
    private void addToPref(ArrayList<String> ans, StringBuilder word, Dot curr) {
        if(curr.getSymbol() != ' ') word.append(curr.getSymbol());
        if(curr.getWord()) ans.add(word.toString());
        //System.out.println(word);
        for(int i = 0; i < curr.getSons().size(); i++) {
            addToPref(ans, word, curr.getSon(i));
            word.deleteCharAt(word.length()-1);
        }

    }

    public String[] prefixWords(String pref) {
        Dot curr = root;
        ArrayList<String> ans = new ArrayList<String>(0);
        StringBuilder word = new StringBuilder(pref);
        for(int i = 0; i < pref.length(); i++){
            int sonInd = curr.findSon(pref.charAt(i));
            if(sonInd == -1) {
                String[] arr = new String[0];
                return ans.toArray(arr);
            }
            curr = curr.getSon(sonInd);
        }
        word.deleteCharAt(word.length()-1);
        addToPref(ans, word, curr);
        String[] arr = new String[ans.size()];
        return ans.toArray(arr);
    }

    public boolean contains(String s) {
        if(this.find(s).length == 0) return false;
        return true;
    }

    public int size(){
        return numberOfWords;
    }
}
