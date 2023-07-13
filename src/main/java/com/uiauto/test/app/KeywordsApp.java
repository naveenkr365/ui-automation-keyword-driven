package com.uiauto.test.app;

import java.io.IOException;
import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.uiauto.action.utils.TestUtil.writeToWord;


public class KeywordsApp extends DriverApp {

    public static Random randomGenerator = new Random();
    public static Calendar cal = new GregorianCalendar();
    public static int date = cal.get(Calendar.DATE);
    public static int month = cal.get(Calendar.MONTH);
    public static int year = cal.get(Calendar.YEAR);
    public static int sec = cal.get(Calendar.SECOND);
    public static int day = cal.get(Calendar.HOUR_OF_DAY);
    public static int hour = cal.get(Calendar.HOUR);
    public static int min = cal.get(Calendar.MINUTE);
    public static String sMin = new Integer(randomGenerator.nextInt(60)).toString();
    public static String sSec = new Integer(randomGenerator.nextInt(60)).toString();
    public static String sHour = new Integer(randomGenerator.nextInt(24)).toString();
    public static String sDate = new Integer(date).toString();
    public static String upload = System.getProperty("user.dir");
    public static long globalwait = 30;

    public static WebDriverWait waitdriver = new WebDriverWait(driver, Duration.ofSeconds(globalwait));

    public static String winHandleBefore = driver.getWindowHandle();

    public static WebElement getElement(By locatedBy) {
        waitdriver.until(ExpectedConditions.presenceOfElementLocated(locatedBy));
        return driver.findElement(locatedBy);
    }

    public static String setText() throws IOException {
        APPLICATION_LOGS.debug("Executing setText Keyword" + " " + object);
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try {
                WebElement text = getElement(lparser.getobjectLocator((object)));
                waitdriver.until(ExpectedConditions.elementToBeClickable(text));
                text.sendKeys(data);
                APPLICATION_LOGS.info("setText keyword data :" + data);
            }catch (StaleElementReferenceException s){
                WebElement text = getElement(lparser.getobjectLocator((object)));
                waitdriver.until(ExpectedConditions.elementToBeClickable(text));
                text.sendKeys(data);
                APPLICATION_LOGS.info("setText keyword data :" + data);
            }
            catch (NoSuchElementException e) {
                APPLICATION_LOGS.debug("No Element Found to enter text -" + object + e.getMessage());
                return "Fail";
            }
            catch(Exception ex){
                APPLICATION_LOGS.debug("Unable to perform operation -" + object + ex.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }

    public static String clickButton() throws IOException {
        APPLICATION_LOGS.debug("Executing clickButton Keyword" + " " + object);
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try {
                WebElement button = getElement(lparser.getobjectLocator((object)));
                waitdriver.until(ExpectedConditions.elementToBeClickable(button));
                button.click();
            }catch (StaleElementReferenceException se){
                WebElement button = getElement(lparser.getobjectLocator((object)));
                waitdriver.until(ExpectedConditions.elementToBeClickable(button));
                button.click();
            }
            catch (NoSuchElementException e) {
                APPLICATION_LOGS.debug("No Element Found to click -" + object + e.getMessage());
                return "Fail";
            } catch (Exception ex) {
                APPLICATION_LOGS.debug("Unable to perform operation -" + object + ex.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }

    public static String clickLink() throws IOException {
        APPLICATION_LOGS.debug("Executing clickLink  Keyword" + " " + object);
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try {
                WebElement link = getElement(lparser.getobjectLocator((object)));
                waitdriver.until(ExpectedConditions.elementToBeClickable(lparser.getobjectLocator(object)));
                link.click();
            } catch (NoSuchElementException e) {
                APPLICATION_LOGS.debug("No Link Found to click -" + object + e.getMessage());
                return "Fail";
            } catch (Exception ex) {
                APPLICATION_LOGS.debug("Unable to perform operation -" + object + ex.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }

    public static String setTextEnter() throws InterruptedException {
        APPLICATION_LOGS.debug("Executing setTextEnter Keyword" + " " + object);
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try {

                WebElement text = getElement(lparser.getobjectLocator((object)));
                waitdriver.until(ExpectedConditions.elementToBeClickable(text));
                text.sendKeys(data);
                text.sendKeys(Keys.RETURN);
                APPLICATION_LOGS.info("setTextEnter keyword data :" + data);
            } catch (NoSuchElementException e) {
                APPLICATION_LOGS.debug("No Element Found to enter text -" + object + e.getMessage());
                return "Fail";
            }
            catch(Exception ex){
                APPLICATION_LOGS.debug("Unable to perform operation -" + object + ex.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }

    public static String checkBoxCheck() {
        APPLICATION_LOGS.debug("Executing checkbox Keyword" + " " + object);
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try {

                if (getElement(lparser.getobjectLocator((object))).getAttribute("checked") != null) {
                    APPLICATION_LOGS.info("Check Box Already Checked");
                }
                else{
                    WebElement checkbox = getElement(lparser.getobjectLocator((object)));
                    waitdriver.until(ExpectedConditions.elementToBeClickable(lparser.getobjectLocator(object)));
                    checkbox.click();
                }
            } catch (NoSuchElementException e) {
                APPLICATION_LOGS.debug("Checkbox not found -" + object + e.getMessage());
                return "Fail";
            } catch (Exception ex) {
                APPLICATION_LOGS.debug("Unable to perform operation -" + object + ex.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }

    public static String selectRadiobutton() {
        APPLICATION_LOGS.debug("Executing selectRadiobutton Keyword" + " " + object);
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try {

                if (getElement(lparser.getobjectLocator((object))).getAttribute("checked") != null) {
                    APPLICATION_LOGS.info("Check Box Already Checked");
                } else {
                    WebElement radio = getElement(lparser.getobjectLocator((object)));
                    waitdriver.until(ExpectedConditions.elementToBeClickable(lparser.getobjectLocator(object)));
                    radio.click();
                    APPLICATION_LOGS.info("Check Box Checked");
                }

            } catch(NoSuchElementException e) {
                APPLICATION_LOGS.debug("Checkbox not found -" + object + e.getMessage());
                return "Fail";
            } catch(Exception ex) {
                APPLICATION_LOGS.debug("Unable to perform operation -" + object + ex.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }

    public static String clearText() throws Exception {
        APPLICATION_LOGS.debug("Executing clearText Keyword" + " " + object);
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {

            try {
                WebElement clear = getElement(lparser.getobjectLocator((object)));
                waitdriver.until(ExpectedConditions.elementToBeClickable(clear));
                clear.clear();
                APPLICATION_LOGS.info("clearText keyword data :" + data);
            } catch(NoSuchElementException e) {
                APPLICATION_LOGS.debug("Checkbox not found -" + object + e.getMessage());
                return "Fail";
            } catch(Exception ex) {
                APPLICATION_LOGS.debug("Unable to perform operation -" + object + ex.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }

    public static String selectText() throws Exception {
        APPLICATION_LOGS.debug("Executing selectText Keyword" + " " + object);
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try {
                if(data.equalsIgnoreCase("@SelectRandom")){
                    Select select = new Select(getElement(lparser.getobjectLocator((object))));
                    waitdriver.until(ExpectedConditions.elementToBeClickable(lparser.getobjectLocator((object))));
                    List<WebElement> allOptions = select.getOptions();
                    int maxIndex = allOptions.size()-1;
                    int e = getRandom(maxIndex);
                    select.selectByIndex(e);
                    APPLICATION_LOGS.info("selectText keyword data :" + data);
                }
                else
                {
                    Select select = new Select(getElement(lparser.getobjectLocator((object))));
                    waitdriver.until(ExpectedConditions.elementToBeClickable(lparser.getobjectLocator((object))));
                    select.selectByVisibleText(data);
                }
            } catch(NoSuchElementException e) {
                APPLICATION_LOGS.debug("Checkbox not found -" + object + e.getMessage());
                System.out.println(e.getMessage());
                return "Fail";

            } catch(Exception ex) {
                APPLICATION_LOGS.debug("Unable to perform operation -" + object + ex.getMessage());
                System.out.println(ex.getMessage());
                return "Fail";

            }
        }
        return "Pass";
    }

    public static String navigate() throws Throwable {
        APPLICATION_LOGS.debug("Executing Navigate Keyword");
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equals("@Skip")) {
            try {
                driver.get(data);
                APPLICATION_LOGS.info("navigate keyword data :" + data);
            } catch (Exception e) {
                APPLICATION_LOGS.debug("Error while navigating -" + e.getMessage());
                return "Fail-";
            }
        }
        return "Pass";
    }

    public static String mouseover() throws IOException {
        APPLICATION_LOGS.debug("Executing mouseover Keyword" + " " + object);
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try {

                WebElement element = waitdriver.until(ExpectedConditions.elementToBeClickable(lparser.getobjectLocator(object)));
                Actions action = new Actions(driver);
                element = getElement(lparser.getobjectLocator((object)));
                action.moveToElement(element).build().perform();

            } catch (NoSuchElementException e) {
                APPLICATION_LOGS.debug("No Element Found to click -" + object + e.getMessage());
                return "Fail";
            } catch (Exception ex) {
                APPLICATION_LOGS.debug("Unable to perform operation -" + object + ex.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }

    public static String fileUpload(){
        APPLICATION_LOGS.debug("Executing fileUpload Keyword" + " " + object);
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if(!data.equalsIgnoreCase("@Skip")){
            try{
                WebElement fileUpload = getElement(lparser.getobjectLocator(object));
                waitdriver.until(ExpectedConditions.elementToBeClickable(fileUpload));
                fileUpload.sendKeys(data);
                APPLICATION_LOGS.info("fileUpload keyword data :" + data);
            }
            catch(NoSuchElementException e){
                APPLICATION_LOGS.debug("No Element Found to upload -"+ object + e.getMessage());
                return "Fail";
            }
            catch(Exception ex){
                APPLICATION_LOGS.debug("Unable to upload -" + object + ex.getMessage());
                return "Fail-";
            }
        }
        return "Pass";
    }

    public static String switchToActiveElements(){
        APPLICATION_LOGS.debug("Executing activeElements Keyword");
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try{
                driver.switchTo().activeElement();
            }
            catch(Exception e){
                APPLICATION_LOGS.debug("Unable to Switch"  + e.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }

    public static String switchIframe(){
        APPLICATION_LOGS.debug("Executing switchIframe Keyword" + " " + object);
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try{
                driver.switchTo().frame(lparser.getobjectLocatorValue(object));
            }
            catch(NoSuchElementException e)
            {
                APPLICATION_LOGS.debug("No Element Found to switchIframe -"+ object + e.getMessage());
                return "Fail";
            }
            catch(Exception ex)
            {
                APPLICATION_LOGS.debug("Unable to switch to the frame" + object + ex.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }



    public static String switchParentFrame(){
        APPLICATION_LOGS.debug("Executing switchParentFrame keyword");
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try{
                driver.switchTo().parentFrame();

            }
            catch(Exception e){
                APPLICATION_LOGS.debug("Unable to switch to the parent frame" +e.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }

    public static String checkboxUncheck(){
        APPLICATION_LOGS.debug("Executing checkboxUncheck Keyword" + " " + object);
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try{
                if (getElement(lparser.getobjectLocator((object))).getAttribute("checked")!= null) {
                    WebElement unCheck = getElement(lparser.getobjectLocator(object));
                    waitdriver.until(ExpectedConditions.elementToBeClickable(unCheck));
                    unCheck.click();
                    APPLICATION_LOGS.info("Check Box Unchecked");
                }
                else
                {
                    APPLICATION_LOGS.info("Check Box Already Unchecked");
                }
            }
            catch(NoSuchElementException e){
                APPLICATION_LOGS.info("No Element Found to Uncheck the checkbox");
                return "Fail-";
            }
            catch(Exception ex){
                APPLICATION_LOGS.debug("Unable to Uncheck the checkbox" + object + ex.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }

    public static String switchParentWindow(){
        APPLICATION_LOGS.debug("Executing switchParentWindow Keyword");
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try{
                driver.switchTo().window(winHandleBefore);
            }
            catch(Exception e)
            {
                APPLICATION_LOGS.debug("Unable to switch to Parent Window"  + e.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }
    public static String switchWindow(){
        APPLICATION_LOGS.debug("Execuing switchWindow Keyword" + " " + object);
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try{

                Set<String> handles = driver.getWindowHandles();
                for(String handle : handles)
                {
                    if(driver.switchTo().window(handle).getTitle().equals(lparser.getobjectLocatorValue(object)))
                        driver.switchTo().window(lparser.getobjectLocatorValue(object));
                }
            }
            catch(NoSuchElementException e){
                APPLICATION_LOGS.info("No Element Found to Switch" + object + e.getMessage());
                return "Fail-";
            }
            catch(Exception ex){
                APPLICATION_LOGS.debug("Unable to switch window"+ object + ex.getMessage());
                return "Fail-";
            }
        }
        return "Pass";
    }

    public static String switchLatestWindow(){
        APPLICATION_LOGS.debug("Executing switch window keyword");
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try{
                for(String handle : driver.getWindowHandles()) {

                    System.out.println(driver.switchTo().window(handle).getTitle());

                }
            }
            catch(Exception e)
            {
                APPLICATION_LOGS.debug("Unable to switch"  + e.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }
    public static String switchAlertAccept(){
        APPLICATION_LOGS.debug(" Executing the switchAlertAccept Keyword");
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try{
                Alert alert =  driver.switchTo().alert();
                alert.accept();
            }
            catch(Exception e)
            {
                APPLICATION_LOGS.debug("Unable to switch to alert" + object + e.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }

    public static String switchAlertDismiss(){
        APPLICATION_LOGS.debug("Executing the switchLertDismiss Keyword");
        String data = testData.getCellData(currentTest, data_column_name, testRepeat);
        if (!data.equalsIgnoreCase("@Skip")) {
            try{
                Alert alert = driver.switchTo().alert();
                alert.dismiss();
            }
            catch(Exception e)
            {
                APPLICATION_LOGS.debug("Unable to switch to alert" + object + e.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }

    public static String verifyText(){
        APPLICATION_LOGS.debug("Executing Verify keyword" + " " + object);
        String data =  testData.getCellData(currentTest, data_column_name, testRepeat);
        String description = stepDescription + " " + data;
        Boolean flag = new Boolean(testData.getCellData(currentTest,data_column_name,testRepeat));
        if (!data.equalsIgnoreCase("@Skip")) {
            try {
                WebElement verify = getElement(lparser.getobjectLocator(object));
                waitdriver.until(ExpectedConditions.visibilityOfAllElements(verify));
                System.out.println(verify.getText());
                if (getElement(lparser.getobjectLocator(object)).getText().equalsIgnoreCase(data) || getElement(lparser.getobjectLocator(object)).getText().contains(data)){
                    APPLICATION_LOGS.info("verifyText keyword data :" + data);
                    writeToWord(description);
                }
            } catch (NoSuchElementException e) {
                APPLICATION_LOGS.debug("No Element Found to Verify the Text");
            } catch (Exception ex) {
                ex.printStackTrace();
                APPLICATION_LOGS.debug("Unable to verify" + object + ex.getMessage());
                return "Fail";
            }
        }
        return "Pass";
    }

    public static String verifyTax(){
        APPLICATION_LOGS.debug("Executing Verify keyword" + " " + object);
        String data =  testData.getCellData(currentTest, data_column_name, testRepeat);
        String description = stepDescription + " " + data;
        Boolean flag = new Boolean(testData.getCellData(currentTest,data_column_name,testRepeat));
        if (!data.equalsIgnoreCase("@Skip")) {
            try {
                String price = "";
                String subTotal;
                double digitPrice;
                Double taxpercent = Double.parseDouble(data);
                WebElement tax = getElement(lparser.getobjectLocator("subtotal"));
                waitdriver.until(ExpectedConditions.visibilityOfAllElements(tax));

                subTotal = tax.getText();

                if (subTotal.contains("$"))
                    price = subTotal.substring(1).replace(",", "");

                else if (subTotal.contains("€"))
                    price = subTotal.substring(0, subTotal.length() - 1).replace(",", "");

                else if (subTotal.contains("Rs"))
                    price = subTotal.substring(3).replace(",", "");

                else if (subTotal.contains("£"))
                    price = subTotal.substring(1).replace(",", "");

                digitPrice = Double.parseDouble(price);

                WebElement verify = getElement(lparser.getobjectLocator(object));
                waitdriver.until(ExpectedConditions.visibilityOfAllElements(verify));

                String expectedTaxPrice = calculateTax(digitPrice, taxpercent);

                String actualTaxPrice = verify.getText();

                if (actualTaxPrice.contains(expectedTaxPrice)) {
                    APPLICATION_LOGS.info("verifyText keyword data :" + data);
                    writeToWord(description);
                }
            } catch (NoSuchElementException e) {
                APPLICATION_LOGS.debug("No Element Found to Verify the Text");
            } catch (Exception ex) {
                ex.printStackTrace();
                APPLICATION_LOGS.debug("Unable to verify" + object + ex.getMessage());
                return  "Fail";
            }
        }
        return "Pass";
    }

    public static String calculateTax(double digitPrice, double tax){
        double taxPercent = tax/100;
        String s = String.format("%,.2f", (digitPrice * taxPercent));
        return s;
    }


    public static  int getRandom (int maxIndex)
    {
        int minIndex = 0;
        Random r = new Random();
        return r.nextInt((maxIndex - minIndex) + 1) + minIndex;
    }
    public static String waitSleep() throws InterruptedException{
        APPLICATION_LOGS.debug("Executing the waitSleep Keyword");
        try{
            String data = testData.getCellData(currentTest, data_column_name, testRepeat);
            if (!data.equalsIgnoreCase("@Skip")) {
                int number = Integer.parseInt(data);
                Thread.sleep(number);
            }
        }
        catch(Exception e){
            APPLICATION_LOGS.debug("Unable to Wait" + object + e.getMessage());
            return "Fail";
        }
        return "Pass";
    }

    public static String waitTwoSec() throws InterruptedException{
        APPLICATION_LOGS.debug("Executing the waitSleep Keyword");
        try{
            Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        }
        catch(Exception e){
            APPLICATION_LOGS.debug("Unable to Wait" + object + e.getMessage());
            return "Fail";
        }
        return "Pass";
    }

    public static String wait30Sec() throws InterruptedException{
        APPLICATION_LOGS.debug("Executing the waitSleep Keyword");
        try{
            Uninterruptibles.sleepUninterruptibly(30, TimeUnit.SECONDS);
        }
        catch(Exception e){
            APPLICATION_LOGS.debug("Unable to Wait" + object + e.getMessage());
            return "Fail";
        }
        return "Pass";
    }

    public static String dragAndDrop(){
        APPLICATION_LOGS.debug("Executing the dragAndDrop Keyword");
        try{
            String data = testData.getCellData(currentTest, data_column_name, testRepeat);
            if(!data.equalsIgnoreCase("@Skip")){
                WebElement From = getElement(lparser.getobjectLocator(object));
                WebElement To = getElement(lparser.getobjectLocator(data));
                Actions builder = new Actions(driver);
                Action dragAndDrop = builder.clickAndHold(From).moveToElement(To).release(To).build();
                dragAndDrop.perform();
            }

        }
        catch(NoSuchElementException e){
            APPLICATION_LOGS.debug("No element found to perform drag and drop action"+ object + e.getMessage());
            return "Fail-";
        }
        catch(Exception ex){
            APPLICATION_LOGS.debug("Unable to perform the operation" + object + ex.getMessage());
            return "Fail-";
        }
        return "Pass";
    }
    public static String dragAndDropBy(){
        APPLICATION_LOGS.debug("Exceuting dragAndDropBy Keyword");
        try{
            String data = testData.getCellData(currentTest, data_column_name, testRepeat);
            int dataa = Integer.parseInt(data);
            if(!data.equalsIgnoreCase("@Skip")){
                WebElement From = getElement(lparser.getobjectLocator(object));
                Actions builder = new Actions(driver);
                builder.dragAndDropBy(From, dataa, dataa);
            }
        }
        catch(NoSuchElementException e){
            APPLICATION_LOGS.debug("No Element found to perform dragAndDropBy action" + object+e.getMessage());
            return "Fail-";
        }
        catch(Exception e){
            APPLICATION_LOGS.debug("Unable to perform the action" + object +e.getMessage() );
            return "Fail-";
        }
        return "Pass";
    }
    public static String doubleClick(){
        APPLICATION_LOGS.debug("Executing doubleClick Keyword");
        try{
            String data = testData.getCellData(currentTest, data_column_name, testRepeat);
            if(!data.equalsIgnoreCase("@Skip")){
                WebElement dclick = getElement(lparser.getobjectLocator(object));
                Actions action = new Actions(driver);
                action.moveToElement(dclick).doubleClick().build().perform();
            }
        }
        catch(NoSuchElementException e){
            APPLICATION_LOGS.debug("No Element found to perform the action double click"+ object +e.getMessage());
            return "Fail";
        }
        catch(Exception ex){
            APPLICATION_LOGS.debug("Unable to perform the action double Click" + object + ex.getMessage());
            return "Fail";
        }
        return "Pass";
    }
    public static String contextClick(){
        APPLICATION_LOGS.debug("Executing context click Keyword");
        try{
            String data = testData.getCellData(currentTest, data_column_name, testRepeat);
            if(!data.equalsIgnoreCase("@Skip")){
                WebElement click = getElement(lparser.getobjectLocator(object));
                Actions action = new Actions(driver).contextClick(click);
                action.build().perform();
            }}
        catch(NoSuchElementException e){
            APPLICATION_LOGS.info("No Element found Perform the action context click" + object + e.getMessage());
            return "Fail";
        }
        catch(Exception ex){
            APPLICATION_LOGS.debug("Unable to perform the action" + object + ex.getMessage());
            return "Fail";
        }
        return "Pass";
    }
    public static String click(){
        APPLICATION_LOGS.debug("Executing Click Keyword");
        try{
            String data = testData.getCellData(currentTest, data_column_name, testRepeat);
            if(!data.equalsIgnoreCase("@Skip")){
                WebElement click = getElement(lparser.getobjectLocator(object));
                Actions action = new Actions(driver);
                action.moveToElement(click).click().build().perform();
            }
        }
        catch(NoSuchElementException e){
            APPLICATION_LOGS.debug("No Element found to perform the action click"+ object +e.getMessage());
            return "Fail";
        }
        catch(Exception ex){
            APPLICATION_LOGS.debug("Unable to perform the action Click" + object + ex.getMessage());
            return "Fail";
        }
        return "Pass";
    }

    public static String closeWindow(){
        APPLICATION_LOGS.debug("Executing Close Window Keyword");
        try{
            String data = testData.getCellData(currentTest, data_column_name, testRepeat);
            if(!data.equalsIgnoreCase("@Skip")){
                driver.close();
            }

        }catch (Exception e){
            APPLICATION_LOGS.debug("Unable to perform the action" + object + e.getMessage());
            return "Fail";
        }
        return "Pass";
    }

    public static String pressKey()throws Exception
    {
        APPLICATION_LOGS.debug("Executing PressKey Keyword");
        try{
            String data = testData.getCellData(currentTest, data_column_name, testRepeat);
            if(!data.equalsIgnoreCase("@Skip")){
                WebElement press = getElement(lparser.getobjectLocator(object));
                System.out.println(data);
                press.sendKeys(data);
                //Actions build = new Actions(driver);
                //build.moveToElement(press);
                //build.sendKeys(Keys.ENTER).build().perform();
            }
        }catch(Exception e){
            APPLICATION_LOGS.debug("Unable to perform the action" + object + e.getMessage());
        }
        return "Pass";
    }

    public static String javaScriptExecutor(){
        APPLICATION_LOGS.debug("Executing javaScriptExecutor keyword");
        try{
            String data = testData.getCellData(currentTest, data_column_name, testRepeat);
            if(!data.equalsIgnoreCase("@Skip")){
                WebElement scroll = getElement(lparser.getobjectLocator(object));
                JavascriptExecutor js =(JavascriptExecutor)driver;
                js.executeScript("arguments[0].scrollIntoView(true);",scroll);
            }
        }
        catch(Exception e){
            APPLICATION_LOGS.debug("Executing javaScriptExecutor Keyword"+ object + e.getMessage());
            return "Fail-";
        }
        return "Pass";
    }

}

