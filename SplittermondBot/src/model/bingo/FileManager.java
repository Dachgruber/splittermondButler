package model.bingo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.HashMap;

public class FileManager {

	@SuppressWarnings("unchecked")
	public boolean saveFile(HashMap<String, BingoItem> hm) {
		
		try(FileOutputStream fileOut =new FileOutputStream("./saves/test.ser"); //Dateiname und Pfad
	            ObjectOutputStream out = new ObjectOutputStream(fileOut);)
	        {
	            out.writeObject(hm);
	        }
	        catch (IOException i) {
	            i.printStackTrace();
	            return false;
	        }
		System.out.println("Saved a GameFile.");
        return true;
	}
    
	
	
	public HashMap<String,BingoItem> loadFile() throws FileNotFoundException, IOException, ParseException {
		HashMap<String,BingoItem> hm = null;

        try(FileInputStream fileIn = new FileInputStream("./saves/test.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);)
        {
            hm = (HashMap<String,BingoItem>) in.readObject();
            System.out.println("Loaded a saved GameFile.");
        }
        catch (IOException i){
            i.printStackTrace();
        }
        catch (ClassNotFoundException c){
            c.printStackTrace();
        }
        
        return hm;
    }
}
