package com.uiauto.action.utils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.uiauto.common.utils.Xlfile_Reader;
import com.uiauto.test.app.DriverApp;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class TestUtil extends DriverApp {

    public static String imageName;
    public static String imageNameIP;
    public static String imageError;
    public static String subject;
    public static String idir = System.getProperty("user.dir");
    public static XWPFDocument docx;
    public static FileOutputStream out;
    public static boolean flagforTitleScreen = true;
    public static String document_name;
    public static int count = 1;
    public static String from_date = null;
    public static String to_date = null;
    public static File outFolder;
    public static Xlfile_Reader datareader = null;

    public static String Handeler() {
        try {
            InetAddress ownIP = null;
            ownIP = InetAddress.getLocalHost();
            subject = ownIP.getHostAddress();

        } catch (Throwable t) {
            t.printStackTrace();
        }
        return subject;
    }

    // returns current date and time
    public static String now(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }

    // store screenshots
    public static void captureScreenshot(String path) throws IOException {
        Calendar cal = new GregorianCalendar();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int sec = cal.get(Calendar.SECOND);
        int min = cal.get(Calendar.MINUTE);
        int date = cal.get(Calendar.DATE);
        int day = cal.get(Calendar.HOUR_OF_DAY);
        String ImageDest = idir+System.getProperty("file.separator") + "Screenshots//";
        // String ImageDest = System.getProperty("user.dir")+""
        imageName = ImageDest + DriverApp.currentTest + DriverApp.currentTSID + year + "_" + date + "_" + (month + 1) + "_" + day + "_" + min + "_" + sec;
        imageError = DriverApp.currentTest + DriverApp.currentTSID + year + "_" + date + "_" + (month + 1) + "_" + day + "_" + min + "_" + sec;
        imageNameIP = DriverApp.currentTest + DriverApp.currentTSID + year + "_" + date + "_" + (month + 1) + "_" + day + "_" + min + "_" + sec;
        // selenium.captureEntirePageScreenshot(imageName+ ".jpeg"," ");
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(imageName + ".jpeg"));
    }
    
    
  //copy logo to report directory
    public static void copyFile( File from, File to ) throws IOException {
        Files.copy( from.toPath(), to.toPath() );
    }

    // GET data from TestData.xsls
    public static Object[][] getData(String sheetName) {
        // return test data;
        // read test data from xls

        int rows = DriverApp.testData.getRowCount(sheetName) - 1;
        if (rows <= 0) {
            Object[][] testData = new Object[1][0];
            return testData;

        }
        rows = DriverApp.testData.getRowCount(sheetName); // 3
        int cols = DriverApp.testData.getColumnCount(sheetName);

        Object data[][] = new Object[rows - 1][cols];

        for (int rowNum = 2; rowNum <= rows; rowNum++) {

            for (int colNum = 0; colNum < cols; colNum++) {
                data[rowNum - 2][colNum] = DriverApp.testData.getCellData(sheetName, colNum, rowNum);
            }
        }

        return data;

    }

    public static void captureScreenShot(XWPFDocument docx, XWPFRun run, FileOutputStream out) throws Exception {
        String screenshot_name = System.currentTimeMillis() + ".png";
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        BufferedImage image = ImageIO.read(screenshot);
        File file = new File(System.getProperty("user.dir")  + "/reports/" + screenshot_name);
        ImageIO.write(image, "png", file);
        InputStream pic = new FileInputStream(System.getProperty("user.dir") + "/reports/" + screenshot_name);
        run.addPicture(pic, XWPFDocument.PICTURE_TYPE_PNG, screenshot_name, Units.toEMU(450), Units.toEMU(300));
        run.addBreak(BreakType.PAGE);
        pic.close();
        file.delete();
    }

    public static void writeToWord(String scenario){
        try {
            if(flagforTitleScreen == true){
               document_name = description;
               docx = new XWPFDocument();
                XWPFRun template = docx.createParagraph().createRun();
                InputStream pic = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/Evidence_Template.png");
                template.addPicture(pic, XWPFDocument.PICTURE_TYPE_PNG, "Evidence", Units.toEMU(650),Units.toEMU(490));
                template.addBreak(BreakType.PAGE);
            }
            flagforTitleScreen = false;
            XWPFRun text = docx.createParagraph().createRun();

            // Formatting line1 by setting  bold
            text.setBold(true);
            text.setText(scenario);
            text.addBreak();

            XWPFRun run = docx.createParagraph().createRun();
            out = new FileOutputStream(System.getProperty("user.dir") + "/reports/" + document_name + ".docx");
            captureScreenShot(docx, run, out);
            TimeUnit.SECONDS.sleep(1);

            docx.write(out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public static void sendMail() throws IOException {

        zipDir();

        // Recipient's email ID needs to be mentioned.
        String to = "kumaran.venkatraman@freshworks.com,sai.krishnan@freshworks.com";

        // Sender's email ID needs to be mentioned
        String from = "sai.krishnan@freshworks.com";

        //Get properties object
        final Properties prop = new Properties();
        prop.put("mail.smtp.username", "sai.krishnan@freshworks.com");
        prop.put("mail.smtp.password", "cwopjpmtvtsaaclx");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // TLS

        //get Session
        Session mailSession = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(prop.getProperty("mail.smtp.username"),
                        prop.getProperty("mail.smtp.password"));
            }
        });
        //compose message
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(mailSession);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Sku's Test Results");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText("Hi All,"
                    + "\n\n Greetings! The Sku was successfully validated please find the test results in the attachment."
                    + "\n\n Regards,"
                    + "\n ITPPM Team");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = System.getProperty("user.dir") + "/reports.zip";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("SKU Validation Report");
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
}

public static void zipDir() throws IOException {
    String sourceFile = System.getProperty("user.dir") + "/reports";
    FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "/reports.zip");
    ZipOutputStream zipOut = new ZipOutputStream(fos);
    File fileToZip = new File(sourceFile);

    zipFile(fileToZip, fileToZip.getName(), zipOut);
    zipOut.close();
    fos.close();
}

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    public static void closeWord() throws IOException {
        flagforTitleScreen = true;
        out.flush();
        out.close();
        docx.close();
    }

}
