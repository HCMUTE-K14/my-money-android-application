package com.vn.hcmute.team.cortana.mymoney.usecase.base;

/**
 * Created by infamouSs on 8/11/17.
 */

/**
 * Abstract UseCase
 *
 * @param <Q> request value
 */
public abstract class UseCase<Q extends UseCase.RequestValue> {
    

    
    private Q mRequestValues;
    
    public Q getRequestValues() {
        return mRequestValues;
    }
    
    public void setRequestValues(Q requestValues) {
        mRequestValues = requestValues;
    }
    
    public abstract void subscribe(Q requestValues);
    
    public abstract void unSubscribe();
    
    public interface RequestValue {
        
    }
}
