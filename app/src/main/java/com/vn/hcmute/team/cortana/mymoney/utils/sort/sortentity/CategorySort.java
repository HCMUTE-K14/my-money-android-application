package com.vn.hcmute.team.cortana.mymoney.utils.sort.sortentity;

import com.vn.hcmute.team.cortana.mymoney.model.Category;

/**
 * Created by infamouSs on 9/18/17.
 */

public class CategorySort extends Sort<Category> {
    
    private String field;
    
    
    private CategorySort(String field) {
        this.field = field;
    }
    
    public static CategorySort createSortBy(String field) {
        if (field.equalsIgnoreCase("name")) {
            return new CategorySort("name");
        }
        return null;
    }
    
    @Override
    public int compare(Category o1, Category o2) {
        if (field.equalsIgnoreCase("name")) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
        
        return super.compare(o1, o2);
    }
}
