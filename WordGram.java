// import org.junit.*;

public class WordGram implements Comparable<WordGram> {
	
	private String[] myWords;
    private int myHash;
	
	public WordGram(String[] source, int start, int size) {
		
		myWords = new String[size];
		
		int index = 0;
		for (int i = start; i < start + size; i++) {
			myWords[index] = source[i];
			index++;
		}
		
		this.myHash = hashCode();
	}
	
	// public String[] myWords = {"dog","cat", "hat", "superman", "pablo", "person"};
	
	public int hashCode() {
		
		int hash = 0;
		
		for (int i = 0; i < myWords.length; i++) {
			
			String myWord = myWords[i];
			int stringHash = 0;
			
			for (int j = 0; j < myWord.length(); j++) {
				
				char myChar = myWord.charAt(j);
				int numVal = Character.getNumericValue(myChar);
				int result = 37 * numVal + j;
				stringHash = 37 * stringHash + result;
			}
		
			int result = 37 * stringHash + i;
			hash = 37 * hash + result;
		}
		
		// System.out.println(hash);
		
		return hash;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		
		for (String myWord: myWords) {
			sb.append("\"" + myWord + "\"" + ",");
		}
		
		sb.setLength(sb.length() - 1);
		sb.append("}");
		
		// System.out.println(sb.toString());
		
		return sb.toString();
	}
	
	public boolean equals(Object other) {
		
		if ( other == null || !(other instanceof WordGram)){
			// not same kind of object
			return false;
		} else if (other instanceof WordGram) {
			// same kind of object
			WordGram wg = (WordGram) other;
			
			boolean equals = true;
			
			// test each string to see if equal 
			for (int i = 0; i < myWords.length; i++) {
				if (this.myWords[i].equals(wg.myWords[i])) {
					continue;
				} else {
					equals = false;
				}
			}
			
			// if all elements same return equals
			if (equals == true) {
				return true;
			} else {
				return false;
			}
			
		} else {
			// everything else
			return false;
		}
	}
	
	public int compareTo(WordGram other) {
		
		int comparison = 0;
		
		int length;
		
		// find how many times to loop from limiting array
		if (myWords.length <= other.myWords.length) {
			length = myWords.length;
		} else {
			length = other.myWords.length;
		}
		
		if (myWords.length == 0 && other.myWords.length == 0) {
			return 0;
		} else if (myWords.length == 0) {
			return -1;
		} else if (other.myWords.length == 0) {
			return 1;
		} else {
		
			for (int i = 0; i < length; i++) {
				
				String str1 = myWords[i];
				String str2 = other.myWords[i];
				
					comparison = str1.compareTo(str2);
					if (comparison == 0) {
						continue;
					} else {
						return comparison;
					}
				
				/*
				String st1 = myWords[i];
				String st2 = other.myWords[i];
				
				for (int j = 0; j < 5; j++) {
					char char1 = st1.charAt(j);
					char char2 = st2.charAt(j);
				}
				*/
			}
		}
		
		if (myWords.length > other.myWords.length) {
			return 1;
		} else if (myWords.length < other.myWords.length) {
			return -1;
		} else {
			return 0;
		}
		
		// return comparison;
	}
	
	
	public WordGram shiftAdd(String last) {
		
		WordGram newWordGram = this;
		
		for (int i = 0; i < myWords.length - 1; i++) {
			myWords[i] = myWords[i + 1];
		}
		
		int indexLast = newWordGram.myWords.length - 1;
		newWordGram.myWords[indexLast] = last;
		
		return newWordGram;
	}
	
	public int length() {
		return myWords.length;
	}
	
}
