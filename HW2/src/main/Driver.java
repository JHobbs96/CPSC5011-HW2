package main;

import exceptions.*;
import vault.PasswordVault;

import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
			PasswordVault vault = new PasswordVault();
				try {
					vault.addNewUser("jeremiah", "bigjerm1&");
					vault.addNewSite("jeremiah", "bigjerm1&", "amazon");
					vault.addNewUser("sarahsmith", "jeremiah&");
					vault.retrieveSitePassword("jeremiah", "bigjerm1&", "amazon");
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
				//First Error
				try{
					vault.updateSitePassword("jeremiah", "bsjbmcsaas", "amazon");
				}
				catch (Exception e){
					System.out.println("Error: " + e.getMessage());
				}
				//Second Error
				try{
					vault.updateSitePassword("jeremiah", "bsmacsacs ", "amazon");
				}
				catch (Exception e){
					System.out.println("Error: " + e.getMessage());
				}
				//Third Error
				try{
					vault.updateSitePassword("jeremiah", "bsmacsacs ", "amazon");
				}
				catch (Exception e){
					System.out.println("Error: " + e.getMessage());
				}
		try{
			//Lock
			vault.updateSitePassword("jeremiah", "bsmacsacs ", "amazon");
		}
		catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}

		try{
			//Lock
			vault.updateSitePassword("jeremiah", "bigjerm1&", "amazon");
		}
		catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
	}

}
