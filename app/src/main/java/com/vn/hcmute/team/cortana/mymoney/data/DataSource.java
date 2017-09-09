package com.vn.hcmute.team.cortana.mymoney.data;

import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Image;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.model.RealTimeCurrency;
import com.vn.hcmute.team.cortana.mymoney.model.ResultConvert;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by infamouSs on 8/10/17.
 */

public interface DataSource {
    
    public interface RemoteDataSource {
        
        Observable<User> login(UserCredential userCredential);
        
        Observable<String> register(User user);
        
        Observable<String> forgetPassword(String email);
        
        Observable<String> changePassword(String userid, String token, String oldPassword,
                  String newPassword);
        
        Observable<String> changeProfile(String userid, String token, User user);
        
        Observable<List<Image>> getImage(String userid, String token);
        
        Observable<String> uploadImage(RequestBody userid, RequestBody token, RequestBody detail,
                  MultipartBody.Part file);
        
        Observable<Image> getImageById(String userid, String token, String imageid);
        
        Observable<String> removeImage(String userid, String token, String imageid);
        
        Observable<String> updateImage(String userid, String token, String imageid);
        
        
        Observable<String> createWallet(Wallet wallet, String userid, String token);
        
        Observable<String> updateWallet(Wallet wallet, String userid, String token);
        
        Observable<String> deleteWallet(String userid, String token, String idwallet);
        
        Observable<List<Wallet>> getAllWallet(String userid, String token);
        
        Observable<String> moveWallet(String userid, String token, String wallet1, String wallet2,
                  String money);
        
        Observable<List<Currencies>> getCurrencies();
    
        Observable<ResultConvert> convertCurrency(String amount,String from,String to);
    
        Observable<RealTimeCurrency> updateRealTimeCurrency();
        
        Observable<List<Event>> getEvent(String uerid, String token);
        
        Observable<String> createEvent(Event event, String userid, String token);
        
        Observable<String> updateEvent(Event event, String userid, String token);
        
        Observable<String> deleteEvent(String userid, String token, String idEvent);
        
        Observable<List<Saving>> getSaving(String userid, String token);
        
        Observable<String> createSaving(Saving saving, String userid, String token);
        
        Observable<String> updateSaving(Saving saving, String userid, String token);
        
        Observable<String> deleteSaving(String userid, String token, String idSaving);
        
        Observable<String> takeInSaving(String userid, String token, String idWallet,
                  String idSaving, String money);
        
        Observable<String> takeOutSaving(String userid, String token, String idWallet,
                  String idSaving, String money);
        
        Observable<List<Person>> getPerson(String userid, String token);
        
        Observable<String> addPerson(Person person, String userid, String token);
        
        Observable<String> removePerson(String userid, String token, String personid);
        
        Observable<String> updatePerson(Person person,String userid,String token);
        
        Observable<String> syncPerson(List<Person> persons,String userid,String token);
        
        Observable<List<Budget>> getBudget(String userid, String token);
        
        Observable<String> createBudget(Budget budget, String userid, String token);
        
        Observable<String> updateBudget(Budget budget, String userid, String token);
        
        Observable<String> deleteBudget(String userid, String token, String budgetId);
        
        
    }
    
    public interface CacheDataSource {
        
        void putUserId(String userid);
        
        void putUserToken(String token);
        
        void putUser(User user);
        
        void putLoginState(User user);
        
        void clearPreferenceCache();
        
        String getUserId();
        
        String getUserToken();
        
        User getUser();
        
        void removeUserId();
        
        void removeUserToken();
        
        void removeUser();
        
        void removeLoginStage();
    
        void putRealTimeCurrency(RealTimeCurrency realTimeCurrency);
    
        void removeRealTimeCurrency();
        
        RealTimeCurrency getRealTimeCurrency();
    }
    
    public interface LocalDataSource {
        
    }
}
