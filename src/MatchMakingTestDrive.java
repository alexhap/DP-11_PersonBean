/**
 * Created by alex on 02.07.2015.
 *
 */

import java.lang.reflect.Proxy;
import java.util.Hashtable;

public class MatchMakingTestDrive {

    Hashtable<String, PersonBean> datingDB = new Hashtable<>();

    public MatchMakingTestDrive() {
        initializeDatabase();
    }

    public void run() {
        PersonBean joe = getPersonFromDatabase("Joe");
        PersonBean ownerProxy = getOwnerProxy(joe);
        System.out.println("Name is " + ownerProxy.getName());
        ownerProxy.setInterests("Bowling");
        System.out.println("Interests set from owner proxy");
        try {
            ownerProxy.setHotOrNotRating(10);
        } catch (Exception e) {
            System.out.println("Can't set rating from owner proxy");
        }
        System.out.println("Rating is " + ownerProxy.getHotOrNotRating());

        PersonBean nonOwnerProxy = getNonOwnerProxy(joe);
        System.out.println("Name is " + nonOwnerProxy.getName());
        try {
            nonOwnerProxy.setInterests("Bowling, Go");
        } catch (Exception e) {
            System.out.println("Can't set interests from non owner proxy");
        }
        nonOwnerProxy.setHotOrNotRating(3);
        System.out.println("Rating set from non owner proxy");
        System.out.println("Interests are " + nonOwnerProxy.getInterests());
        System.out.println("Rating is " + nonOwnerProxy.getHotOrNotRating());
    }

    private PersonBean getOwnerProxy(PersonBean person) {
        return (PersonBean) Proxy.newProxyInstance(person.getClass().getClassLoader(),
                person.getClass().getInterfaces(), new OwnerInvocationHandler(person));
    }

    private PersonBean getNonOwnerProxy(PersonBean person) {
        return (PersonBean) Proxy.newProxyInstance(person.getClass().getClassLoader(),
                person.getClass().getInterfaces(), new NonOwnerInvocationHandler(person));
    }

    private PersonBean getPersonFromDatabase(String name) {
        return datingDB.get(name);
    }

    private void initializeDatabase() {
        PersonBean person = new PersonBeanImpl();
        person.setName("Joe");
        person.setGender("male");
        person.setInterests("Internet");
        person.setHotOrNotRating(5);
        datingDB.put(person.getName(), person);

        person = new PersonBeanImpl();
        person.setName("Kelly");
        person.setGender("female");
        person.setInterests("Cooking");
        person.setHotOrNotRating(6);
        datingDB.put(person.getName(), person);
    }

    public static void main(String args[]) {
        MatchMakingTestDrive testDrive = new MatchMakingTestDrive();
        testDrive.run();
    }
}
