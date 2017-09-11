package com.vn.hcmute.team.cortana.mymoney.utils.sort.helper;

import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.utils.sort.sortentity.Sort;
import com.vn.hcmute.team.cortana.mymoney.utils.sort.sortentity.PersonSort;

/**
 * Created by infamouSs on 8/23/17.
 */

public class SortFactory {
    
    public static Sort getSort(Class clazz, String field) {
        if (clazz.getSimpleName().equals(Person.TAG)) {
            return PersonSort.createSortBy(field);
        }
        return null;
    }
    
}
