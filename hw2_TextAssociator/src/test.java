
public class test {
	
	public static void main(String[] args) {
		TextAssociator textAssociator = new TextAssociator();
		int array = textAssociator.locateArrayPosition("elephant");
		System.out.println(array);
		System.out.println(textAssociator.addNewWord("elephant"));
		System.out.println(textAssociator.addNewWord("elephant"));
	}
	
}
