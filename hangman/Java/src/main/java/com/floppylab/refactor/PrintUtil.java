package com.floppylab.refactor;

import java.io.IOException;
import java.util.ArrayList;

public class PrintUtil {
	
	/** Print an ASCII art drawing's phase based on the current mistake count.
	 * 
	 * @param mistakeCount Define the phase of the drawing
	 */
	public static void printHangman(Integer mistakeCount) {
		printFromResource(mistakeCount);
	}

	private static void printFromResource(Integer mistakeCount) {
		
		String fileName = String.format("/ASCII_art/hangman%02d.txt", mistakeCount);

		try {
			ArrayList<String> hangmanArt = ResourceReader.readWords(fileName);
			for (String hangmanArtRow: hangmanArt) {
				System.out.println(hangmanArtRow);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void printWord(String word, boolean[] isLetterGuessed) {
		System.out.print("The word: ");
		for (int i = 0; i < word.length(); ++i) {
			if (isLetterGuessed[i]) {
				System.out.print(" " + word.charAt(i) + " ");
			} else {
				System.out.print(" _ ");
			}
		}
		System.out.append("\n\n");
	}
	
	public static void printFinalResult(boolean isWin, String word) {
		if (isWin) {
			System.out.print("You won!\n");
		} else {
			System.out.print("You lost.\n");
			System.out.println("The word was: " + word);
		}
	}
	
}
