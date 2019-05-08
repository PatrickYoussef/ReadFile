/**
* Patrick Youssef #40098029
* COMP249
* Assignment #3 
* March 21st 2019
*/
package a3_comp249;

import java.util.Scanner;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class AuthorBiBCreator {

	public static void main(String[] args) {

		Scanner[] sc = new Scanner[10];
		PrintWriter pw1 = null, pw2 = null, pw3 = null;
		Scanner check = new Scanner(System.in);
		String name = "";

		/**
		 * Asking the name author the user want to search for
		 */
		System.out.println("Welcome to the BibCreator !!!!!");
		System.out.print("Please enter the author name you are targeting: ");
		name = check.nextLine();
		System.out.println();

		/**
		 * Calling the openLatex method
		 */
		openLatex(sc);

		/**
		 * Creating files with the name of the author and the three formats, and also the BU's of each format
		 */
		File fileIEEE = new File(name + "-IEEE.json");
		File fileACM = new File(name + "-ACM.json");
		File fileNJ = new File(name + "-NJ.json");
		File fileIEEEbu = new File(name + "-IEEE-BU.json");
		File fileACMbu = new File(name + "-ACM-BU.json");
		File fileNJbu = new File(name + "-NJ-BU.json");

		/**
		 * Checking if there are already existing files with the name wanted
		 */
		checkfiles(fileIEEE, fileIEEEbu, name);
		checkfiles(fileACM, fileACMbu, name);
		checkfiles(fileNJ, fileNJbu, name);

		/**
		 * Opening our printwriters, if an exception is thrown, a message will be published, all the files will close and the program will terminate
		 */
		try {
			pw1 = new PrintWriter(new FileOutputStream(fileIEEE));
		} catch (FileNotFoundException g) {
			System.out.println("The " + fileIEEE.getName() + " file could not be created, program will terminate");
			for (int i = 0; i < sc.length; i++)
				sc[i].close();
			System.exit(0);
		}
		
		
		try {
			pw2 = new PrintWriter(new FileOutputStream(fileACM));
		} catch (FileNotFoundException o) {
			System.out.println("The " + fileACM.getName() + " file could not be created, program will terminate");
			for (int i = 0; i < sc.length; i++)
				sc[i].close();
			System.exit(0);
		}
		
		
		try {
			pw3 = new PrintWriter(new FileOutputStream(fileNJ));
		} catch (FileNotFoundException g) {
			System.out.println("The " + fileNJ.getName() + " file could not be created, program will terminate");
			for (int i = 0; i < sc.length; i++)
				sc[i].close();
			System.exit(0);
		}
		
		/**
		 * Calling the processBibFiles to get three files (IEEE, ACM and NJ format), and get the information of each article with the author searched
		 */
		processBibFiles(sc, name, pw1, pw2, pw3);

		/**
		 * Closing the scanner
		 */
		check.close();
		
		
		/**
		 * Closing message
		 */
		System.out.println("\n\nWe hope you enjoyed creating the needed files using AuthorBibCreator" );

	}
	/**
	 * This method takes the parameter below and write the complete informations of the article containing the author name
	 * @param sc Scanner array containing all the opened files
	 * @param name Author name searched
	 * @param pw1 PrintWriter IEEE format
	 * @param pw2 PrintWriter ACM format
	 * @param pw3 PrintWriter NJ format
	 */
	public static void processBibFiles(Scanner[] sc, String name, PrintWriter pw1, PrintWriter pw2, PrintWriter pw3) {
		String read = null, author = null, journal = null, title = null, year = null, volume = null, number = null,
				pages = null, doi = null, month = null;
		int counter = 1;
		/**
		 * Looks through every file
		 */
		for (int i = 0; i < sc.length; i++) {
			/**
			 * Reads the file as long as there is another line to read
			 */
			while (sc[i].hasNextLine() && sc[i] != null) {
				read = sc[i].nextLine();
				
				/**
				 * Stores the information of the article if the author name is found
				 */
				if (read.contains(name) && read.contains("author={"))
					author = read;

				if (author != null) {
					if (read.contains("journal={"))
						journal = read;

					else if (read.contains("title={"))
						title = read;

					else if (read.contains("year={"))
						year = read;

					else if (read.contains("volume={"))
						volume = read;

					else if (read.contains("number={"))
						number = read;

					else if (read.contains("pages={"))
						pages = read;

					else if (read.contains("doi={"))
						doi = read;

					else if (read.contains("month={"))
						month = read;
				}
				/**
				 * Stores the information without the brackets of the files when all variables contain information
				 */
				if ((author != null) && (journal != null) && (title != null) && (year != null) && (volume != null)
						&& (number != null) && (pages != null) && (doi != null) && (month != null)) {

					author = author.substring(author.indexOf("{") + 1, author.indexOf("}"));
					journal = journal.substring(journal.indexOf("{") + 1, journal.indexOf("}"));
					title = title.substring(title.indexOf("{") + 1, title.indexOf("}"));
					year = year.substring(year.indexOf("{") + 1, year.indexOf("}"));
					volume = volume.substring(volume.indexOf("{") + 1, volume.indexOf("}"));
					number = number.substring(number.indexOf("{") + 1, number.indexOf("}"));
					pages = pages.substring(pages.indexOf("{") + 1, pages.indexOf("}"));
					doi = doi.substring(doi.indexOf("{") + 1, doi.indexOf("}"));
					month = month.substring(month.indexOf("{") + 1, month.indexOf("}"));
					/***Changing the author format to fit the IEEE, ACM and NJ format*/
					String authorIEEE = author.replace(" and ", ", ");
					String[] authorACM = author.split(" and ");
					String authorNJ = author.replace(" and ", " & ");
					/**
					 * Writing the informations in our files
					 */
					pw1.println(authorIEEE + ". \"" + title + "\", " + journal + ", vol." + volume + ", no. "
							+ number + ", p. " + pages + ", " + month + " " + year + ".");

					pw2.println("[" + counter + "]    " + authorACM[0] + " et al. " + year + ". " + title + ". "
							+ journal + ". " + volume + ", " + "(" + year + "), " + pages + ". DOI:https://doi.org/"
							+ doi + ".");
					counter++;

					pw3.println(authorNJ + ". " + title + ". " + journal + ". " + volume + ", " + pages + "("
							+ year + ").");
					/**
					 * Sets the variables to null so we can search a new file
					 */
					author = null;
					journal = null;
					title = null;
					year = null;
					volume = null;
					number = null;
					pages = null;
					doi = null;
					month = null;
				}

			}

		}
		System.out.println("\nA total of " + (counter-1) + " records were found for author(s) with name : " + name);
		System.out.println("Files " + name + "-IEEE.json, " + name + "-ACM.json and " + name + "-NJ.json have been created!");
		/**
		 * Closing our printwriters and our scanners
		 */
		pw1.close();
		pw2.close();
		pw3.close();
		
		for (int l = 0; l < sc.length; l++)
			sc[l].close();
	}
	/**
	 * Checks if the file exists, if so, checks if the file-BU version exists, if so, delete that BU-file and replace the existing name file as the bu-version
	 * @param file the file format
	 * @param bu the file-bu format
	 * @param name author name
	 */
	public static void checkfiles(File file, File bu, String name) {

		try {
			if (file.exists()) {
				throw new FileExistsException();
			}
		} catch (FileExistsException e) {
			System.out.println("A file already exists with the name: " + file.getName());

			if (bu.exists()) {
				bu.delete();
			}
			System.out.println("File will be renamed as: " + bu.getName() + " and any old BUs will be deleted\n");
			file.renameTo(bu);

		}

	}
/**
 * Opening the files that we want to read, catch the exception if it does not work
 * @param sc scanners
 */
	public static void openLatex(Scanner[] sc) {
		int num = 0;

		try {
			for (int i = 1; i <= sc.length; i++) {
				num = i;
				sc[i - 1] = new Scanner(new FileInputStream("Latex" + i + ".bib"));
			}

		} catch (FileNotFoundException obj) {
			System.out.println("Could not open input file Latex " + num + " for reading.");
			System.out.println("Please check if file exists! Program will terminate after closing any opened files ");

			for (int i = 0; i < (num - 1); i++)
				sc[i].close();

			System.exit(0);

		}

	}

}
