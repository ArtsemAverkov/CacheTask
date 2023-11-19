package cachetask.extension.discount;

import cachetask.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class ValidParameterResolverUser implements ParameterResolver {
    public static List<User> validDiscount = Arrays.asList(
            new User(
                    2L,
                    "name",
                    "lastName",
                    "email"),
            new User(
                    2L,
                    "name1",
                    "lastName1",
                    "email1"));

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType()==User.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return validDiscount.get(new Random().nextInt(validDiscount.size()));
    }
}
