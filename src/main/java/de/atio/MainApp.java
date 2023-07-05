package de.atio;

public class MainApp {

    public static void main(String[] args) throws Exception{

        ConfigFileHandler configHandler = new ConfigFileHandler("config.json");
        // get and print the ThingWorx server password

		/* Crypro Demo */

		CryptoUtil cryptoUtil = new CryptoUtil();

		System.out.println("\nDEMO PW1 .....");

		String pw1				= configHandler.GetStringValue("sapb1DB,serverSettings,password");
		Boolean pw1encrypted	= configHandler.GetBooleanValue("sapb1DB,serverSettings,encrypted");
		System.out.println("pw1encrypted " + pw1encrypted);
		if (!pw1encrypted) {
			String encryptedPW = cryptoUtil.encrypt(pw1);
			System.out.println("password \""+pw1+"\" is plain text!!! Please replace by : " + encryptedPW + " and set encrypted = true");
		}else if(pw1encrypted){
			System.out.println("original password : " + cryptoUtil.decrypt(pw1));
		}

		System.out.println("\nDEMO PW2 .....");
		
		String pw2				= configHandler.GetStringValue("localDB,serverSettings,password");
		Boolean pw2encrypted	= configHandler.GetBooleanValue("localDB,serverSettings,encrypted");
		System.out.println("pw2encrypted " + pw2encrypted);
		if (!pw2encrypted) {
			String encryptedPW = cryptoUtil.encrypt(pw2);
			System.out.println("password \""+pw2+"\" is plain text!!! Please replace by : " + encryptedPW + " and set encrypted = true");
		}else if(pw2encrypted){
			System.out.println("original password : " + cryptoUtil.decrypt(pw2));
		}

		System.out.println("\n");

		/* Code Wiederholungen sind natürlich nicht gut. Hier nur um die Funktion zu demonstrieren */
		
	}
}
