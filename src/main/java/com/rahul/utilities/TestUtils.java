package com.rahul.utilities;

import com.rahul.inputs.Constants;

import java.util.Hashtable;


public class TestUtils {

    private static Xls_Reader xls = new Xls_Reader(Constants.CONFIG_LOC);

    /*############################################################
     * ' Function Name: getData
     * ' Purpose: Read inputs row excel
     * ' Inputs Parameters: Sheetname
     * ' Returns: Hashtable
     */
    public static Object[][] getData(String sheetName, Xls_Reader xls) {
        int rowCount = xls.getRowCount(sheetName) - 1;
        int columnCount = xls.getColumnCount(sheetName);

        int c = 0;
        for (int k = 2; k < rowCount + 2; k++) {
            if (!xls.getCellData(sheetName, 0, k).equalsIgnoreCase("Y"))
                c++;
        }
        int d = 0;
        int row = rowCount - c;

        Object[][] data = new Object[row][1];
        Hashtable<String, String> table;
        for (int i = 2; i <= rowCount + 1; i++) {
            if (xls.getCellData(sheetName, 0, i).equalsIgnoreCase("Y")) {
                table = new Hashtable<String, String>();
                for (int j = 0; j < columnCount; j++) {
                    String key = xls.getCellData(sheetName, j, 1).trim();
                    String val = xls.getCellData(sheetName, j, i).trim();
                    table.put(key, val);
                }
                data[d][0] = table;
                d++;
            }
        }
        return data;
    }


    /*############################################################
     * ' Function Name: getTestData
     * ' Purpose: To get the testdata row excel
     * ' Inputs Parameters: Hashtable, column name
     * ' Returns: data
     */
    public static String getTestData(Hashtable<String, String> testdata, String colName) {
        String data = "";
        try {
            data = testdata.get(colName);
        } catch (Exception e) {
            System.out.println(colName + " : does not exist in datasheet");
        }
        return data;
    }


    /*############################################################
     * ' Function Name: isTestExecutable
     * ' Purpose: To find whether the current test to be executed
     * ' Inputs Parameters: String
     * ' Returns: boolean
     */
    public static synchronized boolean isTestExecutable(String test) {

        for (int i = 2; i <= xls.getRowCount("Execution"); i++) {
            if (xls.getCellData("Execution", "TestName", i).trim().equalsIgnoreCase(test)) {
                if (xls.getCellData("Execution", "RunMode", i).trim().contains("Y")) {
                    return true;
                }
            }
        }
        return false;
    }


}
