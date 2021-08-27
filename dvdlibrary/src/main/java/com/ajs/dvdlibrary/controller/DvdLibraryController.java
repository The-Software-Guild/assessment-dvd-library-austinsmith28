package com.ajs.dvdlibrary.controller;

import java.util.List;

import com.ajs.dvdlibrary.dao.DvdLibraryDao;
import com.ajs.dvdlibrary.dao.DvdLibraryDaoException;
import com.ajs.dvdlibrary.dto.Dvd;
import com.ajs.dvdlibrary.ui.DvdLibraryView;
import com.ajs.dvdlibrary.ui.UserIO;
import com.ajs.dvdlibrary.ui.UserIOConsoleImpl;



public class DvdLibraryController{

    private DvdLibraryView view;
    private DvdLibraryDao dao;
    private UserIO io = new UserIOConsoleImpl();
    
    
    public DvdLibraryController(DvdLibraryDao dao, DvdLibraryView view) {
        this.dao = dao;
        this.view = view;
    }

    
    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        createDvd();
                        break;
                    case 2:
                    	removeDvd();
                        break;
                    case 3:
                        editDvd();
                        break;
                    case 4:
                    	listDvds();
                        break;
                    case 5:
                    	viewDvd();
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
            exitMessage();
        } catch (DvdLibraryDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void createDvd() throws DvdLibraryDaoException {
        view.displayCreateDvdBanner();
        Dvd newDvd = view.getNewDvdInfo();
        dao.addDvd(newDvd.getTitle(), newDvd);
        view.displayCreateSuccessBanner();
    }

    private void listDvds() throws DvdLibraryDaoException {
        view.displayDisplayAllBanner();
        List<Dvd> dvdList = dao.getAllDvds();
        view.displayDvdList(dvdList);
    }

    private void viewDvd() throws DvdLibraryDaoException {
        view.displayDisplayDvdBanner();
        String dvdTitle = view.getDvdTitleChoice();
        Dvd dvd = dao.getDvd(dvdTitle);
        view.displayDvd(dvd);
    }

    private void removeDvd() throws DvdLibraryDaoException {
        view.displayRemoveDvdBanner();
        String dvdTitle = view.getDvdTitleChoice();
        Dvd removedDvd = dao.removeDvd(dvdTitle);
        view.displayRemoveResult(removedDvd);
    }
    
    private void editDvd() throws DvdLibraryDaoException {
    	view.displayEditDvdBanner();
    	String dvdTitle = view.getDvdTitleChoice();
    	Dvd removedDvd = dao.removeDvd(dvdTitle);
    	view.displayRemoveResult(removedDvd);
    	Dvd newDvd = view.getNewDvdInfo();
    	dao.addDvd(newDvd.getTitle(), newDvd);
    	view.displayEditSuccessBanner();
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

}
