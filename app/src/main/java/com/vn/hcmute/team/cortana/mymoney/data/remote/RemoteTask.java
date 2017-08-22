package com.vn.hcmute.team.cortana.mymoney.data.remote;

import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Image;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by infamouSs on 8/10/17.
 */

public interface RemoteTask {
    
    public interface UserTask {
        
        Observable<User> login(UserCredential userCredential);
        
        Observable<String> register(User user);
    }
    
    public interface ImageTask {
        
        Observable<List<Image>> getImage(String userid, String token);
        
        Observable<String> uploadImage();
    }
    
    interface WalletTask {
        
        Observable<String> createWallet(Wallet wallet, String userid, String token);
        
        Observable<String> updateWallet(Wallet wallet, String userid, String token);
        
        Observable<String> deleteWallet(String userid, String token, String idwallet);
        
        Observable<List<Wallet>> getAllWallet(String userid, String token);
        
        Observable<String> moveWallet(String userid, String token, String wallet1, String wallet2,
                  String money);
    }
    interface CurrenciesTask{
        Observable<List<Currencies>> getCurrencies();
    }
    interface EventTask{
        
        Observable<List<Event>> getEvent(String uerid,String token);
        Observable<String> createEvent(Event event,String userid,String token);
        Observable<String> updateEvent(Event event,String userid,String token);
        Observable<String> deleteEvent(String userid,String token,String idEvent);
        
    }
}
