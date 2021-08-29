package com.ajs.dvdlibrary.ui;

import java.util.List;

import com.ajs.dvdlibrary.dto.Dvd;
import com.ajs.dvdlibrary.dao.DvdLibraryDao;
import com.ajs.dvdlibrary.dao.DvdLibraryDaoException;
import com.ajs.dvdlibrary.dao.DvdLibraryDaoImpl;



public class DvdLibraryView {

    private UserIO io;
    private DvdLibraryDao dao;

    
    public DvdLibraryView(UserIO io, DvdLibraryDao dao) {
        this.io = io;
        this.dao = dao;
    }
    
    
    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. Add DVD");
        io.print("2. Remove DVD");
        io.print("3. Edit DVD");
        io.print("4. List DVD");
        io.print("5. Search By Title");
        io.print("6. Exit");

        return io.readInt("Please select from the above choices.", 1, 6);
    }
    
    
    public Dvd getNewDvdInfo() throws DvdLibraryDaoException {
        String title = io.readString("Please enter Dvd Title");
        String releaseDate = io.readString("Please enter Release Date");
        String mpaaRating = io.readString("Please enter MPAA Rating");
        String directorName = io.readString("Please enter Director Name");
        String studio = io.readString("Please enter Studio");
        String userNote = io.readString("Please enter User Note / Rating");
        Dvd currentDvd = new Dvd(dao.generateId());
        currentDvd.setTitle(title);
        currentDvd.setReleaseDate(releaseDate);
        currentDvd.setMpaaRating(mpaaRating);
        currentDvd.setDirectorName(directorName);
        currentDvd.setStudio(studio);
        currentDvd.setUserNote(userNote);
        return currentDvd;
    }
    
    public void displayCreateDvdBanner() {
        io.print("=== Create DVD ===");
    }
    
    public void displayCreateSuccessBanner() {
        io.readString(
                "DVD successfully created.  Please hit enter to continue");
    }
    
    public void displayDvdList(List<Dvd> DvdList) {
        for (Dvd currentDvd : DvdList) {
            String dvdInfo = String.format("DVD : %s : %s %s",
                  currentDvd.getTitle(),
                  currentDvd.getReleaseDate(),
                  currentDvd.getMpaaRating(),
                  currentDvd.getDirectorName(),
                  currentDvd.getStudio(),
                  currentDvd.getUserNote());
            io.print(dvdInfo);
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayDisplayAllBanner() {
        io.print("=== Display All DVDs ===");
    }
    
    public void displayDisplayDvdBanner () {
        io.print("=== Display DVD ===");
    }

    public String getDvdTitleChoice() {
        return io.readString("Please enter the DVD title.");
    }

    public void displayDvd(Dvd dvd) {
        if (dvd != null) {
            io.print(dvd.getTitle());
            io.print(dvd.getReleaseDate());
            io.print(dvd.getMpaaRating());
            io.print("");
        } else {
            io.print("No such DVD.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayRemoveDvdBanner () {
        io.print("=== Remove DVD ===");
    }
    
    public String displayPickDvdBanner() {
    	return io.readString("Enter the key of the dvd you would like to remove.");
    }

    public void displayRemoveResult(Dvd dvd) {
        if(dvd != null){
          io.print("DVD successfully removed.");
        }else{
          io.print("No such DVD.");
        }
        io.readString("Please hit enter to continue.");
    }
    public void displayEditDvdBanner () {
        io.print("=== Edit DVD ===");
    }
    public void displayEditSuccessBanner() {
        io.readString(
                "DVD successfully edited.  Please hit enter to continue");
    }
    
    
    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
}

