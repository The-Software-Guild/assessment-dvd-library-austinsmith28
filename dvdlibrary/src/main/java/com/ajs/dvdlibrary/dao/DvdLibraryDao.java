package com.ajs.dvdlibrary.dao;

import java.util.List;

import com.ajs.dvdlibrary.dto.Dvd;


public interface DvdLibraryDao {
	
	String generateId()
	throws DvdLibraryDaoException;
    /**
     * Adds the given DVD to the library and associates it with the given 
     * title. If there is already a DVD associated with the given 
     * title it will return that DVD object, otherwise it will 
     * return null.
     * 
     * @param title with which DVD is to be associated
     * @param Dvd dvd to be added to the roster
     * @return the Dvd object previously associated with the given  
     * title if it exists, null otherwise
     * @throws DvdLibraryDaoException
     */
    Dvd addDvd(String title, Dvd dvd)
     throws DvdLibraryDaoException;

    /**
     * Returns a List of all DVDs in the library. 
     * 
     * @return DVD List containing all dvds in the library.
     * @throws DvdLibraryDaoException
     */
    List<Dvd> getAllDvds()
     throws DvdLibraryDaoException;

    /**
     * Returns the dvd object associated with the given title.
     * Returns null if no such dvd exists
     * 
     * @param title ID of the dvd to retrieve
     * @return the DVD object associated with the given title,  
     * null if no such dvd exists
     * @throws DvdLibraryDaoException
     */
    Dvd getDvd(String title)
     throws DvdLibraryDaoException;

    /**
     * Removes from the library the dvd associated with the given title. 
     * Returns the dvd object that is being removed or null if 
     * there is no dvd associated with the given title
     * 
     * @param title id of dvd to be removed
     * @return Dvd object that was removed or null if no dvd 
     * was associated with the given title
     * @throws DvdLibraryDaoException
     */
    
    
   
public Dvd removeThisDvd(String choice) 
	throws DvdLibraryDaoException;
    
public List<Dvd> removeDvd(String title)
     throws DvdLibraryDaoException;
}
