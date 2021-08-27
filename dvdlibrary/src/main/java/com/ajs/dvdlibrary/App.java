package com.ajs.dvdlibrary;

import com.ajs.dvdlibrary.controller.DvdLibraryController;
import com.ajs.dvdlibrary.dao.DvdLibraryDao;
import com.ajs.dvdlibrary.dao.DvdLibraryDaoImpl;
import com.ajs.dvdlibrary.ui.DvdLibraryView;
import com.ajs.dvdlibrary.ui.UserIO;
import com.ajs.dvdlibrary.ui.UserIOConsoleImpl;

/**
 * @author Austin Smith
 * date: 8/27/2021
 * This is the entry point to the DVD library
 *
 */
public class App 
{
	public static void main(String[] args) {
	    UserIO myIo = new UserIOConsoleImpl();
	    DvdLibraryView myView = new DvdLibraryView(myIo);
	    DvdLibraryDao myDao = new DvdLibraryDaoImpl();
	    DvdLibraryController controller =
	            new DvdLibraryController(myDao, myView);
	    controller.run();
	} 
}
