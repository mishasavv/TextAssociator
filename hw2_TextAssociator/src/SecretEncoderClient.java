import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


/* CSE 373 Fall 2015 starter code
 * @Author: Kevin Quinn
 * 
 * ThesaurusClient takes a text file of synonyms for words 
 * as specified by THESAURUS_FILE and input from standard.in and
 * outputs the spruced up version of the user's input by randomly selecting
 * synonyms for all input words that have synonyms.
 * 
 * This Client program is dependent on TextAssociator
 */
public class SecretEncoderClient {
	
	//Path to desired thesaurus file to read
	public final static String CODE_FILE = "secret_code.txt";
	
	public static void main(String[] args) throws IOException {
		File file = new File(CODE_FILE);
		
		//Create new empty TextAssociator
		TextAssociator sc = new TextAssociator();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String text = null;
		while ((text = reader.readLine()) != null) {
			String[] letters = text.split(",");
			String currentLetter = letters[0].trim();
			sc.addNewWord(currentLetter);
			for (int i = 1; i < letters.length; i++) {
				sc.addAssociation(currentLetter, letters[i].trim());
			}
		}
		Scanner scanner= new Scanner(System.in);
		String inputString = "";
		Random rand = new Random();
		while (true) {
			String result = "";
			String delimit = ",";
			System.out.print("Enter \"encode\" or \"decode\" to encode or decode text. (type \"exit\" to quit):");
			inputString = scanner.nextLine();
			if (inputString.equals("encode")) {
				System.out.print("Enter text to be encoded. (type \"exit\" to quit):");
				inputString = scanner.nextLine();
				String[] letters  = inputString.split("");
				for (String letter : letters) {
					String lowerLetter = letter.toLowerCase();
					Set<String> code = sc.getAssociations(lowerLetter);
					if (code == null) {
						result += letter + delimit;
					} else {
						result += code.toArray()[rand.nextInt(code.size())] + delimit;
					}
				}
				System.out.println(result.trim());
				System.out.println();
			} else if(inputString.equals("decode")) {
				System.out.print("Enter text to be decoded. (type \"exit\" to quit):");
				inputString = scanner.nextLine();
				String[] codes  = inputString.split(delimit);
				for (String code : codes) {
					String lowerLetter = code.toLowerCase();
					
					
					Set<String> code = sc.getAssociations(lowerLetter);
					if (code == null) {
						result += letter;
					} else {
						result += code.toArray()[rand.nextInt(code.size())];
					}
				}
				System.out.println(result.trim());
				System.out.println();
			} else {
				break;
			}	
		}
		reader.close();
	}
	private boolean findAssocitaion(){
		return false;
	}
}
