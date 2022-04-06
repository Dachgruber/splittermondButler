package model.bingo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.Normalizer;
import java.text.ParseException;
import java.util.HashMap;

/**
 * The FileManager class handles loading and saving HashMaps to a specific file
 * path. It is used to save the state of the BingoTable (with added items,
 * activation/deactivation of the items etc)
 *
 * The current FileManager uses the Java-Serialisation approach, which sadly
 * excludes save transferring over different versions. Currently mainly a copy
 * from SpuelMetts JDA-Textadventure-Bot
 * https://github.com/SpuelMett/Discord-Text-Adventure
 *
 * @author Cornelius, SpuelMett
 *
 */
public class FileManager {

	// use this for both loading and saving, so that only one save slot is forced
	private static final String SAVEFILEPATH = "./saves/bingotest.ser";
	private static final String TXTFILEPATH = "./saves/bingotest.txt";

	/**
	 * saves a HashMap to a determined file location. Only one save is currently
	 * supported, so saving different HashMaps will override eatchother
	 *
	 * @param hm HashMap with Integer/BingoItem configuration
	 * @return boolean was the save successful?
	 */
	@SuppressWarnings("unchecked")
	public boolean saveFileToDisk(HashMap<Integer, BingoItem> hm) {

		try (FileOutputStream fileOut = new FileOutputStream(FileManager.SAVEFILEPATH); // Dateiname und Pfad
				ObjectOutputStream out = new ObjectOutputStream(fileOut);) {
			out.writeObject(hm);
		} catch (IOException i) {
			i.printStackTrace();
			return false;
		}
		System.out.println("Saved a GameFile.");
		return true;
	}

	/**
	 * loads a HashMap from the determined file location. Loading a save will not
	 * modify the save file, so multiple loading operations of the same file are
	 * theoretically possible.
	 *
	 * @return HashMap with Integer/BingoItem configuration
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */

	public HashMap<Integer, BingoItem> loadFileFromDisk() throws FileNotFoundException, IOException, ParseException {
		HashMap<Integer, BingoItem> hm = null;

		try (FileInputStream fileIn = new FileInputStream(FileManager.SAVEFILEPATH);
				ObjectInputStream in = new ObjectInputStream(fileIn);) {
			hm = (HashMap<Integer, BingoItem>) in.readObject();
			System.out.println("Loaded a saved GameFile.");
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
		}

		return hm;
	}

	/**
	 * loads the txt-file from a determined filepath and builds a StringArray from
	 * the content. Used to load a preset txt into the software.
	 *
	 * @return StringArray with every line of the txt.file in their own element
	 * @throws IOException
	 */
	
	public String[] loadFileFromTxt() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(FileManager.TXTFILEPATH));
		String line = br.readLine();
		StringBuilder sb = new StringBuilder();

		while (line != null) {
			if (!line.startsWith("//"))
				sb.append(line).append("");
			
			line = br.readLine();
		}

		return sb.toString().replaceAll("\n","").split(";");

	}
	
	/**
	 * Changing the german umlauts to something the console doesnt get a stroke from. 
	 * Converting ä->ae,ü->ue etc
	 * @param inputString
	 * @return normalized String
	 */
	//currently unused as ANSI encoding works with the txt, may be used in the future tho
	private String replaceGermanChars(String inputString) {
		String newString = inputString.replace("\u00fc", "ue") 					// ü -> ue 
	            .replace("\u00f6", "oe")										// ö -> oe
	            .replace("\u00e4", "ae")										// ä -> ae
	            .replace("\u00df", "ss")										// ß -> ss
	            .replaceAll("\u00dc(?=[a-z\u00e4\u00f6\u00fc\u00df ])", "Ue")	// Ü gefolgt vom kleinem Buchstaben -> Ue
	            .replaceAll("\u00d6(?=[a-z\u00e4\u00f6\u00fc\u00df ])", "Oe")	// Ä gefolgt vom kleinem Buchstaben -> Ae
	            .replaceAll("\u00c4(?=[a-z\u00e4\u00f6\u00fc\u00df ])", "Ae")	// Ö gefolgt vom kleinem Buchstaben -> Oe
	            .replace("\u00dc", "UE")										// Ü -> UE
	            .replace("\u00d6", "OE")										// Ö -> OE
	            .replace("\u00c4", "AE");										// Ä -> AE
	    return newString;
	}
}
