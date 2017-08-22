package com.vn.hcmute.team.cortana.mymoney.di.module;

import android.content.Context;
import com.vn.hcmute.team.cortana.mymoney.data.DataRepository;
import com.vn.hcmute.team.cortana.mymoney.ui.wallet.WalletPresenter;
import com.vn.hcmute.team.cortana.mymoney.usecase.remote.WalletUseCase;
import dagger.Module;
import dagger.Provides;

/**
 * Created by kunsubin on 8/22/2017.
 */
@Module
public class WalletModule {
    
    public WalletModule() {
    }
    
    @Provides
    WalletUseCase provideWalletUseCase(Context context, DataRepository dataRepository) {
        return new WalletUseCase(context, dataRepository);
    }
    
    @Provides
    WalletPresenter provideWalletPresenter(WalletUseCase walletUseCase) {
        return new WalletPresenter(walletUseCase);
    }
}
