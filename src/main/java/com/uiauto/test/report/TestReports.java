package com.uiauto.test.report;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.uiauto.action.utils.TestUtil;
import com.uiauto.test.app.DriverApp;

public class TestReports {
    public static int scriptNumber = 1;
    public static String indexResultFilename;
    // public static String currentDir;
    public static String currentSuiteName;
    public static int tcid;
    // public static String currentSuitePath;

    public static double passNumber;
    public static double failNumber;
    public static boolean newTest = true;
    public static ArrayList<String> description = new ArrayList<String>();;
    public static ArrayList<String> keyword = new ArrayList<String>();;
    public static ArrayList<String> teststatus = new ArrayList<String>();;
    public static ArrayList<String> screenShotPath = new ArrayList<String>();;
    public static ArrayList<String> videopath = new ArrayList<String>();;
    public static String currentDir = System.getProperty("user.dir");
    public static String separator=System.getProperty("file.separator");

    public static void startTesting(String filename, String testStartTime, String env, String rel) {
        indexResultFilename = filename;
        currentDir = indexResultFilename.substring(0, indexResultFilename.lastIndexOf(System.getProperty("file.separator")));

        System.out.println("Current directory is: " + currentDir+System.getProperty("file.separator"));
        System.out.println("Current filename is: " + indexResultFilename);

        FileWriter fstream = null;
        BufferedWriter out = null;
        try {
            // Create file

            fstream = new FileWriter(filename);
            out = new BufferedWriter(fstream);
            String RUN_DATE = TestUtil.now("dd.MMMMM.yyyy").toString();
            String PROJECT = env;

            out.newLine();
            out.write("<html>\n");
            out.write("<HEAD>\n");
            out.write("<td width=150 align=left><FONT COLOR=#0A4984 FACE=Arial SIZE=2.75> <img src='Freshworks.png' align=left></img></td>\n");
            out.write(" <TITLE>Automation Test Results by QA Team</TITLE>\n");
            out.write("</HEAD>\n");
            out.write("<body>\n");
            out.write("<table  border=0 cellspacing=0 cellpadding=0 align=center>\n");
            out.write("<tr>\n");
            out.write("</tr>\n");
            out.write("</table>\n");
            out.write("<tr>\n");
            out.write("<tr>\n");
            out.write("<tr>\n");
            out.write("<h2 align=center><FONT COLOR=#0A4984 FACE=AriaL SIZE=6><b><center>Automation Test-Results</center></b></h2>\n");
            out.write("<table  border=1 cellspacing=1 cellpadding=3 >\n");
            out.write("<tr>\n");
            out.write("<tr>\n");
            out.write("<br>");
            out.write("<h4> <FONT COLOR=black FACE=Arial SIZE=4.5> <u>Test Details :</u></h4>");
            out.write("<td width=150 align=left bgcolor=#58ACFA><FONT COLOR=#0A4984 FACE=Arial SIZE=2.75><b>Run Date</b></td>\n");
            out.write("<td width=150 align=left><FONT COLOR=black FACE=Arial SIZE=2.75><b>" + RUN_DATE + "</b></td>\n");
            out.write("</tr>\n");
            out.write("<tr>\n");
            out.write("<td width=150 align=left bgcolor=#58ACFA><FONT COLOR=#0A4984 FACE=Arial SIZE=2.75><b>Run StartTime</b></td>\n");
            out.write("<td width=150 align=left><FONT COLOR=black FACE=Arial SIZE=2.75><b>" + testStartTime + "</b></td>\n");
            out.write("</tr>\n");
            out.write("<tr>\n");
            // out.newLine();
            out.write("<td width=150 align= left  bgcolor=#58ACFA><FONT COLOR=#0A4984 FACE= Arial  SIZE=2.75><b>Run EndTime</b></td>\n");
            out.write("<td width=150 align= left ><FONT COLOR=black FACE= Arial  SIZE=2.75><b>END_TIME</b></td>\n");
            out.write("</tr>\n");
            out.write("<tr>\n");
            // out.newLine();
            out.write("<td width=150 align= left  bgcolor=#58ACFA><FONT COLOR=#0A4984 FACE= Arial  SIZE=2.75><b>Project</b></td>\n");
            out.write("<td width=150 align= left ><FONT COLOR=black FACE= Arial  SIZE=2.75><b>" + PROJECT + "</b></td>\n");
            out.write("</tr>\n");
            out.write("<tr>\n");

            /*out.write("<td width=150 align= left  bgcolor=#58ACFA><FONT COLOR=#0A4984 FACE= Arial  SIZE=2.75><b>Release</b></td>\n");
            out.write("<td width=150 align= left ><FONT COLOR=black FACE= Arial  SIZE=2.75><b>" + RELEASE + "</b></td>\n");
            out.write("</tr>\n");
            out.write("</tr>\n");*/
            
            out.write("<td width=150 align= left  bgcolor=#58ACFA><FONT COLOR=#0A4984 FACE= Arial  SIZE=2.75><b>Run By</b></td>\n");
            out.write("<td width=150 align= left ><FONT COLOR=black FACE= Arial  SIZE=2.75><b>ITPPM QA</b></td>\n");
            out.write("</tr>\n");

            out.write("</table>\n");

            // Close the output stream
            out.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        } finally {

            fstream = null;
            out = null;
        }
    }

    public static void startSuite(String suiteName) {

        FileWriter fstream = null;
        BufferedWriter out = null;
        currentSuiteName = suiteName.replaceAll(" ", "_");

        tcid = 1;
        try {
            fstream = new FileWriter(indexResultFilename, true);
            out = new BufferedWriter(fstream);
            out.write("<h4> <FONT COLOR=black FACE= Arial  SIZE=4.5> <u>" + suiteName + " Report :</u></h4>\n");
            out.write("<table  border=1 cellspacing=1 cellpadding=1 width=100%>\n");
            out.write("<tr>\n");
            out.write("<td width=10%  align= center  bgcolor=#58ACFA><FONT COLOR=#0A4984 FACE= Arial  SIZE=2><b>Test Script#</b></td>\n");
            out.write("<td width=40% align= center  bgcolor=#58ACFA><FONT COLOR=#0A4984 FACE= Arial  SIZE=2><b>Test Case Name</b></td>\n");
            out.write("<td width=10% align= center  bgcolor=#58ACFA><FONT COLOR=#0A4984 FACE= Arial  SIZE=2><b>Status</b></td>\n");
            out.write("<td width=20% align= center  bgcolor=#58ACFA><FONT COLOR=#0A4984 FACE= Arial  SIZE=2><b>Run Start Time</b></td>\n");
            out.write("<td width=20% align= center  bgcolor=#58ACFA><FONT COLOR=#0A4984 FACE= Arial  SIZE=2><b>Run End Time</b></td>\n");
            out.write("</tr>\n");
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            fstream = null;
            out = null;
        }
    }

    public static void endSuite() {
        FileWriter fstream = null;
        BufferedWriter out = null;

        try {
            fstream = new FileWriter(indexResultFilename, true);
            System.out.println(indexResultFilename);
            out = new BufferedWriter(fstream);
            out.write("</table>\n");
            out.write("</body>\n");
            out.write("</html>\n");
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {

            fstream = null;
            out = null;
        }

    }

    public static void addTestCase(String testCaseName, String testCaseStartTime, String testCaseEndTime, String status) throws Exception {
        newTest = true;
        FileWriter fstream = null;
        BufferedWriter out = null;

        try {
            newTest = true;
            // build the keywords page
            if (status.equalsIgnoreCase("Skipped") || status.equalsIgnoreCase("Skip")) {

            } else {
                // changed for filepath
                File f = new File(currentDir+System.getProperty("file.separator")+ currentSuiteName + "_TC" + tcid + "_" + testCaseName.replaceAll(" ", "_") + DriverApp.year + "_"
                        + DriverApp.date + "_" + (DriverApp.month + 1) + "_" + DriverApp.day + "_" + DriverApp.min + "_" + DriverApp.sec + ".html");
                
                f.createNewFile();
                fstream = new FileWriter(currentDir+System.getProperty("file.separator")+currentSuiteName + "_TC" + tcid + "_" + testCaseName.replaceAll(" ", "_") + DriverApp.year + "_"
                        + DriverApp.date + "_" + (DriverApp.month + 1) + "_" + DriverApp.day + "_" + DriverApp.min + "_" + DriverApp.sec + ".html");
                
                out = new BufferedWriter(fstream);
                out.write("<html>");
                out.write("<head>");
                out.write("<title>");
                out.write(testCaseName + " Detailed Reports");
                out.write("</title>");
                out.write("</head>");
                out.write("<body>");
                out.write("<h4> <FONT COLOR=660000 FACE=Arial SIZE=4.5> Detailed Report :</h4>");
                out.write("<table  border=1 cellspacing=1    cellpadding=1 width=100%>");
                out.write("<tr> ");
                out.write("<td align=center width=10%  align=center bgcolor=#58ACFA><FONT COLOR=#0A4984 FACE=Arial SIZE=2><b>Step/Row#</b></td>");
                out.write("<td align=center width=50% align=center bgcolor=#58ACFA><FONT COLOR=#0A4984 FACE=Arial SIZE=2><b>Description</b></td>");
                out.write("<td align=center width=10% align=center bgcolor=#58ACFA><FONT COLOR=#0A4984 FACE=Arial SIZE=2><b>Keyword</b></td>");
                out.write("<td align=center width=15% align=center bgcolor=#58ACFA><FONT COLOR=#0A4984 FACE=Arial SIZE=2><b>Result</b></td>");
                out.write("<td align=center width=15% align=center bgcolor=#58ACFA><FONT COLOR=#0A4984 FACE=Arial SIZE=2><b>Screen Shot</b></td>");
                out.write("</tr>");

                if (description != null) {
                    for (int i = 0; i < description.size(); i++) {
                        out.write("<tr> ");
                        out.write("<td align=center width=10%><FONT COLOR=black FACE=Arial SIZE=1><b>TS" + (i + 1) + "</b></td>");
                        out.write("<td align=center width=50%><FONT COLOR=black FACE=Arial SIZE=1><b>" + description.get(i) + "</b></td>");
                        out.write("<td align=center width=10%><FONT COLOR=black FACE=Arial SIZE=1><b>" + keyword.get(i) + "</b></td>");
                        
                        if (teststatus.get(i).startsWith("Pass")) {
                            out.write("<td width=20% align= center  bgcolor=#BCE954><FONT COLOR=black FACE=Arial SIZE=2><b>" + teststatus.get(i)
                                    + "</b></td>\n");
                        } else if (teststatus.get(i).startsWith("Fail")) {

                            out.write("<td width=20% align= center  bgcolor=red><FONT COLOR=black FACE= Arial  SIZE=2><b>" + teststatus.get(i) + "</b></td>\n");
                        }
                        // out.write("<td align=center width=20%><FONT COLOR=#153E7E FACE=Arial SIZE=1><b>"+teststatus.get(i)+"</b></td>");

                        if (teststatus.get(i).startsWith("Fail")) {

                            // TestUtil.startRecording();
                            if (screenShotPath.get(i) != null) {
                                out.write("<td align=center width=20%><FONT COLOR=black FACE=Arial SIZE=1><b><a href=" + screenShotPath.get(i)
                                        + " target=_blank>Screen Shot</a></b></td>");
                                //System.out.println("Screenshot path:"+screenShotPath.get(i));
                                // TestUtil.stopRecording();
                            } else
                                out.write("<td align=center width=20%><FONT COLOR=black FACE=Arial SIZE=1><b>&nbsp;</b></td>");
                            out.write("</tr>");
                        }

                        if (teststatus.get(i).startsWith("Pass")) {

                            // TestUtil.startRecording();
                            if (screenShotPath.get(i) != null) {
                                out.write("<td align=center width=20%><FONT COLOR=black FACE=Arial SIZE=1><b><a href=" + screenShotPath.get(i)
                                        + " target=_blank>Screen Shot</a></b></td>");
                                //System.out.println("Screenshot path:"+screenShotPath.get(i));
                                // TestUtil.stopRecording();
                            }

                            else
                                out.write("<td align=center width=20%><FONT COLOR=black FACE=Arial SIZE=1><b>&nbsp;</b></td>");
                            out.write("</tr>");
                        }
                    }
                }

                out.close();

            }

            fstream = new FileWriter(indexResultFilename, true);
            out = new BufferedWriter(fstream);

            fstream = new FileWriter(indexResultFilename, true);
            out = new BufferedWriter(fstream);
            // out.newLine();

            out.write("<tr>\n");
            // System.out.println(currentSuitePath);
            out.write("<td width=10% align= center ><FONT COLOR=black FACE= Arial  SIZE=2><b>" + scriptNumber + "</b></td>\n");
            if (status.equalsIgnoreCase("Skipped") || status.equalsIgnoreCase("Skip"))
                out.write("<td width=40% align= center ><FONT COLOR=black FACE= Arial  SIZE=2><b>" + testCaseName + "</b></td>\n");
            else
               
                out.write("<td width=40% align= center ><FONT COLOR=black FACE= Arial  SIZE=2><b><a href=" + currentSuiteName
                        + "_TC" + tcid + "_" + testCaseName.replaceAll(" ", "_") + DriverApp.year + "_" + DriverApp.date + "_" + (DriverApp.month + 1) + "_"
                        + DriverApp.day + "_" + DriverApp.min + "_" + DriverApp.sec + ".html>" + testCaseName + "</a></b></td>\n");
            
            tcid++;

            if (status.startsWith("Pass"))
                out.write("<td width=10% align= center  bgcolor=#BCE954><FONT COLOR=black FACE=Arial SIZE=2><b>" + status + "</b></td>\n");
            else if (status.startsWith("Fail"))
                out.write("<td width=10% align= center  bgcolor=red><FONT COLOR=black FACE= Arial  SIZE=2><b>" + status + "</b></td>\n");

            else if (status.equalsIgnoreCase("Skipped") || status.equalsIgnoreCase("Skip"))
                out.write("<td width=10% align= center  bgcolor=yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>" + status + "</b></td>\n");

            out.write("<td width=20% align= center ><FONT COLOR=black FACE= Arial  SIZE=2><b>" + testCaseStartTime + "</b></td>\n");
            out.write("<td width=20% align= center ><FONT COLOR=black FACE= Arial  SIZE=2><b>" + testCaseEndTime + "</b></td>\n");

            out.write("</tr>\n");

            scriptNumber++;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        description = new ArrayList<String>();
        keyword = new ArrayList<String>();
        teststatus = new ArrayList<String>();
        screenShotPath = new ArrayList<String>();
        videopath = new ArrayList<String>();
        newTest = false;
    }

    public static void addKeyword(String desc, String key, String stat, String path) {

        description.add(desc);
        keyword.add(key);
        teststatus.add(stat);
        screenShotPath.add(path);

    }

    public static void updateEndTime(String endTime) {
        StringBuffer buf = new StringBuffer();
        try {
            // Open the file that is the first
            // command line parameter
            FileInputStream fstream = new FileInputStream(indexResultFilename);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            // Read File Line By Line

            while ((strLine = br.readLine()) != null) {

                if (strLine.indexOf("END_TIME") != -1) {
                    strLine = strLine.replace("END_TIME", endTime);
                }
                buf.append(strLine);

            }
            // Close the input stream
            in.close();
            System.out.println(buf);
            FileOutputStream fos = new FileOutputStream(indexResultFilename);
            DataOutputStream output = new DataOutputStream(fos);
            output.writeBytes(buf.toString());
            fos.close();

        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

}