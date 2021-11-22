import org.junit.jupiter.api.Test;
import org.junit.platform.console.ConsoleLauncher;

import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarCatalogTests {
    
	//Data Wrangler Test Methods
    /**
     * role data wrangler
     *
     * @author Simran
     *
     */
    //Test1 for role data wrangler
    //Test to check if all entries are loaded correctly
    @Test
    public void datawrangler_testSize()
    {
        CarLoader load = new CarLoader();
        try{
            List<CarData> carlist = load.loadFile("./data/dataset.csv");
            assertEquals(2499,carlist.size());
        }
        catch(FileNotFoundException e){
            assertTrue(false);
        }
    }

    /**
     * role data wrangler
     *
     * @author Simran
     *
     */
    //Test2 for role data wrangler
    //Test to check if the data is in correct format
    @Test
    public void datawrangler_testFormat()
    {
        CarData a = new CarData("chevrolet","1500",31900);
        CarLoader load = new CarLoader();
        try{
            List<CarData> carlist = load.loadFile("./data/dataset.csv");
            assertEquals(a.getMake(),carlist.get(12).getMake());
        }
        catch(FileNotFoundException e){
            assertTrue(false);
        }
    }

    /**
     * role data wrangler
     *
     * @author Simran
     *
     */
    //Test3 for role data wrangler
    //Test to check if exception works properly
    @Test
    public void datawrangler_testException()
    {
        int flag =1;
        CarLoader load = new CarLoader();
        try{
            List<CarData> carlist = load.loadFile("./data/not_a_dataset.csv");
        }
        catch(FileNotFoundException e)
        {
            flag=0;
        }
        assertEquals(0,flag);
    }


    public static void main(String[] args) {
        String className = MethodHandles.lookup().lookupClass().getName();
        String classPath = System.getProperty("java.class.path").replace(" ", "\\ ");
        String[] arguments = new String[] {
                "-cp",
                classPath,
                "--include-classname=.*",
                "--select-class=" + className };
        ConsoleLauncher.main(arguments);
    }

    /** 
     * Front end test methods
     * @author Ojas Sethi
     */
    @Test
    public static void test1() {
        SearchFrontEnd frontEngine = new SearchFrontEnd();
        SearchBackEnd backEngine = new SearchBackEnd();
        try {
            frontEngine.run(backEngine);
            assertTrue(true); // Passes if the run method works
        } catch (Exception e) {
            assertTrue(false); // Fails otherwise if any exception is caught
        }

    }
}
