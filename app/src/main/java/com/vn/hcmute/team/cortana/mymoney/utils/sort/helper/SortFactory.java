package com.vn.hcmute.team.cortana.mymoney.utils.sort.helper;

import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.utils.sort.sortentity.CategorySort;
import com.vn.hcmute.team.cortana.mymoney.utils.sort.sortentity.PersonSort;
import com.vn.hcmute.team.cortana.mymoney.utils.sort.sortentity.Sort;

/**
 * Created by infamouSs on 8/23/17.
 */

public class SortFactory {
    
    public static Sort getSort(Class clazz, String field) {
        if (clazz.getSimpleName().equals(Person.TAG)) {
            return PersonSort.createSortBy(field);
        } else if (clazz.getSimpleName().equals(Category.TAG)) {
            return CategorySort.createSortBy(field);
        }
        return null;
    }
    
}
