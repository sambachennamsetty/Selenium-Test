package com.selenium.test;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSelenium {

	public static void main(String[] args) throws Exception {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\samba\\Downloads\\chromedriver_win32\\chromedriver.exe");

		process();
	}

	static String getRating(WebDriver driver) {
		String rating = EMPTY;
		try {
			rating = driver.findElement(By.xpath("//div[@class='Ob2kfd']//span[@class='Aq14fc']")).getText();
		} catch (Exception e) {
		}
		return rating;
	}

	static String getReviews(WebDriver driver) {
		String reviews = EMPTY;
		try {
			reviews = driver.findElement(By.xpath("//div[@class='Ob2kfd']//span[@class='hqzQac']")).getText();
		} catch (Exception e) {
		}
		return reviews;
	}

	static String getWebsite(WebDriver driver) {
		String website = EMPTY;
		try {
			website = driver.findElement(By.xpath("//div[@class='IzNS7c duf-h']//div[@class='QqG1Sd']//a"))
					.getAttribute("ping");
			if (isNotEmpty(website)) {
				website = driver.findElement(By.xpath("//div[@class='IzNS7c duf-h']//div[@class='QqG1Sd']//a"))
						.getAttribute("href");
			}
		} catch (Exception e) {
		}
		return website;
	}

	static String getAddress(WebDriver driver) {
		String address = EMPTY;
		try {
			address = driver.findElement(By.xpath(
					"//div[@class='QsDR1c']//div[contains(@data-attrid,'kc:/location/location:address')]//div//div//span[2]"))
					.getText();
		} catch (Exception e) {
		}
		return address;
	}

	static String getMobileNumber(WebDriver driver) {
		String mobileNumber = EMPTY;
		try {
			mobileNumber = driver.findElement(By.xpath(
					"//div[@class='QsDR1c']//div[contains(@data-attrid,'kc:/collection/knowledge_panels/has_phone:phone')]//div//div//span[2]"))
					.getText();
		} catch (Exception e) {
		}
		return mobileNumber;
	}

	static String getOfficeName(WebDriver driver) {
		String officeName = EMPTY;
		try {
			officeName = driver.findElement(By.xpath("//div[@class='SPZz6b']//h2//span")).getText();
		} catch (Exception e) {
		}
		return officeName;
	}

	static String getYelpUrl(WebDriver driver) {
		try {
			if (driver.getPageSource().contains("https://www.yelp.com")) {
				return driver.findElement(By.partialLinkText("https://www.yelp.com")).getAttribute("href");
			}
			if (driver.getPageSource().contains("https://m.yelp.com")) {
				return driver.findElement(By.partialLinkText("https://m.yelp.com")).getAttribute("href");
			}
			return EMPTY;
		} catch (Exception e) {
			return EMPTY;
		}
	}

	static boolean isGoogleMapExists(WebDriver driver) {
		try {
			driver.findElement(By.xpath("//div[@class='liYKde g VjDLd']"));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	static String getYelpRating(WebDriver driver) {
		try {
			return driver.findElement(By.xpath(
					"//div[@class=' arrange__373c0__2iVWK gutter-1-5__373c0__3ss9D vertical-align-middle__373c0__2sr2a margin-b2__373c0__117pB border-color--default__373c0__r305k']//div//span//div"))
					.getAttribute("aria-label");
		} catch (Exception e) {
			return EMPTY;
		}
	}

	static String getYelpReviews(WebDriver driver) {
		try {
			return driver.findElement(By.xpath(
					"//div[@class=' arrange__373c0__2iVWK gutter-1-5__373c0__3ss9D vertical-align-middle__373c0__2sr2a margin-b2__373c0__117pB border-color--default__373c0__r305k']//span[contains(@class,' css-1h1j0y3')]"))
					.getText();
		} catch (Exception e) {
			return EMPTY;
		}
	}

	static String getYelpClaimStatus(WebDriver driver) {
		try {
			return driver.findElement(By.xpath("//span[@class=' hovercard-trigger__373c0__3FJ-s']")).getText();
		} catch (Exception e) {
			return EMPTY;
		}
	}

	static String getNumberFromString(final String data) {

		if (data != null) {
			String regex = "\\b[\\+-]?[0-9]*[\\.]?[0-9]+([eE][\\+-]?[0-9]+)?\\b";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(data);
			while (m.find()) {
				return m.group();
			}
		}
		return EMPTY;
	}

	static String process() throws Exception {

		File file = new File("C:\\Users\\samba\\Downloads\\Dentrix-Table.csv");
		InputStream inputStream = new FileInputStream(file);

		CSVParser csvParser = null;
		BufferedWriter writer = null;
		CSVPrinter csvPrinter = null;

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		try {
			final BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			csvParser = new CSVParser(fileReader,
					CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			final File updatedFile = new File("C:\\Users\\samba\\Downloads\\Dentrix-Table-updated.csv");

			if (!updatedFile.exists()) {
				writer = Files.newBufferedWriter(Paths.get("C:\\Users\\samba\\Downloads\\Dentrix-Table-updated.csv"));
				csvPrinter = new CSVPrinter(writer,
						CSVFormat.DEFAULT.withHeader("Office Name", "Address", "State", "Code", "Pincode",
								"Phone Number", "Email", "Business Name", "Google Rating", "Google Reviews",
								"Business Website", "Address", "Mobile Number", "Yelp URL", "Yelp Rating",
								"Yelp Reviews", "Yelp Status"));
			} else {
				writer = Files.newBufferedWriter(Paths.get("C:\\Users\\samba\\Downloads\\Dentrix-Table-updated.csv"),
						StandardOpenOption.APPEND, StandardOpenOption.CREATE);
				csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
			}

			for (CSVRecord csvRecord : csvRecords) {

				String officeName = EMPTY;
				String googleRating = EMPTY;
				String googleReviews = EMPTY;
				String website = EMPTY;
				String address = EMPTY;
				String mobileNumber = EMPTY;
				String yelpUrl = EMPTY;
				String yelpRating = EMPTY;
				String yelpReviews = EMPTY;
				String yelpStatus = EMPTY;

				String csvOfficeName = csvRecord.get(0);
				String officeAddress = csvRecord.get(1);

				driver.get("https://www.google.com");
				Thread.sleep(3000);

				WebElement searchBox = driver.findElement(By.name("q"));
				searchBox.sendKeys(csvOfficeName + " " + officeAddress);
				searchBox.submit();

				Thread.sleep(5000);

				if (isGoogleMapExists(driver)) {
					officeName = getOfficeName(driver);
					googleRating = getRating(driver);
					googleReviews = getReviews(driver);
					website = getWebsite(driver);
					address = getAddress(driver);
					mobileNumber = getMobileNumber(driver);
				}
				yelpUrl = getYelpUrl(driver);

				if (isNotEmpty(yelpUrl)) {
					driver.get(yelpUrl);
					Thread.sleep(5000);
					yelpRating = getYelpRating(driver);
					yelpReviews = getYelpReviews(driver);
					yelpStatus = getYelpClaimStatus(driver);
				}

				csvPrinter.printRecord(csvRecord.get(0), csvRecord.get(1), csvRecord.get(2), csvRecord.get(3),
						csvRecord.get(4), csvRecord.get(5), csvRecord.get(6), officeName,
						getNumberFromString(googleRating), getNumberFromString(googleReviews), website, address,
						mobileNumber, yelpUrl, getNumberFromString(yelpRating), getNumberFromString(yelpReviews),
						yelpStatus);
				csvPrinter.flush();
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (csvParser != null) {
				csvParser.close();
				inputStream.close();
			}
			if (csvPrinter != null) {
				csvPrinter.close();
			}

			if (writer != null) {
				writer.close();
			}
		}
		return null;
	}
}
