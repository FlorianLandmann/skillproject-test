package lieblingsfarbe.model;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LieblingsfarbeTest {
    @Test
    public void validateSettersAndGetters() throws Exception{


        PojoClass activityPojo = PojoClassFactory.getPojoClass(Lieblingsfarbe.class);

        Validator validator = ValidatorBuilder.create()
                // Lets make sure that we have a getter and a setter for every field defined.
                .with(new SetterMustExistRule()).with(new GetterMustExistRule())

                // Lets also validate that they are behaving as expected
                .with(new SetterTester()).with(new GetterTester()).build();

        // Start the Test
        validator.validate(activityPojo);
    }
    @Test
    public void validateIsValid() throws Exception {
        Lieblingsfarbe lieblingsfarbe_null = new Lieblingsfarbe(null);
        assertEquals("isNotValid_null", false, lieblingsfarbe_null.isValid());
        Lieblingsfarbe lieblingsfarbe_empty = new Lieblingsfarbe("");
        assertEquals("isNotValid_empty", false, lieblingsfarbe_empty.isValid());
        Lieblingsfarbe lieblingsfarbe = new Lieblingsfarbe("blau");
        assertEquals("isValid", true, lieblingsfarbe.isValid());
    }
}
