
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// @RunWith attaches a runner with the test class to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class DictionaryTester {

	private Dictionary dictionary;
	private DictionaryService dictService;
	List<String> dictionaryList;
	String enteredWord = "";
	String newWord = "";

	@Before
	public void setUp() {
		dictionary = new Dictionary();
		dictService = mock(DictionaryService.class);
		dictionary.setDictionaryService(dictService);

		// I'm adding a mocked dictionary here
		when(dictService.getDictionary()).thenReturn(createDictionaryArray());
		dictionaryList = dictService.getDictionary();
	}

	/**
	 * Create String list based on the Dictionary file (EnglishWords in this case)
	 * to mock the dictionary service
	 * 
	 * @return String list with the dictionary content
	 */
	static List<String> createDictionaryArray() {
		List<String> listDictionary = new ArrayList<String>();
		BufferedReader reader;

		try {
			ClassLoader loader = DictionaryTester.class.getClassLoader();
			File file = new File(loader.getResource("EnglishWords").getFile());
			reader = new BufferedReader(new FileReader(file));
			// reader = new BufferedReader(new FileReader("EnglishWords"));
			String line = reader.readLine();
			while (line != null) {
				listDictionary.add(line);
				line = reader.readLine(); // read next line
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listDictionary;
	}

	/**
	 * Validate all all possible words that existing in the dictionary (EnglishWords
	 * in this case) to mock the isEnglishWord function
	 * 
	 */

	@Test
	public void validateWorkingWord() {
		when(dictService.isEnglishWord("working")).thenReturn(isThisEnglish("working"));
		Assert.assertTrue(dictionary.isEnglishWord("working"));
		// Print all possible words
		dictionary.findPossibleWords("working");
		
	}

	/**
	 * Validate if given word exists in the dictionary (EnglishWords in this case)
	 * to mock the isEnglishWord function
	 * 
	 * @param word
	 * @return word
	 */

	public String enterWord() {
		Scanner myObj = new Scanner(System.in); // Create a Scanner object
		System.out.println("");
		System.out.println("Enter English word");

		String word = myObj.nextLine(); // Read user input
		System.out.println("The word you entered is: " + word); // Output user input
		String removedchars = removeSpaces(word);

		return removedchars;

	}

	public boolean isThisEnglish(String word) {

		for (String w : dictionaryList) {
			if (w.equals(word.toLowerCase())) {
				System.out.println(word + " is a valid english word");
				return true;
			}
			if(word.isEmpty()) {
				
				System.out.println("The word does not exist in dictionary");
				break;
			}
		}
		return false;
	}

	@Test
	public void validateWord() {
		enteredWord = enterWord();
		when(dictService.isEnglishWord(enteredWord)).thenReturn(isThisEnglish(enteredWord));
		Assert.assertTrue(dictionary.isEnglishWord(enteredWord));  //Assert will stop execution for empty or null word output.

		List<String> listWords = createDictionaryArray();

		for (int c = 1; c < listWords.size(); c++) {
			if (enteredWord.contains(listWords.get(c))) {

				System.out.println("Word is: " + listWords.get(c));

			}

		}
		

		

	}
	/*
	 * 
	 * removeSpace method will remove spaces in the given String' We have to go
	 * through each char and convert it to String otherwise 'If' condition will not
	 * work.
	 */

	public String removeSpaces(String found) {
		String removedcharsWord = "";
		String wrd;
		for (int l = 0; l < found.length(); l++) {
			wrd = String.valueOf(found.charAt(l));
			if (!wrd.equals(" ")) {
				removedcharsWord += wrd;

			}
			

		}
		String removedW = removedcharsWord.replaceAll("[^a-zA-Z0-9]", "");

		return removedW;
	}
}
