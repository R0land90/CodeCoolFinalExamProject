import io.qameta.allure.*;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.util.ArrayList;

public class TestSavingData extends TestBase{

    @RepeatedTest(1)
    @Epic("Hangszerdiszkont.hu")
    @Story("Scanning a multi-page list")
    @Description("Accessing guitar string pages")
    @DisplayName("TC16")
    @Severity(SeverityLevel.CRITICAL)
    public void SteppingOnMultiplePages() throws IOException, InterruptedException {
            //PAGEFACTORY

        Tools tools = (Tools) PageFactory.Create("Tools",driver);
        LandingPage landingPage = (LandingPage) PageFactory.Create("LandingPage",driver);
        GoodsPage goodsPage = (GoodsPage) PageFactory.Create("GSPage",driver);
        
            //TEST

        //Double-click on the web item to access the guitar categories
        tools.buttonClicker(landingPage.guitarCategoryButton,true);
        //Select a sub-category
        tools.buttonClicker(landingPage.guitarStringsButton,false);
        //Create a local integer variable to store the number of available pages
        int totalPagesNumber = tools.getTotalPagesNumberFromStringElement(goodsPage.pageSizeLoc);

        tools.paginizer(totalPagesNumber,goodsPage.goodsStringName,guitarStringList);
        /*
        //Create a local String variable that will be the URL base of your loop.
        String pageURL = tools.getGoodsURL();
        //Create a for loop that will allow you to add the page number to the URL you just stored, one by one, and extract the data you want to an ArrayList.
        for (int i =2; i<totalPagesNumber+2; i++){
            tools.getTextToArrayList(goodsPage.goodsStringName,guitarStringList);
            if (i<totalPagesNumber+1){
                String newPageURL = pageURL.concat(String.valueOf(i));
                driver.get(newPageURL);}
        }

        */

        //Finally, save your data to a file.
        tools.saveToJson(guitarStringList,PageBase.guitarStringsFilePath);

                //ASSERT

        // Read file contents
        JSONArray jsonArray = tools.readJsonArray(PageBase.guitarStringsFilePath);
        // Convert JSONArray to String Array
        ArrayList<String> jsList = tools.jSonArrayToStringArray(jsonArray);
        // Compare
        Assertions.assertEquals(guitarStringList,jsList);
    }
}
