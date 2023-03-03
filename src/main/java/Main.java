public class Main {
    public static void main(String[] args) {
        PrefixTree tree = new PrefixTree();
        tree.add("aboba");
        tree.add("aboba");
        tree.add("boris");
        tree.add("abober");
        tree.add("abob");
        tree.add("abod");
        tree.add("adobe");
        tree.add("abobaaab");
        tree.add("a");
        tree.add("abobaac");
        //tree.delete("a");
        //tree.deleteAll("aboba");
        tree.delete("abobaaab");
        String[] arr = tree.prefixWords("abob");
        //System.out.println(tree.numberOfWords);
        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
            System.out.println(tree.contains(arr[i]));
        }
        System.out.println(tree.contains("abobaa"));
        //System.out.println(arr[1].charAt(0));
    }

}
