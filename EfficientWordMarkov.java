import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class EfficientWordMarkov implements MarkovInterface<WordGram> {

public HashMap<WordGram, ArrayList<String>> markovMap = new HashMap<WordGram, ArrayList<String>>();
	
	
	private String myText;
	private String[] myTextArray;
	private Random myRandom;
	private int myOrder;
	
	private static String PSEUDO_EOS = "";
	private static int RANDOM_SEED = 1234;
	
	public EfficientWordMarkov(int order) {
		myRandom = new Random(RANDOM_SEED);
		myOrder = order;
	}
	
	public int size() {
		return myText.length();
	}
	
	public void setTraining(String text) {
		
		this.myText = text;
		myTextArray = text.split("\\s+");
		
		for (int i = 0; i <= myTextArray.length - myOrder; i++) {
			
			// Create key according to index and myOrder
			WordGram key = new WordGram(myTextArray, i, myOrder);
			String value;
			
			// check if value should be next word or PSEUDO_EOS
			if (i == myTextArray.length - myOrder) {
				value = PSEUDO_EOS;
			} else {
				value = myTextArray[i + myOrder];
			}
			
			// if markovMap doesn't contain the key, create it
			// or else, add the value to the key
			if (! markovMap.containsKey(key)) {
				ArrayList<String> valueList = new ArrayList<String>();
				valueList.add(value);
				markovMap.put(key, valueList);
			} else {
				ArrayList<String> updatedValueList = markovMap.get(key);
				updatedValueList.add(value);
				markovMap.put(key, updatedValueList);
			}
		}
	}
	
	public String getRandomText(int numWords) {
		
		StringBuilder sb = new StringBuilder();
		// System.out.println(myRandom.nextInt(myText.length() - myOrder));
		// int index = myRandom.nextInt(myText.length() - myOrder);
		
		// String current = myText.substring(index, index + myOrder);
		
		int index = myRandom.nextInt(myTextArray.length - myOrder);
		// String[] current = Arrays.copyOfRange(myTextArray, index, index + myOrder);
		WordGram current = new WordGram(myTextArray, index, myOrder);
		
		System.out.printf("first random %d for '%s'\n",index,current.toString());
		sb.append(current.toString());
		for(int k=0; k < numWords-myOrder; k++){
			ArrayList<String> follows = getFollows(current);
			if (follows.size() == 0){
				break;
			}
			index = myRandom.nextInt(follows.size());
			
			String nextItem = follows.get(index);
			if (nextItem.equals(PSEUDO_EOS)) {
				//System.out.println("PSEUDO");
				break;
			}
			
			sb.append(" ");
			sb.append(nextItem);
			
			current = current.shiftAdd(nextItem);
		}
		
		return sb.toString();
	}
	
	public ArrayList<String> getFollows(WordGram key) {
		return markovMap.get(key);
	}
	
	public int getOrder() {
		return myOrder;
	}
}
