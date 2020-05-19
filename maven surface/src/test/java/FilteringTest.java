import Controller.ProductPageController;
import Model.Category;
import junit.framework.TestCase;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Test;

public class FilteringTest extends TestCase {

    @Tested
    ProductPageController controller;

    @Injectable
    Category category;


    @Test
    public void testProductFilter(){

        new Expectations(){
            {
                Category.categoryWithNameExists("digital");result=true;
                category.getSpecialAttributes().contains("size");result=true;
            }
        };
        controller.addFilter("size","14 inch");
        controller.addFilter("category","digital");
        controller.addFilter("size","15 inch");

        assertEquals(false,controller.getCurrentFilters().get("size").contains("14 inch"));
        assertEquals(true,controller.getCurrentFilters().get("size").contains("15 inch"));

    }
}
