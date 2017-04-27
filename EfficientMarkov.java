import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;
// import static org.junit.Assert.*;
// import org.junit.*;   

public class EfficientMarkov implements MarkovInterface<String> {
	
	public TreeMap<String, ArrayList<String>> markovMap = new TreeMap<String, ArrayList<String>>();
	
	
	private String myText;
	private Random myRandom;
	private int myOrder;
	
	private static String PSEUDO_EOS = "";
	private static int RANDOM_SEED = 1234;
	
	public EfficientMarkov(int order) {
		myRandom = new Random(RANDOM_SEED);
		myOrder = order;
	}
	
	public void setTraining(String text) {
		
		this.myText = text;
		
		//String text = "acbcacbccbbabcabcbbcbcaaacacabc";
		
		for (int i = 0; i <= text.length() - myOrder; i++) {	
			
			
			// Create key according to index and myOrder
			String key = text.substring(i, i + myOrder);
			String value;
			
			// check if value should be next char or PSEUDO_EOS
			if (i == text.length() - myOrder) {
				value = PSEUDO_EOS;
			} else {
				value = text.substring(i + myOrder, i + myOrder + 1);
			}
			
			// if markovMap doesn't contain the key, create it
			// or else, add the value to the key
			if (! markovMap.containsKey(key)) {
				ArrayList<String> valueList = new ArrayList<String>();
				valueList.add(value);
				markovMap.put(key, valueList);
			} else {
				ArrayList<String> updatedValue = markovMap.get(key);
				updatedValue.add(value);
				markovMap.put(key, updatedValue);
			}
			// System.out.printf("Key: %s Value: %s%n", key, value);	
		}
		// System.out.println(markovMap.toString());
	}

	public String getRandomText(int length) {
		StringBuilder sb = new StringBuilder();
		// System.out.println(myRandom.nextInt(myText.length() - myOrder));
		int index = myRandom.nextInt(myText.length() - myOrder);
		
		String current = myText.substring(index, index + myOrder);
		//System.out.printf("first random %d for '%s'\n",index,current);
		sb.append(current);
		for(int k=0; k < length-myOrder; k++){
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
			sb.append(nextItem);
			current = current.substring(1)+ nextItem;
		}
		return sb.toString();
	}

	public ArrayList<String> getFollows(String key) {
		return markovMap.get(key);
	}

	public int getOrder() {
		return myOrder;
	}

}
