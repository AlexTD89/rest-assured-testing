package Utilities;

public class Driver_with_Enum {
    /**
     * this method uses enum special class to get specific values for the driver.
     * enums are good when we need to have limited amount of choice
     *
     * @param browser
     * @return
     */

    public static String getDriver(Browser browser){
        //create a new webDriver based on given browser type
        switch (browser){
            case CHROME:
                 return "chrome driver";
            case FIREFOX:
                return "fire-fox";
        }

        return null;
    }

    public static void main(String[] args) {
        String driver = getDriver(Browser.CHROME);
        System.out.println("driver = " + driver); // driver = chrome driver
    }
}
