/**
 * Created by alex on 02.07.2015.
 *
 */

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class OwnerInvocationHandler implements InvocationHandler {
    PersonBean person;

    public OwnerInvocationHandler(PersonBean person) {
        this.person = person;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().startsWith("get")) return method.invoke(person, args);
        else if (method.getName().equals("setHotOrNotRating")) return new IllegalAccessException();
        else if (method.getName().startsWith("set")) return method.invoke(person, args);
        return null;
    }
}
