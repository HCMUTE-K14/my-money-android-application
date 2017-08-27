package com.vn.hcmute.team.cortana.mymoney.utils.sort.modelsort;

import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.utils.sort.Sort;

/**
 * Created by infamouSs on 8/26/17.
 */

public class PersonSort extends Sort<Person> {
    
    private String field;
    
    private PersonSort(String field) {
        this.field = field;
    }
    
    public static PersonSort createSortBy(String field) {
        if (field.equalsIgnoreCase("name")) {
            return new PersonSort("name");
        }
        return null;
    }
    
    @Override
    public int compare(Person o1, Person o2) {
        if (field.equalsIgnoreCase("name")) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
        
        return super.compare(o1, o2);
    }
}
