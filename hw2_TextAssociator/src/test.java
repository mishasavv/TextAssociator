
public class test {
	
	public static void main(String[] args) {
		TextAssociator textAssociator = new TextAssociator();
		int array = textAssociator.locateArrayPosition("elephant");
		System.out.println(array);
		System.out.println(textAssociator.addNewWord("elephant"));
		System.out.println(textAssociator.addNewWord("elephant"));
		System.out.println(textAssociator.addAssociation("elephant","large"));
		System.out.println(textAssociator.addAssociation("elephant","large"));
		System.out.println(textAssociator.addAssociation("elephant","big"));
		System.out.println(textAssociator.getAssociations("elephant"));		
		System.out.println(textAssociator.remove("elephant"));
		System.out.println(textAssociator.remove("elephant"));
		System.out.println(textAssociator.getAssociations("elephant"));
	}
	
}
