package com.vn.hcmute.team.cortana.mymoney.ui.view.selectwallet;

import android.content.Context;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.data.cache.PreferencesHelper;
import com.vn.hcmute.team.cortana.mymoney.di.module.GlideApp;
import com.vn.hcmute.team.cortana.mymoney.model.Wallet;
import com.vn.hcmute.team.cortana.mymoney.ui.view.MenuPopupWithIcon;
import com.vn.hcmute.team.cortana.mymoney.utils.DrawableUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 8/30/17.
 */

public class SelectWalletAdapter extends RecyclerView.Adapter<ViewHolder> {
    
    public static final String TAG = SelectWalletAdapter.class.getSimpleName();
    
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    
    private Context mContext;
    private List<Wallet> mWallets = new ArrayList<>();
    
    private SelectWalletListener mSelectWalletListener;
    private PreferencesHelper mPreferencesHelper;
    private boolean shouldShowTotal;
    private boolean shouldShowFooter;
    private boolean shouldShowMenuWallet;
    
    public SelectWalletAdapter(Context context, List<Wallet> wallets) {
        this.mContext = context;
        if (wallets != null) {
            this.mWallets.addAll(wallets);
        }
        this.mPreferencesHelper = PreferencesHelper.getInstance(mContext);
        shouldShowTotal = true;
        shouldShowFooter = true;
        shouldShowMenuWallet = false;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        
        if (viewType == TYPE_HEADER) {
            v = LayoutInflater.from(parent.getContext())
                      .inflate(R.layout.item_recycler_view_header_select_wallet, parent, false);
            return new SelectWalletHeaderItemViewHolder(v);
        }
        
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(parent.getContext())
                      .inflate(R.layout.item_recycler_view_item_select_wallet, parent, false);
            
            return new SelectWalletItemViewHolder(v);
        } else if (viewType == TYPE_FOOTER) {
            v = LayoutInflater.from(parent.getContext())
                      .inflate(R.layout.item_recycler_view_footer_select_wallet, parent, false);
            return new SelectWalletFooterItemViewHolder(v);
        } else {
            return null;
        }
    }
    
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        
        if (mSelectWalletListener == null) {
            return;
        }
        if (holder instanceof SelectWalletItemViewHolder) {
            final SelectWalletItemViewHolder item = (SelectWalletItemViewHolder) holder;
            
            final Wallet wallet =
                      shouldHaveHeader() ? mWallets.get(position - 1) : mWallets.get(position);
            
            item.mImageViewSelectedWallet.setVisibility(
                      isSelectedWallet(wallet.getWalletid()) ? View.VISIBLE : View.INVISIBLE);
            item.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectWalletListener.onCLickWallet(wallet);
                }
            });
            
            if (!shouldShowMenuWallet) {
                item.itemView.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        
                        MenuPopupWithIcon menuPopupWithIcon = new MenuPopupWithIcon(mContext, v);
                        menuPopupWithIcon.inflate(R.menu.menu_popup_select_wallet);
                        menuPopupWithIcon.setMenuItemCLickListener(new OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.action_edit:
                                        mSelectWalletListener.onEditWallet(position, wallet);
                                        return true;
                                    case R.id.action_remove_wallet:
                                        mSelectWalletListener.onRemoveWallet(position, wallet);
                                        return true;
                                    case R.id.action_archive:
                                        mSelectWalletListener.onArchiveWallet(position, wallet);
                                    default:
                                        return false;
                                }
                            }
                        });
                        
                        menuPopupWithIcon.show();
                        
                        return true;
                    }
                });
            }
            
            int imageWallet = DrawableUtil.getDrawable(mContext, wallet.getWalletImage());
            
            GlideApp.with(mContext).load(imageWallet)
                      .placeholder(R.drawable.folder_placeholder)
                      .error(R.drawable.folder_placeholder)
                      .into(item.mImageViewIconWallet);
            
            item.mTextViewNameWallet.setText(wallet.getWalletName());
            
            String value = mContext.getString(R.string.txt_show_value_wallet,
                      wallet.getCurrencyUnit().getCurSymbol(),
                      (TextUtils.isEmpty(wallet.getMoney()) ? "0" : wallet.getMoney()));
            
            item.mTextViewValueWallet.setText(value);
            item.mImageViewArchive.setVisibility(wallet.isArchive() ? View.VISIBLE : View.GONE);
            item.mImageViewMenuWallet
                      .setVisibility(shouldShowMenuWallet ? View.VISIBLE : View.GONE);
            if (shouldShowMenuWallet) {
                item.mImageViewMenuWallet.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                        MenuPopupWithIcon menuPopupWithIcon = new MenuPopupWithIcon(mContext,
                                  item.mImageViewMenuWallet);
                        menuPopupWithIcon.inflate(R.menu.menu_popup_my_wallet);
                        
                        menuPopupWithIcon.setMenuItemCLickListener(new OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.action_edit:
                                        mSelectWalletListener.onEditWallet(position, wallet);
                                        return true;
                                    case R.id.action_remove_wallet:
                                        mSelectWalletListener.onRemoveWallet(position, wallet);
                                        return true;
                                    case R.id.action_transfer_money:
                                        mSelectWalletListener
                                                  .onTransferMoneyWallet(position, wallet);
                                    default:
                                        return false;
                                }
                            }
                        });
                        
                        menuPopupWithIcon.show();
                    }
                });
            }
            
        } else if (holder instanceof SelectWalletFooterItemViewHolder) {
            SelectWalletFooterItemViewHolder footer = (SelectWalletFooterItemViewHolder) holder;
            if (!shouldShowFooter) {
                footer.itemView.setVisibility(View.GONE);
                return;
            }
            footer.mButtonMyWallet.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectWalletListener.onClickMyWallet();
                }
            });
            
            footer.mButtonAddWallet.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectWalletListener.onClickAddWallet();
                }
            });
        }
    }
    
    @Override
    public int getItemCount() {
        if (shouldHaveHeader()) {
            return this.mWallets.size() + 2;
        }
        return shouldShowFooter ? this.mWallets.size() + 1 : this.mWallets.size();
    }
    
    
    @Override
    public int getItemViewType(int position) {
        if (isHeaderItem(position)) {
            return TYPE_HEADER;
        }
        if (isFooterItem(position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }
    
    public void setSelectWalletListener(
              SelectWalletListener selectWalletListener) {
        mSelectWalletListener = selectWalletListener;
    }
    
    
    private boolean isSelectedWallet(String walletId) {
        Wallet wallet = mPreferencesHelper.getCurrentWallet();
        return wallet == null || walletId.equalsIgnoreCase(wallet.getWalletid());
    }
    
    private boolean isHeaderItem(int position) {
        return shouldHaveHeader() && position == 0;
    }
    
    private boolean isFooterItem(int position) {
        if (shouldHaveHeader()) {
            return position == this.mWallets.size() + 1;
        }
        return position == this.mWallets.size();
        
    }
    
    public boolean shouldHaveHeader() {
        return this.mWallets.size() >= 2;
    }
    
    public void setData(List<Wallet> wallets) {
        if (wallets != null) {
            this.mWallets.clear();
            this.mWallets.addAll(wallets);
        }
    }
    
    public boolean isEmptyData() {
        return this.mWallets.isEmpty();
    }
    
    public void removeWallet(Wallet wallet) {
        
        this.mWallets.remove(wallet);
        
        notifyDataSetChanged();
    }
    
    public void updateArchiveWallet(int position, Wallet wallet) {
        int realPosition = this.mWallets.indexOf(wallet);
        this.mWallets.get(realPosition).setArchive(wallet.isArchive());
        notifyItemChanged(position);
    }
    
    public void addWallet(Wallet wallet) {
        this.mWallets.add(wallet);
        int updatePosition = shouldHaveHeader() ? mWallets.size() : mWallets.size() - 1;
        notifyDataSetChanged();
    }
    
    public void updateWallet(Wallet wallet) {
        int realPosition = this.mWallets.indexOf(wallet);
        
        this.mWallets.get(realPosition).setWalletName(wallet.getWalletName());
        this.mWallets.get(realPosition).setCurrencyUnit(wallet.getCurrencyUnit());
        this.mWallets.get(realPosition).setArchive(wallet.isArchive());
        this.mWallets.get(realPosition).setMoney(wallet.getMoney());
        
        int updatePosition = shouldHaveHeader() ? realPosition + 1 : realPosition;
        notifyItemChanged(updatePosition);
    }
    
    public boolean isShouldShowTotal() {
        return shouldShowTotal;
    }
    
    public void setShouldShowTotal(boolean shouldShowTotal) {
        this.shouldShowTotal = shouldShowTotal;
        notifyDataSetChanged();
    }
    
    public boolean isShouldShowFooter() {
        return shouldShowFooter;
    }
    
    public void setShouldShowFooter(boolean shouldShowFooter) {
        this.shouldShowFooter = shouldShowFooter;
        notifyDataSetChanged();
    }
    
    public boolean isShouldShowMenuWallet() {
        return shouldShowMenuWallet;
    }
    
    public void setShouldShowMenuWallet(boolean shouldShowMenuWallet) {
        this.shouldShowMenuWallet = shouldShowMenuWallet;
    }
    
    static class SelectWalletHeaderItemViewHolder extends RecyclerView.ViewHolder {
        
        private SelectWalletHeaderItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    
    static class SelectWalletItemViewHolder extends RecyclerView.ViewHolder {
        
        @BindView(R.id.image_view_icon_selected_wallet)
        ImageView mImageViewSelectedWallet;
        
        @BindView(R.id.image_view_icon_wallet)
        ImageView mImageViewIconWallet;
        
        @BindView(R.id.txt_name_wallet)
        TextView mTextViewNameWallet;
        
        @BindView(R.id.txt_value_wallet)
        TextView mTextViewValueWallet;
        
        @BindView(R.id.image_view_archive)
        ImageView mImageViewArchive;
        
        @BindView(R.id.menu_wallet)
        ImageView mImageViewMenuWallet;
        
        private SelectWalletItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    
    static class SelectWalletFooterItemViewHolder extends RecyclerView.ViewHolder {
        
        @BindView(R.id.btn_add_wallet)
        LinearLayout mButtonAddWallet;
        
        @BindView(R.id.btn_my_wallet)
        LinearLayout mButtonMyWallet;
        
        private SelectWalletFooterItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    
}
