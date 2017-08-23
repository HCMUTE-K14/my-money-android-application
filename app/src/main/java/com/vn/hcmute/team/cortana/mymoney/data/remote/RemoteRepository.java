package com.vn.hcmute.team.cortana.mymoney.data.remote;

import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.BudgetService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.CurrenciesService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.EventService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.ImageService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.PersonService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.SavingService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.UserService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.WalletService;
import com.vn.hcmute.team.cortana.mymoney.exception.BudgetException;
import com.vn.hcmute.team.cortana.mymoney.exception.EventException;
import com.vn.hcmute.team.cortana.mymoney.exception.ImageException;
import com.vn.hcmute.team.cortana.mymoney.exception.PersonException;
import com.vn.hcmute.team.cortana.mymoney.exception.SavingException;
import com.vn.hcmute.team.cortana.mymoney.exception.UserLoginException;
import com.vn.hcmute.team.cortana.mymoney.exception.UserRegisterException;
import com.vn.hcmute.team.cortana.mymoney.exception.WalletException;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Image;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.utils.logger.MyLogger;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.List;
import javax.inject.Inject;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by infamouSs on 8/10/17.
 */


public class RemoteRepository implements RemoteTask.UserTask, RemoteTask.ImageTask,
                                         RemoteTask.WalletTask, RemoteTask.CurrenciesTask,
                                         RemoteTask.EventTask, RemoteTask.SavingTask ,RemoteTask.PersonTask, RemoteTask.BudgetTask{

 
    public static final String TAG = RemoteRepository.class.getSimpleName();
    
    private ServiceGenerator mServiceGenerator;
    
    @Inject
    public RemoteRepository(ServiceGenerator serviceGenerator) {
        this.mServiceGenerator = serviceGenerator;
    }
    
    @Override
    public Observable<User> login(UserCredential userCredential) {
        UserService userService = mServiceGenerator.getService(UserService.class);
        if (userService == null) {
            return null;
        }
        return userService.login(userCredential)
                  .map(new Function<JsonResponse<User>, User>() {
                      
                      @Override
                      public User apply(@NonNull JsonResponse<User> userJsonResponse)
                                throws Exception {
                          if (userJsonResponse.getStatus().equals("success")) {
                              return userJsonResponse.getData();
                          } else {
                              throw new UserLoginException(userJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> register(User user) {
        UserService userService = mServiceGenerator.getService(UserService.class);
        if (userService == null) {
            return null;
        }
        return userService.register(user)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getMessage();
                          } else {
                              throw new UserRegisterException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<List<Image>> getImage(String userid, String token) {
        ImageService imageService = mServiceGenerator.getService(ImageService.class);


        if (imageService == null) {
            return null;
        }

        return imageService.get(userid, token)
                  .map(new Function<JsonResponse<List<Image>>, List<Image>>() {
                      @Override
                      public List<Image> apply(@NonNull JsonResponse<List<Image>> listJsonResponse)
                                throws Exception {
                          
                          if (listJsonResponse.getStatus().equals("success")) {
                              return listJsonResponse.getData();
                          } else {
                              throw new ImageException(listJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<Image> getImageByid(String userid, String token, String imageid) {
        ImageService imageService = mServiceGenerator.getService(ImageService.class);
        if (imageService == null) {
            return null;
        }
        return imageService.getImageById(userid, token, imageid)
                  .map(new Function<JsonResponse<Image>, Image>() {
                      @Override
                      public Image apply(@NonNull JsonResponse<Image> imageJsonResponse)
                                throws Exception {
                          if (imageJsonResponse.getStatus().equals("success")) {
                              return imageJsonResponse.getData();
                          } else {
                              throw new ImageException(imageJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    
    @Override
    public Observable<String> removeImage(String userid, String token, String imageid) {
        ImageService imageService = mServiceGenerator.getService(ImageService.class);
        if (imageService == null) {
            return null;
        }
        return imageService.remove(userid, token, imageid)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new ImageException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> uploadImage(RequestBody userid, RequestBody token, RequestBody detail,
              MultipartBody.Part file) {
        ImageService imageService = mServiceGenerator.getService(ImageService.class);
        if (imageService == null) {
            return null;
        }
        return imageService.upload(userid, token, detail, file)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new ImageException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> updateImage(String userid, String token, String imageid) {
        ImageService imageService = mServiceGenerator.getService(ImageService.class);
        if (imageService == null) {
            return null;
        }
        return imageService.update(userid, token, imageid)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new ImageException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }

    // wallet
    @Override
    public Observable<String> createWallet(Wallet wallet, String userid, String token) {
        WalletService mWalletService = mServiceGenerator.getService(WalletService.class);
        
        return mWalletService.createWallet(wallet, userid, token)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new ImageException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
   
    

    @Override
    public Observable<String> updateWallet(Wallet wallet, String userid, String token) {
        WalletService mWalletService = mServiceGenerator.getService(WalletService.class);
        return mWalletService.updateWallet(wallet, userid, token)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new WalletException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<String> deleteWallet(String userid, String token, String idwallet) {
        WalletService mWalletService = mServiceGenerator.getService(WalletService.class);
        return mWalletService.deleteWallet(userid, token, idwallet)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new WalletException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<List<Wallet>> getAllWallet(String userid, String token) {
        WalletService mWalletService = mServiceGenerator.getService(WalletService.class);
        return mWalletService.getAllWallet(userid, token)
                  .map(new Function<JsonResponse<List<Wallet>>, List<Wallet>>() {
                      @Override
                      public List<Wallet> apply(
                                @NonNull JsonResponse<List<Wallet>> listJsonResponse)
                                throws Exception {
                          
                          if (listJsonResponse.getStatus().equals("success")) {
                              MyLogger.d("TRUE");
                              return listJsonResponse.getData();
                          } else {
                              MyLogger.d("FALSE");
                              throw new WalletException(listJsonResponse.getMessage());
                          }
                      }
                  });
        //return null;
    }
    
    @Override
    public Observable<String> moveWallet(String userid, String token, String wallet1,
              String wallet2, String money) {
        WalletService mWalletService = mServiceGenerator.getService(WalletService.class);
        return mWalletService.moveWallet(userid, token, wallet1, wallet2, money)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new WalletException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    //currencies
    @Override
    public Observable<List<Currencies>> getCurrencies() {
        
        CurrenciesService currencies = mServiceGenerator.getService(CurrenciesService.class);
        
        return currencies.getCurrencies().map(
                  new Function<JsonResponse<List<Currencies>>, List<Currencies>>() {
                      @Override
                      public List<Currencies> apply(
                                @NonNull JsonResponse<List<Currencies>> listJsonResponse)
                                throws Exception {
                          
                          if (listJsonResponse.getStatus().equals("success")) {
                              return listJsonResponse.getData();
                          } else {
                              throw new Exception(listJsonResponse.getMessage());
                          }
                      }
                  });
        
    }
    
    //event
    @Override
    public Observable<List<Event>> getEvent(String uerid, String token) {
        EventService eventService = mServiceGenerator.getService(EventService.class);
        
        return eventService.getEvent(uerid, token)
                  .map(new Function<JsonResponse<List<Event>>, List<Event>>() {
                      @Override
                      public List<Event> apply(@NonNull JsonResponse<List<Event>> listJsonResponse)
                                throws Exception {
                          if (listJsonResponse.getStatus().equals("success")) {
                              return listJsonResponse.getData();
                          } else {
                              throw new EventException(listJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<String> createEvent(Event event, String userid, String token) {
        EventService eventService = mServiceGenerator.getService(EventService.class);
        
        return eventService.createEvent(event, userid, token).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new EventException(stringJsonResponse.getMessage());
                          }
                          
                          
                      }
                  });
    }
    
    @Override
    public Observable<String> updateEvent(Event event, String userid, String token) {
        EventService eventService = mServiceGenerator.getService(EventService.class);
        
        return eventService.updateEvent(event, userid, token).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new EventException(stringJsonResponse.getMessage());
                          }
                          
                          
                      }
                  });
    }
    
    @Override
    public Observable<String> deleteEvent(String userid, String token, String idEvent) {
        EventService eventService = mServiceGenerator.getService(EventService.class);
        
        return eventService.deleteEvent(userid, token, idEvent).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new EventException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    //saving
    @Override
    public Observable<List<Saving>> getSaving(String userid, String token) {
        SavingService savingService=mServiceGenerator.getService(SavingService.class);
        
        return savingService.getSaving(userid,token).map(
                  new Function<JsonResponse<List<Saving>>, List<Saving>>() {
                      @Override
                      public List<Saving> apply(
                                @NonNull JsonResponse<List<Saving>> listJsonResponse)
                                throws Exception {
                          if(listJsonResponse.getStatus().equals("success")){
                              return listJsonResponse.getData();
                          }else{
                              throw new SavingException(listJsonResponse.getMessage());
                          }
                          
                          
                      }
                  });
    }
    
    @Override
    public Observable<String> createSaving(final Saving saving, String userid, String token) {
        SavingService savingService=mServiceGenerator.getService(SavingService.class);
        return savingService.createSaving(saving,userid,token).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if(stringJsonResponse.getStatus().equals("success")){
                              return stringJsonResponse.getData();
                          }else {
                              throw new SavingException(stringJsonResponse.getMessage());
                          }
                      
                      }
                  });
    }
    
    @Override
    public Observable<String> updateSaving(Saving saving, String userid, String token) {
        SavingService savingService=mServiceGenerator.getService(SavingService.class);
        return savingService.updateSaving(saving,userid,token).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          MyLogger.d("baodmfds",stringJsonResponse.getMessage());
                          if(stringJsonResponse.getStatus().equals("success")){
                              MyLogger.d("skjfdsjksfdsfsfsdfs");
                              return stringJsonResponse.getData();
                          }else {
                              MyLogger.d("fail update saving");
                              throw new SavingException(stringJsonResponse.getMessage());
                          }
                      
                      }
                  });
    }
    
    @Override
    public Observable<String> deleteSaving(String userid, String token, String idSaving) {
        SavingService savingService=mServiceGenerator.getService(SavingService.class);
        return savingService.deleteSaving(userid,token,idSaving).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if(stringJsonResponse.getStatus().equals("success")){
                              return stringJsonResponse.getData();
                          }else {
                              throw new SavingException(stringJsonResponse.getMessage());
                          }
                      
                      }
                  });
    }
    
    @Override
    public Observable<String> takeInSaving(String userid, String token, String idWallet,
              String idSaving, String money) {
        SavingService savingService=mServiceGenerator.getService(SavingService.class);
        return savingService.takeInSaving(userid,token,idWallet,idSaving,money).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if(stringJsonResponse.getStatus().equals("success")){
                              return stringJsonResponse.getData();
                          }else {
                              throw new SavingException(stringJsonResponse.getMessage());
                          }
                      
                      }
                  });
    }
    
    @Override
    public Observable<String> takeOutSaving(String userid, String token, String idWallet,
              String idSaving, String money) {
        SavingService savingService=mServiceGenerator.getService(SavingService.class);
        return savingService.takeOutSaving(userid,token,idWallet,idSaving,money).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if(stringJsonResponse.getStatus().equals("success")){
                              return stringJsonResponse.getData();
                          }else {
                              throw new SavingException(stringJsonResponse.getMessage());
                          }
                      
                      }
                  });
    }
    //person
    @Override
    public Observable<List<Person>> getPerson(String userid, String token) {
        PersonService personService=mServiceGenerator.getService(PersonService.class);
        
        return personService.getPerson(userid,token).map(
                  new Function<JsonResponse<List<Person>>, List<Person>>() {
                      @Override
                      public List<Person> apply(
                                @NonNull JsonResponse<List<Person>> listJsonResponse)
                                throws Exception {
                          if(listJsonResponse.getStatus().equals("success")){
                              return listJsonResponse.getData();
                          }else {
                              throw  new PersonException(listJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> addPerson(Person person, String userid, String token) {
        PersonService personService=mServiceGenerator.getService(PersonService.class);
        return personService.addPerson(person,userid,token).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if(stringJsonResponse.getStatus().equals("success")){
                              return stringJsonResponse.getData();
                          }else {
                              throw new PersonException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<String> removePerson(String userid, String token, String personid) {
        PersonService personService=mServiceGenerator.getService(PersonService.class);
        return personService.removePerson(userid,token,personid).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          MyLogger.d("checkkkkkkkkkkkk",stringJsonResponse.getData());
                          
                          if(stringJsonResponse.getStatus().equals("success")){
                              MyLogger.d("check data",stringJsonResponse.getData());
                              return stringJsonResponse.getData();
                              
                          }else {
                              throw new PersonException(stringJsonResponse.getMessage());
                          }
                      
                      }
                  });
    }
    //budget
    @Override
    public Observable<List<Budget>> getBudget(String userid, String token) {
        BudgetService budgetService=mServiceGenerator.getService(BudgetService.class);
        return budgetService.getBudget(userid,token).map(
                  new Function<JsonResponse<List<Budget>>, List<Budget>>() {
                      @Override
                      public List<Budget> apply(
                                @NonNull JsonResponse<List<Budget>> listJsonResponse)
                                throws Exception {
                          if(listJsonResponse.getStatus().equals("success")){
                              return listJsonResponse.getData();
                          }else {
                              throw new BudgetException(listJsonResponse.getMessage());
                          }
                  
                      }
                  });
    }
    
    @Override
    public Observable<String> createBudget(Budget budget, String userid, String token) {
        BudgetService budgetService=mServiceGenerator.getService(BudgetService.class);
        return budgetService.createBudget(budget,userid,token).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if(stringJsonResponse.getStatus().equals("success")){
                              return stringJsonResponse.getData();
                              
                          }else {
                              throw new BudgetException(stringJsonResponse.getMessage());
                          }
                         
                      }
                  });
    }
    
    @Override
    public Observable<String> updateBudget(Budget budget, String userid, String token) {
        BudgetService budgetService=mServiceGenerator.getService(BudgetService.class);
        return budgetService.updateBudget(budget,userid,token).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if(stringJsonResponse.getStatus().equals("success")){
                              return stringJsonResponse.getData();
                          
                          }else {
                              throw new BudgetException(stringJsonResponse.getMessage());
                          }
                      
                      }
                  });
    }
    
    @Override
    public Observable<String> deleteBudget(String userid, String token, String budgetId) {
        BudgetService budgetService=mServiceGenerator.getService(BudgetService.class);
        return budgetService.deleteBudget(userid,token,budgetId).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if(stringJsonResponse.getStatus().equals("success")){
                              return stringJsonResponse.getData();
                          
                          }else {
                              throw new BudgetException(stringJsonResponse.getMessage());
                          }
                      
                      }
                  });
    }

}
