package com.itppm.test.app;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import com.itppm.action.utils.TestUtil;
import com.itppm.common.utils.Locatorsparser;
import com.itppm.common.utils.Xlfile_Reader;
import com.itppm.test.report.TestReports;
import static com.itppm.action.utils.TestUtil.closeWord;

public class DriverApp {

	public static Properties CONFIG;
	public static Xlfile_Reader Core;
    public static Xlfile_Reader testData = null;
	public static Random randomGenerator = new Random(); // Random Port Number generation
	public static String currentTest;
	public static String keyword;
	public static WebDriver driver = null;
	public static String object;
	public static String currentTSID;
	public static String description;
	public static String stepDescription;
	public static String proceedOnFail;
	public static String testStatus;
	public static String data_column_name;
	public static int testRepeat;
	public static int nSelPort;
	public static Calendar cal = new GregorianCalendar();
	public static int month = cal.get(Calendar.MONTH);
	public static int year = cal.get(Calendar.YEAR);
	public static int sec = cal.get(Calendar.SECOND);
	public static int min = cal.get(Calendar.MINUTE);
	public static int date = cal.get(Calendar.DATE);
	public static int day = cal.get(Calendar.HOUR_OF_DAY);
	public static String strDate;
	public static String result;
	public static String cdir = System.getProperty("user.dir");
    public static Locatorsparser lparser = null;

	// Added for Report
	public static String reportBasePath = cdir
			+ System.getProperty("file.separator");
	public static File ImageDirectory;

	public static String fsep = System.getProperty("file.separator");
	// moving image to report directory
	File src1 = new File(cdir + System.getProperty("file.separator") + "src"
			+ fsep + "test" + fsep + "resources" + fsep + "Freshworks.png");
	File src2 = new File(cdir + System.getProperty("file.separator") + "src"
			+ fsep + "test" + fsep + "resources" + fsep + "lyris.png");

	/* Get the current system time - used for generated unique file ids (ex:
	Screenshots, Reports etc on every test run) */
	public static String getCurrentTimeStamp() {
		SimpleDateFormat CurrentDate = new SimpleDateFormat("MM-dd-yyyy" + "_"
				+ "HH-mm-ss");
		Date now = new Date();
		String CDate = CurrentDate.format(now);
		return CDate;
	}

	// Loaded the Selenium and Application log files
	public static final Logger SELENIUM_LOGS = Logger.getRootLogger();
	public static final Logger APPLICATION_LOGS = Logger
			.getLogger("devpinoyLogger");

	@BeforeSuite
	@Parameters({ "browser", "appURL" })
	public void startTesting(@Optional("ft") String browser,
			@Optional("ftt") String appURL) throws Exception {

		try {
			setDriver(browser, appURL);

		} catch (Exception e) {
			System.out.println("Error....." + e.getStackTrace());
		}

		// Code to Generate random numbers
		nSelPort = randomGenerator.nextInt(40000);
		strDate = getCurrentTimeStamp();
		System.out.println("date time stamp :" + strDate);

		String newDir = reportBasePath + "TestExecutionReport_" + strDate;
		File theDir = new File(newDir);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out
					.println("creating directory: " + "TestReport_" + strDate);
			boolean result = false;

			try {
				theDir.mkdir();
				result = true;

				// I decided to replace already existing files with same name

			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

		File imgDir1 = new File(newDir + System.getProperty("file.separator")
				+ "Freshworks.png");

		// if the directory does not exist, create it
		if (!imgDir1.exists()) {
			System.out.println("Copying log files");
			boolean result = false;
			try {
				/*
				 * imgDir.mkdir(); result = true; ImageDirectory= imgDir;
				 */

				TestUtil.copyFile(src1, imgDir1);

			}

			catch (SecurityException se) {
				// handle it
			}

			if (result) {
				System.out.println("IMG DIR created");
			}
		}
		/* Start testing method will start generating the Test Reports from the
		beginning */

		TestReports.startTesting(newDir + System.getProperty("file.separator")
				+ "TestReport_" + strDate + ".html",
				TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"), "QA Automation",
				"3.0");

		// Loading Config File
		CONFIG = new Properties();
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "src" + fsep + "test"
				+ fsep + "resources" + fsep + "config.properties");
		CONFIG.load(fs);

		// Load datatable
		Core = new Xlfile_Reader(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "src" + fsep + "test"
				+ fsep + "resources" + fsep + "Core.xlsx");
		testData = new Xlfile_Reader(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "src" + fsep + "test"
				+ fsep + "resources" + fsep + "TestData.xlsx");

		// Load parser

        lparser = new Locatorsparser(System.getProperty("user.dir") + fsep
				+ "src" + fsep + "test" + fsep + "resources" + fsep
				+ "ObjectRepo.properties");

	}

	private void setDriver(String browser, String appURL) {
		if (browser.equalsIgnoreCase("firefox")) {
			driver = initFirefoxDriver(appURL);
			driver.manage().window().maximize();

		}
		else if (browser.equalsIgnoreCase("ie")) {
			driver = initEdgeDriver(appURL);
			driver.manage().window().maximize();

		} else if (browser.equalsIgnoreCase("chrome")) {
			driver = initChromeDriver(appURL);
			driver.manage().window().maximize();

		}
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	}

	private static WebDriver initChromeDriver(String appURL) {
		System.out.println("Launching google chrome with new profile..");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("chrome.switches", "--disable-extensions");
		//Added below 1 line
		options.addArguments("--remote-allow-origins=*");
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(options);
		driver.navigate().to(appURL);
		return driver;
	}

	private static WebDriver initFirefoxDriver(String appURL) {
		System.out.println("Launching Firefox browser..");
		WebDriverManager.firefoxdriver().setup();
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	private static WebDriver initEdgeDriver(String appURL) {
		System.out.println("Launching IE browser..");
		WebDriverManager.edgedriver().setup();
		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	// report ends
	@Test
	public void testApp() throws Throwable {

		String startTime = null;

		TestReports.startSuite("Suite 1");

		for (int tcid = 2; tcid <= Core.getRowCount("Suite1"); tcid++) {
			currentTest = Core.getCellData("Suite1", "TCID", tcid);
			description = Core.getCellData("Suite1", "Description", tcid);
			// initialize start time of test
			if (Core.getCellData("Suite1", "Runmode", tcid).equals("Y")) {

				// executed the keywords

				// loop again - rows in test data
				int totalSets = testData.getRowCount(currentTest + "1");
				; // holds total rows in test data sheet. IF sheet does not
					// exist then 2 by default
				totalSets = testData.getRowCount(currentTest);
				if (totalSets <= 1) {
					totalSets = 2; // run atleast once
				}

				for (testRepeat = 2; testRepeat <= totalSets; testRepeat++) {
					startTime = TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa");

					APPLICATION_LOGS.debug("Executing the test " + currentTest);

					// implemented keywords file
					try {
						for (int tsid = 2; tsid <= Core
								.getRowCount(currentTest); tsid++) {

							// Get values from xls file
							keyword = Core.getCellData(currentTest, "Keyword",
									tsid);
							object = Core.getCellData(currentTest, "Object",
									tsid);
							currentTSID = Core.getCellData(currentTest, "TCID",
									tsid);
							stepDescription = Core.getCellData(currentTest,
									"Description", tsid);
							proceedOnFail = Core.getCellData(currentTest,
									"ProceedOnFail", tsid);
							data_column_name = Core.getCellData(currentTest,
									"Data_Column_Name", tsid);

							Method method = KeywordsApp.class
									.getMethod(keyword);
							result = (String) method.invoke(method);
							APPLICATION_LOGS.debug("***Result of execution -- "
									+ result);

							if (result.startsWith("Pass")) {
								testStatus = result;

								TestUtil.captureScreenshot(cdir
										+ System.getProperty("file.separator")
										+ "Screenshots//" + TestUtil.imageName
										+ ".jpeg");

								TestReports
										.addKeyword(
												stepDescription,
												keyword,
												result,
//												"file:///"
//														cdir +
												"/generic-itppm/"
														+ System.getProperty("file.separator") +
														 "Screenshots/"
														+ TestUtil.imageNameIP
														+ ".jpeg");
							}

							else if (result.startsWith("Fail")) {
								testStatus = result;
								TestUtil.captureScreenshot(cdir
										+ System.getProperty("file.separator")
										+ "Screenshots//" + TestUtil.imageName
										+ ".jpeg");

								// changed to make the screenshot path generic
								TestReports
										.addKeyword(
												stepDescription,
												keyword,
												result,
//												"file:///"
//														cdir +
												"/generic-itppm"
														+ System.getProperty("file.separator") +
														"Screenshots"
														+ System.getProperty("file.separator") +
														 TestUtil.imageNameIP
														+ ".jpeg");
								if (proceedOnFail.equalsIgnoreCase("N")) {

									break;

								}
								break;

							}

						}

					} catch (Throwable t) {
						APPLICATION_LOGS.debug("Error came");

					}

					// report pass or fail in HTML Report
					if (testStatus == null) {
						testStatus = "Pass";
					}
					APPLICATION_LOGS.debug("######################"
							+ currentTest + " --- " + testStatus);
					TestReports.addTestCase(currentTest, startTime,
							TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"),
							testStatus);

					if (result.startsWith("Fail")) {

						break;
					}
				}// test data

			} else {
				APPLICATION_LOGS.debug("Skipping the test " + currentTest);
				testStatus = "Skip";

				// report skipped
				APPLICATION_LOGS.debug("#######################" + currentTest
						+ " --- " + testStatus);
				TestReports.addTestCase(currentTest,
						TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"),
						TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"), testStatus);

			}

			testStatus = null;

			if (result.startsWith("Fail")) {
				break;
			}
		}
		TestReports.endSuite();
	}

	@AfterSuite
	public static void endScript() throws Exception {

		// Once the test is completed update the end time in HTML report
		TestReports.updateEndTime(TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"));
		driver.close();
		driver.quit();

		// Sending Mail when script fails
		if (result.startsWith("Fail")) {
			closeWord();
			driver.close();
			driver.quit();

		}
	}
}