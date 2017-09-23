package com.vn.hcmute.team.cortana.mymoney.utils.sort;

import com.vn.hcmute.team.cortana.mymoney.utils.sort.helper.SortException;
import com.vn.hcmute.team.cortana.mymoney.utils.sort.helper.SortFactory;
import com.vn.hcmute.team.cortana.mymoney.utils.sort.sortentity.Sort;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by infamouSs on 8/26/17.
 */

public class SortUtil {
    
    public static <T> List<T> sort(List<T> data, Class<T> clazz, String field, SortType sortType) {
        Sort sort = SortFactory.getSort(clazz, field);
        if (sort == null) {
            throw new SortException("Cannot create sort");
        }
        List<T> result = new ArrayList<>(data);
        if (sortType == SortType.A_Z) {
            Collections.sort(result, sort);
        } else if (sortType == SortType.Z_A) {
            Collections.sort(result, Collections.reverseOrder(sort));
        }
        
        return result;
    }
    
}
