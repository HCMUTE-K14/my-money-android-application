package com.vn.hcmute.team.cortana.mymoney.utils.sort.helper;

import com.vn.hcmute.team.cortana.mymoney.model.Person;
import java.util.Comparator;

/**
 * Created by infamouSs on 8/26/17.
 */

public class ComparatorFactory {
    
    
    private static final String PERSON = "Person";
    
    public static <T> Comparator<T> getCompartor(Class<T> clazz) {
        switch (clazz.getSimpleName()) {
            case PERSON:
                Comparator<Person> comparator = new Comparator<Person>() {
                    @Override
                    public int compare(Person o1, Person o2) {
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    }
                };
                return (Comparator<T>) comparator;
            default:
                break;
        }
        return null;
    }
}
