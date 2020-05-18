import Model.Category;
import Model.Product;
import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagementTest extends TestCase {
    @Test
    public void testSubManagementAddition(){
        ArrayList<String> attributes=new ArrayList<>();
        attributes.add("resolution");
        attributes.add("processor");
        HashMap<String,String> productAttribute=new HashMap<>();
        productAttribute.put("name","vivobook");
        productAttribute.put("brand","asus");
        productAttribute.put("resolution","50px");
        productAttribute.put("processor","intel core i7");
        new Category("electronic",null,attributes);
        //new Product("23434", (float) 23.1,Category.getCategoryByName("electronic"),productAttribute);
        assertEquals(true,Product.productWithIdExists("23434"));
    }
}
