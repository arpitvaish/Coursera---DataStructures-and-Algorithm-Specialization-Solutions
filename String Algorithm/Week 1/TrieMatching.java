
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TrieMatching {

	public static class TrieNode {
		public Map<Character, TrieNode> children = new HashMap<>();
		public int size = 0;

		public void putChildrenIfAbsent(Character ch) {
			children.putIfAbsent(ch, new TrieNode());
		}

		public TrieNode getChild(Character ch) {
			return children.get(ch);
		}

		@Override
		public String toString() {
			return "TrieNode [children=" + children + ", size=" + size + "]";
		}

	}

	public static class Trie {

		public TrieNode root = new TrieNode();

		Trie() {

		}

		Trie(String[] words) {
			for (String word : words) {
				add(word);
			}
		}

		public void add(String word) {
			TrieNode curr = root;
			for (int i = 0; i < word.length(); i++) {
				Character ch = word.charAt(i);
				curr.putChildrenIfAbsent(ch);
				curr = curr.getChild(ch);
				curr.size++;
			}
		}

		public int find(String word) {
			TrieNode curr = root;
			for (int i = 0; i < word.length(); i++) {
				Character ch = word.charAt(i);
				curr = curr.getChild(ch);
				if (curr == null) {
					return 0;
				}
			}
			return curr.size;
		}
	}

	public static Scanner sc = new Scanner(System.in);

	public static List<Integer> search(Trie trie, String text) {
		TrieNode curr = trie.root;
		int index = 0;
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < text.length(); i++) {
			curr = trie.root;
			index = i;
			while (index < text.length()) {
				Character ch = text.charAt(index);
				if (curr.children.keySet().contains(ch)) {
					curr = curr.getChild(ch);
					if (curr.children.isEmpty()) {
						result.add(i);
					}
					index++;
				} else {
					break;
				}
			}
		}

		return result;
	}

	public static void main(String[] args) {
		String text = sc.next();
		int testCases = sc.nextInt();
		List<Integer> ans = new ArrayList<>();
		List<String> patterns = new ArrayList<>();
		Trie trie = new Trie();
		for (int i = 0; i < testCases; i++) {
			trie.add(sc.next());
		}

		ans = search(trie, text);
		for (int j = 0; j < ans.size(); j++) {
			System.out.print("" + ans.get(j));
			System.out.print(j + 1 < ans.size() ? " " : "\n");
		}

	}

}
