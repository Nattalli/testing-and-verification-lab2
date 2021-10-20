package sysProg2;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

import java.io.*;
import java.util.Scanner;


public class FirstTest {

    public Scanner text, text1;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeTest(groups = { "test" })
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test(groups = { "test" })
    public void AnswerNo(){
        String input = "no.txt 2";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Main2.main(null);

        Assert.assertEquals("Enter file name Enter k: NO", outContent.toString().trim());
    }


    @BeforeMethod(groups = { "test", "parametrized" })
    public void BasicTest() throws FileNotFoundException {
        this.text = Main2.getScanner("1.txt");
    }

    @Test(groups = { "test" })
    public void FileReading(){
        String second = "java.util.Scanner[delimiters=\\p{javaWhitespace}+][position=0][match valid=false][need input=false][source closed=false][skipped=false][group separator=\\x{a0}][decimal separator=\\x{2c}][positive prefix=][negative prefix=\\Q-\\E][positive suffix=][negative suffix=][NaN string=\\QNaN\\E][infinity string=\\Q∞\\E]";
        Assert.assertEquals(this.text.toString(), second, "Scanners are equals!");
    }

    @Test(groups = { "test" })
    public void Exception(){
        Assert.assertFalse(Boolean.parseBoolean(String.valueOf(new Main2.CompleteStepNotPossibleException("Error!"))));
    }

    @Test(groups = { "test" })
    public void NotEmptyFile() throws FileNotFoundException {
        this.text1 = Main2.getScanner("2.txt");
        Assert.assertNotNull(this.text);
    }

    @Test(groups = { "test" })
    public void AreEquals() throws FileNotFoundException{
        this.text1 = Main2.getScanner("2.txt");
        Assert.assertEquals(this.text1.toString(), this.text.toString());
    }

    @Test(groups = { "test" })
    public void DifferentScanners(){
        Assert.assertNotEquals(this.text, this.text1);
    }

    @DataProvider (name = "data-provider")
    public Object[][] dpMethod(){
        return new Object[][] {{"1.txt"}, {"2.txt"}, {"3.txt"}};
    }

    @Test ( groups = {"parametrized"},dataProvider = "data-provider")
    public void AllFiles (String val) {
        Assert.assertNotNull(val.toString());
    }

}
