/*
 * Jeremiah Hobbs
 * CPSC 5011, Seattle University
 * This is free and unencumbered software released into the public domain.
 */

import vault.PasswordVault;

/**
 * Driver that tests functionality for Jeremiah's HW2. Tests all methods
 * of PasswordVault.
 */
public class Driver {

	public static void main(String[] args) {
			PasswordVault vault = new PasswordVault();
				try {
					System.out.println("**TESTING SUCCESS CASES**\n");
					//Basic functionality of adding users, sites, password
					// retrieval, and updates.
					vault.addNewUser("jeremiah", "bigjerm1&");
					vault.addNewSite("jeremiah", "bigjerm1&",
							"amazon");
					vault.addNewUser("sarahsmith",
							"jeremiah&");
					vault.addNewSite("sarahsmith",
							"jeremiah&",
							"amazon");
					vault.retrieveSitePassword("jeremiah",
							"bigjerm1&", "amazon");
					vault.updateSitePassword("jeremiah",
							"bigjerm1&", "amazon");
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
				System.out.println("**TESTING INVALID PASSWORD " +
						"AND INVALID SITE**\n");
				//First Error. Password Mismatch
				try{
					vault.updateSitePassword("jeremiah",
							"bsjbmcsaas", "amazon");
				}
				catch (Exception e){
					System.out.println("Error: " + e.getMessage());
				}
				//Second Error. Invalid Site
				try{
					vault.updateSitePassword("jeremiah",
							"bigjerm1&", "google");
				}
				catch (Exception e){
					System.out.println("Error: " + e.getMessage());
				}
				System.out.println("**TESTING DUPLICATE USER**\n");
				//Third Error. Duplicate User
				try{
					vault.addNewUser("jeremiah", "bigjerm");
				}
				catch (Exception e){
					System.out.println("Error: " + e.getMessage());
				}
				//Fourth Error. Site Not Found
				System.out.println("**TESTING SITE NOT FOUND AND DUPLICATE " +
						"SITE**\n");
				try{
					vault.updateSitePassword("jeremiah",
							"bigjerm1&", "facebook");
				}
				catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
				//Fifth Error. Duplicate Site
				try{
					vault.addNewSite("jeremiah",
							"bigjerm1&","amazon");
				}
				catch (Exception e){
					System.out.println("Error: " + e.getMessage());
				}
				System.out.println("**TESTING INVALID USER, USER NOT FOUND," +
						"AND INVALID PASSWORD**\n");
				//Sixth Error. Invalid Username
				try{
					vault.addNewUser("JOHNSMITH",
							"bigjerm1&");
				}
				catch (Exception e){
					System.out.println("Error: " + e.getMessage());
		}
				//Seventh Error. User Not Found
				try{
					vault.addNewSite("johnnyboy",
							"bigjerm1&","amazon");
				}
				catch (Exception e){
					System.out.println("Error: " + e.getMessage());
				}
				//Eighth Error. Invalid Password
				try{
					vault.addNewUser("johnnyboy",
							"mypass123");
				}
				catch (Exception e){
					System.out.println("Error: " + e.getMessage());
				}
				System.out.println("**TESTING ACCOUNT LOCK**\n");
				try{
					//Lock
					vault.updateSitePassword("jeremiah",
							"bsmacsacs ", "amazon");
				}
				catch (Exception e){
					System.out.println("Error: " + e.getMessage());
				}
				try{//Lock
					vault.updateSitePassword("jeremiah",
							"bsmacsacs ", "amazon");
				}
				catch (Exception e){
					System.out.println("Error: " + e.getMessage());
				}

				try{
					//Lock
					vault.updateSitePassword("jeremiah",
							"bigjerm92", "amazon");
				}
				catch (Exception e){
					System.out.println("Error: " + e.getMessage());
				}
				try{
					//LOCKED
					vault.updateSitePassword("jeremiah",
							"bigjerm92", "amazon");
				}
					catch (Exception e){
					System.out.println("Error: " + e.getMessage());
				}
		}
	}
