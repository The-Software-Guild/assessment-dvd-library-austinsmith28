package com.ajs.dvdlibrary.dao;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.ajs.dvdlibrary.dto.Dvd;



public class DvdLibraryDaoImpl implements DvdLibraryDao {
	
	private Map<String, Dvd> dvds = new HashMap<>();
	public static final String LIBRARY_FILE = "library.txt";
	public static final String DELIMITER = "::";
	
	
	@Override
	public Dvd addDvd(String Title, Dvd dvd) throws DvdLibraryDaoException {
	    loadLibrary();
	    Dvd newStudent = dvds.put(Title, dvd);
	    writeLibrary();
	    return newStudent;
	}

	@Override
	public List<Dvd> getAllDvds() throws DvdLibraryDaoException {
		loadLibrary();
	    return new ArrayList(dvds.values());
	}

	@Override
	public Dvd getDvd(String title) throws DvdLibraryDaoException {
		loadLibrary();
	    return dvds.get(title);
	}
	
	@Override
	public Dvd removeDvd(String title) throws DvdLibraryDaoException {
		loadLibrary();
	    Dvd removedStudent = dvds.remove(title);
	    writeLibrary();
	    return removedStudent;
	}
    
    private Dvd unmarshallDvd(String dvdAsText){
        // dvdAsText is expecting a line read in from our file.
        // For example, it might look like this:
        // Avatar::12/18/2009::PG-13::James Cameron::Lightstorm Entertainment::good sci-fi movie
        //
        // We then split that line on our DELIMITER - which we are using as ::
        // Leaving us with an array of Strings, stored in dvdTokens.
        // Which should look like this:
        // _________________________________________________________________________________
        // |      |          |     |             |                        |                 |
        // |Avatar|12/18/2009|PG-13|James Cameron|Lightstorm Entertainment|good sci-fi movie|
        // |      |          |     |             |                        |                 | 
        // ---------------------------------------------------------------------------------
        //  [0]       [1]      [2]      [3]                 [4]                  [5]
        String[] dvdTokens = dvdAsText.split(DELIMITER);

        // Given the pattern above, the title is in index 0 of the array.
        String title = dvdTokens[0];

        // Which we can then use to create a new Dvd object to satisfy
        // the requirements of the Dvd constructor.
        Dvd dvdFromFile = new Dvd(title);

        // However, there are 3 remaining tokens that need to be set into the
        // new dvd object. Do this manually by using the appropriate setters.

        // Index 1 - Release Date
        dvdFromFile.setReleaseDate(dvdTokens[1]);

        // Index 2 - MPAA Rating
        dvdFromFile.setMpaaRating(dvdTokens[2]);

        // Index 3 - Director Name
        dvdFromFile.setDirectorName(dvdTokens[3]);
      
        // Index 4 - Studio
        dvdFromFile.setStudio(dvdTokens[4]);
        
        // Index 5 - User Note / Rating
        dvdFromFile.setUserNote(dvdTokens[5]);

        // We have now created a dvd! Return it!
        return dvdFromFile;
    }
    
    private void loadLibrary() throws DvdLibraryDaoException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(LIBRARY_FILE)));
        } catch (FileNotFoundException e) {
            throw new DvdLibraryDaoException(
                    "-_- Could not load library data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentDvd holds the most recent student unmarshalled
        Dvd currentDvd;
        // Go through LIBRARY_FILE line by line, decoding each line into a 
        // Dvd object by calling the unmarshallDvd method.
        // Process while we have more lines in the file
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a Dvd
            currentDvd = unmarshallDvd(currentLine);

            // We are going to use the title as the map key for our dvd object.
            // Put currentDvd into the map using title as the key
            dvds.put(currentDvd.getTitle(), currentDvd);
        }
        // close scanner
        scanner.close();
    }
    
    private String marshallDvd(Dvd aDvd){
        // We need to turn a Dvd object into a line of text for our file.
        // For example, we need an in memory object to end up like this:
        // Avatar::12/18/2009::PG-13::James Cameron::Lightstorm Entertainment::good sci-fi movie

        // It's not a complicated process. Just get out each property,
        // and concatenate with our DELIMITER as a kind of spacer. 

        // Start with the title, since that's supposed to be first.
        String dvdAsText = aDvd.getTitle() + DELIMITER;

        // add the rest of the properties in the correct order:

        // Release Date
        dvdAsText += aDvd.getReleaseDate() + DELIMITER;

        // MPAA Rating
        dvdAsText += aDvd.getMpaaRating() + DELIMITER;
        
        // Director Name
        dvdAsText += aDvd.getDirectorName() + DELIMITER;
        
        // Studio
        dvdAsText += aDvd.getStudio() + DELIMITER;

        // User Note - don't forget to skip the DELIMITER here.
        dvdAsText += aDvd.getUserNote();

        // We have now turned a dvd to text! Return it!
        return dvdAsText;
    }
    
    /**
     * Writes all dvds in the library out to a LIBRARY_FILE.  See loadRoster
     * for file format.
     * 
     * @throws DvdLibraryDaoException if an error occurs writing to the file
     */
    private void writeLibrary() throws DvdLibraryDaoException {
        // NOTE FOR APPRENTICES: We are not handling the IOException - but
        // we are translating it to an application specific exception and 
        // then simple throwing it (i.e. 'reporting' it) to the code that
        // called us.  It is the responsibility of the calling code to 
        // handle any errors that occur.
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(LIBRARY_FILE));
        } catch (IOException e) {
            throw new DvdLibraryDaoException(
                    "Could not save DVD data.", e);
        }

        // Write out the Dvd objects to the library file.
        // NOTE TO THE APPRENTICES: We could just grab the dvd map,
        // get the Collection of Dvds and iterate over them but we've
        // already created a method that gets a List of Dvds so
        // we'll reuse it.
        String dvdAsText;
        List<Dvd> dvdList = this.getAllDvds();
        for (Dvd currentDvd : dvdList) {
            // turn a Dvd into a String
            dvdAsText = marshallDvd(currentDvd);
            // write the Dvd object to the file
            out.println(dvdAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }

}

