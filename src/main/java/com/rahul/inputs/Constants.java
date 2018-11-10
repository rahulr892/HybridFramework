package com.rahul.inputs;

/*############################################################
 * ' Class Name: Constants
 * ' Purpose: constants in project along with file paths
 */
public class Constants {

    public final static String Win7_Chrome = "Win7_Chrome";
    public final static String Win7_IE = "Win7_IE";
    public final static String Win10_FF = "Win10_FF";
    public final static String Mac_Chrome = "Mac_Chrome";
    public final static String Mac_Safari = "Mac_Safari";

    //=========================================================================
    // Constant paths

    private final static String rootDir = System.getProperty("user.dir");

    public final static String TESTDATA_LOC = rootDir + "/src/main/java/com/rahul/inputs/TestData.xlsx";

    public final static String CONFIG_LOC = rootDir + "/src/main/java/com/rahul/inputs/Config.xlsx";

    public final static String FILES_FOR_UPLOAD_LOC = rootDir + "/src/main/java/com/rahul/samplefiles/";

    public final static String ENV_PROPERTIES = rootDir + "/src/main/resources/environment.properties";

    public final static String SSH_KEY = rootDir + "/src/main/resources/smk.ppk";

}

