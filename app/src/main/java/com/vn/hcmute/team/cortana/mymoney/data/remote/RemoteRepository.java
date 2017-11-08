package com.vn.hcmute.team.cortana.mymoney.data.remote;

import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.BudgetService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.CategoryService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.CurrenciesService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.DebtLoanService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.EventService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.ImageService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.PersonService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.SavingService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.TransactionService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.UserService;
import com.vn.hcmute.team.cortana.mymoney.data.remote.serivce.WalletService;
import com.vn.hcmute.team.cortana.mymoney.exception.BudgetException;
import com.vn.hcmute.team.cortana.mymoney.exception.CategoryException;
import com.vn.hcmute.team.cortana.mymoney.exception.DebtLoanException;
import com.vn.hcmute.team.cortana.mymoney.exception.EventException;
import com.vn.hcmute.team.cortana.mymoney.exception.ImageException;
import com.vn.hcmute.team.cortana.mymoney.exception.PersonException;
import com.vn.hcmute.team.cortana.mymoney.exception.SavingException;
import com.vn.hcmute.team.cortana.mymoney.exception.TransactionException;
import com.vn.hcmute.team.cortana.mymoney.exception.UserLoginException;
import com.vn.hcmute.team.cortana.mymoney.exception.UserRegisterException;
import com.vn.hcmute.team.cortana.mymoney.exception.WalletException;
import com.vn.hcmute.team.cortana.mymoney.model.Budget;
import com.vn.hcmute.team.cortana.mymoney.model.Category;
import com.vn.hcmute.team.cortana.mymoney.model.Currencies;
import com.vn.hcmute.team.cortana.mymoney.model.DebtLoan;
import com.vn.hcmute.team.cortana.mymoney.model.Event;
import com.vn.hcmute.team.cortana.mymoney.model.Image;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.model.RealTimeCurrency;
import com.vn.hcmute.team.cortana.mymoney.model.ResultConvert;
import com.vn.hcmute.team.cortana.mymoney.model.Saving;
import com.vn.hcmute.team.cortana.mymoney.model.Transaction;
import com.vn.hcmute.team.cortana.mymoney.model.User;
import com.vn.hcmute.team.cortana.mymoney.model.UserCredential;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by infamouSs on 8/10/17.
 */
@Singleton
public class RemoteRepository implements RemoteTask.UserTask, RemoteTask.ImageTask,
                                         RemoteTask.WalletTask, RemoteTask.CurrenciesTask,
                                         RemoteTask.EventTask, RemoteTask.SavingTask,
                                         RemoteTask.PersonTask, RemoteTask.BudgetTask,
                                         RemoteTask.CategoryTask, RemoteTask.TransactionTask,
                                         RemoteTask.DebtLoanTask {
    
    
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
    public Observable<User> login(User user) {
        UserService userService = mServiceGenerator.getService(UserService.class);
        if (userService == null) {
            return null;
        }
        return userService.login(user)
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
    public Observable<String> isExistFacebookAccount(User user) {
        UserService userService = mServiceGenerator.getService(UserService.class);
        if (userService == null) {
            return null;
        }
        return userService.isExistFacebookAccount(user)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getMessage();
                          } else {
                              throw new UserLoginException(stringJsonResponse.getMessage());
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
    public Observable<String> forgetPassword(String email) {
        UserService userService = mServiceGenerator.getService(UserService.class);
        if (userService == null) {
            return null;
        }
        return userService.forgetPassword(email)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getMessage();
                          } else {
                              throw new ImageException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> changePassword(String userid, String token, String oldPassword,
              String newPassword) {
        UserService userService = mServiceGenerator.getService(UserService.class);
        if (userService == null) {
            return null;
        }
        return userService.changePassword(userid, token, oldPassword, newPassword)
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
    public Observable<String> changeProfile(String userid, String token, User user) {
        UserService userService = mServiceGenerator.getService(UserService.class);
        if (userService == null) {
            return null;
        }
        return userService.changeProfile(userid, token, user)
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
    public Observable<List<Image>> uploadImage(RequestBody userid, RequestBody token,
              RequestBody detail,
              List<MultipartBody.Part> files) {
        ImageService imageService = mServiceGenerator.getService(ImageService.class);
        if (imageService == null) {
            return null;
        }
        return imageService.upload(userid, token, detail, files)
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
        if (mWalletService == null) {
            return null;
        }
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
        if (mWalletService == null) {
            return null;
        }
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
        if (mWalletService == null) {
            return null;
        }
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
        if (mWalletService == null) {
            return null;
        }
        return mWalletService.getAllWallet(userid, token)
                  .map(new Function<JsonResponse<List<Wallet>>, List<Wallet>>() {
                      @Override
                      public List<Wallet> apply(
                                @NonNull JsonResponse<List<Wallet>> listJsonResponse)
                                throws Exception {
                          
                          if (listJsonResponse.getStatus().equals("success")) {
                              return listJsonResponse.getData();
                          } else {
                              throw new WalletException(listJsonResponse.getMessage());
                          }
                      }
                  });
        //return null;
    }
    
    @Override
    public Observable<String> moveWallet(String userid, String token, String walletFrom,
              String walletTo, String moneyMinus, String moneyPlus, String dateCreated) {
        WalletService mWalletService = mServiceGenerator.getService(WalletService.class);
        if (mWalletService == null) {
            return null;
        }
        return mWalletService.moveWallet(userid, token, walletFrom, walletTo, moneyMinus, moneyPlus,
                  dateCreated)
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
    public Observable<Wallet> getWalletById(String userid, String token, String wallet_id) {
        WalletService mWalletService = mServiceGenerator.getService(WalletService.class);
        if (mWalletService == null) {
            return null;
        }
        return mWalletService.getWalletById(userid, token, wallet_id)
                  .map(new Function<JsonResponse<Wallet>, Wallet>() {
                      @Override
                      public Wallet apply(JsonResponse<Wallet> walletJsonResponse)
                                throws Exception {
                          if (walletJsonResponse.getStatus().equals("success")) {
                              return walletJsonResponse.getData();
                          } else {
                              throw new WalletException(walletJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    
    //currencies
    @Override
    public Observable<List<Currencies>> getCurrencies() {
        
        CurrenciesService currencies = mServiceGenerator.getService(CurrenciesService.class);
        if (currencies == null) {
            return null;
        }
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
    
    @Override
    public Observable<ResultConvert> convertCurrency(String amount, String from, String to) {
        CurrenciesService currencies = mServiceGenerator.getService(CurrenciesService.class);
        if (currencies == null) {
            return null;
        }
        return currencies.convertCurrency(amount, from, to).map(
                  new Function<JsonResponse<ResultConvert>, ResultConvert>() {
                      @Override
                      public ResultConvert apply(
                                @NonNull JsonResponse<ResultConvert> resultConvertJsonResponse)
                                throws Exception {
                          if (resultConvertJsonResponse.getStatus().equals("success")) {
                              return resultConvertJsonResponse.getData();
                          } else {
                              throw new Exception(resultConvertJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<RealTimeCurrency> getRealTimeCurrency() {
        CurrenciesService currencies = mServiceGenerator.getService(CurrenciesService.class);
        if (currencies == null) {
            return null;
        }
        return currencies.getRealTimeCurrency().map(
                  new Function<JsonResponse<RealTimeCurrency>, RealTimeCurrency>() {
                      @Override
                      public RealTimeCurrency apply(
                                @NonNull JsonResponse<RealTimeCurrency> realTimeCurrencyJsonResponse)
                                throws Exception {
                          if (realTimeCurrencyJsonResponse.getStatus().equals("success")) {
                              return realTimeCurrencyJsonResponse.getData();
                          } else {
                              throw new Exception(realTimeCurrencyJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    //event
    @Override
    public Observable<List<Event>> getEvent(String uerid, String token) {
        EventService eventService = mServiceGenerator.getService(EventService.class);
        if (eventService == null) {
            return null;
        }
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
        if (eventService == null) {
            return null;
        }
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
        if (eventService == null) {
            return null;
        }
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
        if (eventService == null) {
            return null;
        }
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
        SavingService savingService = mServiceGenerator.getService(SavingService.class);
        if (savingService == null) {
            return null;
        }
        return savingService.getSaving(userid, token).map(
                  new Function<JsonResponse<List<Saving>>, List<Saving>>() {
                      @Override
                      public List<Saving> apply(
                                @NonNull JsonResponse<List<Saving>> listJsonResponse)
                                throws Exception {
                          if (listJsonResponse.getStatus().equals("success")) {
                              return listJsonResponse.getData();
                          } else {
                              throw new SavingException(listJsonResponse.getMessage());
                          }
                          
                          
                      }
                  });
    }
    
    @Override
    public Observable<String> createSaving(final Saving saving, String userid, String token) {
        SavingService savingService = mServiceGenerator.getService(SavingService.class);
        if (savingService == null) {
            return null;
        }
        return savingService.createSaving(saving, userid, token).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new SavingException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<String> updateSaving(Saving saving, String userid, String token) {
        SavingService savingService = mServiceGenerator.getService(SavingService.class);
        if (savingService == null) {
            return null;
        }
        return savingService.updateSaving(saving, userid, token).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new SavingException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<String> deleteSaving(String userid, String token, String idSaving) {
        SavingService savingService = mServiceGenerator.getService(SavingService.class);
        if (savingService == null) {
            return null;
        }
        return savingService.deleteSaving(userid, token, idSaving).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new SavingException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<String> takeInSaving(String userid, String token, String idWallet,
              String idSaving, String moneyUpdateWallet, String moneyUpdateSaving) {
        SavingService savingService = mServiceGenerator.getService(SavingService.class);
        if (savingService == null) {
            return null;
        }
        return savingService.takeInSaving(userid, token, idWallet, idSaving, moneyUpdateWallet,
                  moneyUpdateSaving).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new SavingException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<String> takeOutSaving(String userid, String token, String idWallet,
              String idSaving, String moneyUpdateWallet, String moneyUpdateSaving) {
        SavingService savingService = mServiceGenerator.getService(SavingService.class);
        if (savingService == null) {
            return null;
        }
        return savingService.takeOutSaving(userid, token, idWallet, idSaving, moneyUpdateWallet,
                  moneyUpdateSaving).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new SavingException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    //person
    @Override
    public Observable<List<Person>> getPerson(String userid, String token) {
        PersonService personService = mServiceGenerator.getService(PersonService.class);
        if (personService == null) {
            return null;
        }
        return personService.getPerson(userid, token).map(
                  new Function<JsonResponse<List<Person>>, List<Person>>() {
                      @Override
                      public List<Person> apply(
                                @NonNull JsonResponse<List<Person>> listJsonResponse)
                                throws Exception {
                          if (listJsonResponse.getStatus().equals("success")) {
                              return listJsonResponse.getData();
                          } else {
                              throw new PersonException(listJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> addPerson(Person person, String userid, String token) {
        PersonService personService = mServiceGenerator.getService(PersonService.class);
        if (personService == null) {
            return null;
        }
        return personService.addPerson(person, userid, token).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new PersonException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<String> removePerson(String userid, String token, String personid) {
        PersonService personService = mServiceGenerator.getService(PersonService.class);
        if (personService == null) {
            return null;
        }
        return personService.removePerson(userid, token, personid).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                              
                          } else {
                              throw new PersonException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<String> updatePerson(Person person, String userid, String token) {
        PersonService personService = mServiceGenerator.getService(PersonService.class);
        if (personService == null) {
            return null;
        }
        return personService.updatePerson(userid, token, person).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                              
                          } else {
                              throw new PersonException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> syncPerson(List<Person> persons, String userid, String token) {
        PersonService personService = mServiceGenerator.getService(PersonService.class);
        if (personService == null) {
            return null;
        }
        return personService.synchPerson(userid, token, persons).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new PersonException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    //budget
    @Override
    public Observable<List<Budget>> getBudget(String userid, String token) {
        BudgetService budgetService = mServiceGenerator.getService(BudgetService.class);
        if (budgetService == null) {
            return null;
        }
        return budgetService.getBudget(userid, token).map(
                  new Function<JsonResponse<List<Budget>>, List<Budget>>() {
                      @Override
                      public List<Budget> apply(
                                @NonNull JsonResponse<List<Budget>> listJsonResponse)
                                throws Exception {
                          if (listJsonResponse.getStatus().equals("success")) {
                              return listJsonResponse.getData();
                          } else {
                              throw new BudgetException(listJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<String> createBudget(Budget budget, String userid, String token) {
        BudgetService budgetService = mServiceGenerator.getService(BudgetService.class);
        if (budgetService == null) {
            return null;
        }
        return budgetService.createBudget(budget, userid, token).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                              
                          } else {
                              throw new BudgetException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<String> updateBudget(Budget budget, String userid, String token) {
        BudgetService budgetService = mServiceGenerator.getService(BudgetService.class);
        if (budgetService == null) {
            return null;
        }
        return budgetService.updateBudget(budget, userid, token).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                              
                          } else {
                              throw new BudgetException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<String> deleteBudget(String userid, String token, String budgetId) {
        BudgetService budgetService = mServiceGenerator.getService(BudgetService.class);
        if (budgetService == null) {
            return null;
        }
        return budgetService.deleteBudget(userid, token, budgetId).map(
                  new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                              
                          } else {
                              throw new BudgetException(stringJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<List<Category>> getListCategory(String userid, String token, String type) {
        CategoryService categoryService = mServiceGenerator.getService(CategoryService.class);
        if (categoryService == null) {
            return null;
        }
        
        return categoryService.getListCategory(userid, token, type)
                  .map(new Function<JsonResponse<List<Category>>, List<Category>>() {
                      @Override
                      public List<Category> apply(
                                @NonNull JsonResponse<List<Category>> listJsonResponse)
                                throws Exception {
                          if (listJsonResponse.getStatus().equals("success")) {
                              return listJsonResponse.getData();
                              
                          } else {
                              throw new CategoryException(listJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<List<Category>> getListCategoryByType(String userid, String token,
              String type, String transType) {
        CategoryService categoryService = mServiceGenerator.getService(CategoryService.class);
        if (categoryService == null) {
            return null;
        }
        
        return categoryService.getListCategoryByType(userid, token, type, transType)
                  .map(new Function<JsonResponse<List<Category>>, List<Category>>() {
                      @Override
                      public List<Category> apply(
                                @NonNull JsonResponse<List<Category>> listJsonResponse)
                                throws Exception {
                          if (listJsonResponse.getStatus().equals("success")) {
                              return listJsonResponse.getData();
                          } else {
                              throw new CategoryException(listJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> createCategory(String userid, String token, String parentId,
              Category category) {
        CategoryService categoryService = mServiceGenerator.getService(CategoryService.class);
        if (categoryService == null) {
            return null;
        }
        
        return categoryService.addCategory(userid, token, parentId, category)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getMessage();
                              
                          } else {
                              throw new BudgetException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> updateCategory(String userid, String token, String oldParentId,
              String newParentId, Category category) {
        CategoryService categoryService = mServiceGenerator.getService(CategoryService.class);
        if (categoryService == null) {
            return null;
        }
        
        return categoryService.updateCategory(userid, token, oldParentId, newParentId, category)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getMessage();
                              
                          } else {
                              throw new BudgetException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    
    @Override
    public Observable<String> deleteCategory(String userid, String token, String parentId,
              Category category) {
        CategoryService categoryService = mServiceGenerator.getService(CategoryService.class);
        if (categoryService == null) {
            return null;
        }
        
        return categoryService.deleteCategory(userid, token, parentId, category)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getMessage();
                              
                          } else {
                              throw new BudgetException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<List<Transaction>> getTransactionByCategory(String userid, String token,
              String categoryId, String walletId) {
        TransactionService transactionService = mServiceGenerator
                  .getService(TransactionService.class);
        if (transactionService == null) {
            return null;
        }
        return transactionService.getTransactionByCategory(walletId, userid, token, categoryId)
                  .map(new Function<JsonResponse<List<Transaction>>, List<Transaction>>() {
                      @Override
                      public List<Transaction> apply(
                                @NonNull JsonResponse<List<Transaction>> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new TransactionException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<Transaction> getTransactionById(String id, String userid, String token) {
        TransactionService transactionService = mServiceGenerator
                  .getService(TransactionService.class);
        if (transactionService == null) {
            return null;
        }
        return transactionService.getTransactionById(id, userid, token)
                  .map(new Function<JsonResponse<Transaction>, Transaction>() {
                      @Override
                      public Transaction apply(
                                @NonNull JsonResponse<Transaction> transactionJsonResponse)
                                throws Exception {
                          if (transactionJsonResponse.getStatus().equals("success")) {
                              return transactionJsonResponse.getData();
                          } else {
                              throw new TransactionException(transactionJsonResponse.getMessage());
                          }
                          
                      }
                  });
    }
    
    @Override
    public Observable<List<Transaction>> getTransaction(String userid, String token) {
        TransactionService transactionService = mServiceGenerator
                  .getService(TransactionService.class);
        if (transactionService == null) {
            return null;
        }
        return transactionService.getTransaction(userid, token)
                  .map(new Function<JsonResponse<List<Transaction>>, List<Transaction>>() {
                      @Override
                      public List<Transaction> apply(
                                @NonNull JsonResponse<List<Transaction>> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new TransactionException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<List<Transaction>> getTransactionByType(String userid, String token,
              String type,
              String walletId) {
        TransactionService transactionService = mServiceGenerator
                  .getService(TransactionService.class);
        if (transactionService == null) {
            return null;
        }
        return transactionService.getTransactionByCategory(walletId, userid, token, type)
                  .map(new Function<JsonResponse<List<Transaction>>, List<Transaction>>() {
                      @Override
                      public List<Transaction> apply(
                                @NonNull JsonResponse<List<Transaction>> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new TransactionException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<List<Transaction>> getTransactionByTime(String userid, String token,
              String startDate,
              String endDate, String walletId) {
        TransactionService transactionService = mServiceGenerator
                  .getService(TransactionService.class);
        if (transactionService == null) {
            return null;
        }
        return transactionService
                  .getTransactionByTime(walletId, userid, token, startDate, endDate)
                  .map(new Function<JsonResponse<List<Transaction>>, List<Transaction>>() {
                      @Override
                      public List<Transaction> apply(
                                @NonNull JsonResponse<List<Transaction>> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new TransactionException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<List<Transaction>> getTransactionByEvent(String eventid, String userid,
              String token) {
        TransactionService transactionService = mServiceGenerator
                  .getService(TransactionService.class);
        if (transactionService == null) {
            return null;
        }
        return transactionService
                  .getTransactionByEvent(eventid, userid, token)
                  .map(new Function<JsonResponse<List<Transaction>>, List<Transaction>>() {
                      @Override
                      public List<Transaction> apply(
                                @NonNull JsonResponse<List<Transaction>> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new TransactionException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<List<Transaction>> getTransactionByBudget(String startDate, String endDate,
              String categoryId, String walletId, String userid, String token) {
        TransactionService transactionService = mServiceGenerator
                  .getService(TransactionService.class);
        if (transactionService == null) {
            return null;
        }
        //MyLogger.d(startDate+"kejkwjkr"+endDate);
        return transactionService
                  .getTransactionByBudget(startDate, endDate, categoryId, walletId, userid, token)
                  .map(new Function<JsonResponse<List<Transaction>>, List<Transaction>>() {
                      @Override
                      public List<Transaction> apply(
                                @NonNull JsonResponse<List<Transaction>> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getData();
                          } else {
                              throw new TransactionException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> addTransaction(String userid, String token,
              final Transaction transaction) {
        TransactionService transactionService = mServiceGenerator
                  .getService(TransactionService.class);
        if (transactionService == null) {
            return null;
        }
        return transactionService.addTransaction(userid, token, transaction)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getMessage();
                          } else {
                              throw new TransactionException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> updateTransaction(String userid, String token,
              Transaction transaction) {
        TransactionService transactionService = mServiceGenerator
                  .getService(TransactionService.class);
        if (transactionService == null) {
            return null;
        }
        return transactionService.updateTransaction(userid, token, transaction)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getMessage();
                          } else {
                              throw new TransactionException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> deleteTransaction(String userid, String token, String trans_id) {
        TransactionService transactionService = mServiceGenerator
                  .getService(TransactionService.class);
        if (transactionService == null) {
            return null;
        }
        
        return transactionService.deleteTransaction(userid, token, trans_id)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getMessage();
                          } else {
                              throw new TransactionException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<List<DebtLoan>> getDebtLoan(String userid, String token, String
              wallet_id,
              String type) {
        DebtLoanService debtLoanService = mServiceGenerator.getService(DebtLoanService.class);
        
        if (debtLoanService == null) {
            return null;
        }
        
        return debtLoanService.getDebtLoanByType(userid, token, wallet_id, type)
                  .map(new Function<JsonResponse<List<DebtLoan>>, List<DebtLoan>>() {
                      @Override
                      public List<DebtLoan> apply(
                                @NonNull JsonResponse<List<DebtLoan>> listJsonResponse)
                                throws Exception {
                          if (listJsonResponse.getStatus().equals("success")) {
                              return listJsonResponse.getData();
                          } else {
                              throw new DebtLoanException(listJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> addDebtLoan(String userid, String token, DebtLoan debtLoan) {
        DebtLoanService debtLoanService = mServiceGenerator.getService(DebtLoanService.class);
        
        if (debtLoanService == null) {
            return null;
        }
        
        return debtLoanService.addDebtLoan(userid, token, debtLoan)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getMessage();
                          } else {
                              throw new DebtLoanException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> updateDebtLoan(String userid, String token, DebtLoan debtLoan,
              String wallet_id) {
        DebtLoanService debtLoanService = mServiceGenerator.getService(DebtLoanService.class);
        
        if (debtLoanService == null) {
            return null;
        }
        
        return debtLoanService.updateDebtLoan(userid, token, wallet_id, debtLoan)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getMessage();
                          } else {
                              throw new DebtLoanException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
    @Override
    public Observable<String> deleteDebtLoan(String userid, String token, DebtLoan debtLoan) {
        DebtLoanService debtLoanService = mServiceGenerator.getService(DebtLoanService.class);
        
        if (debtLoanService == null) {
            return null;
        }
        
        return debtLoanService.deleteDebtLoan(userid, token, debtLoan)
                  .map(new Function<JsonResponse<String>, String>() {
                      @Override
                      public String apply(@NonNull JsonResponse<String> stringJsonResponse)
                                throws Exception {
                          if (stringJsonResponse.getStatus().equals("success")) {
                              return stringJsonResponse.getMessage();
                          } else {
                              throw new DebtLoanException(stringJsonResponse.getMessage());
                          }
                      }
                  });
    }
    
}
