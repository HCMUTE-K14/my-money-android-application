package com.vn.hcmute.team.cortana.mymoney.ui.person;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.ui.person.PersonAdapter.PersonAdapterViewHolder;
import com.vn.hcmute.team.cortana.mymoney.ui.view.RoundedLetterView;
import com.vn.hcmute.team.cortana.mymoney.utils.ColorUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 8/25/17.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapterViewHolder> {
    
    private Context mContext;
    private List<Person> mPersons = new ArrayList<>();
    private List<Person> mSelectedPersons = new ArrayList<>();
    private OnPersonClickListener mPersonClickListener;
    
    private boolean isFirstTime;
    private boolean isNeedLoadMore;
    
    public PersonAdapter(Context context, List<Person> selectedPersons,
              OnPersonClickListener personClickListener) {
        this.mContext = context;
        
        if (mSelectedPersons != null && !mSelectedPersons.isEmpty()) {
            this.mSelectedPersons.addAll(selectedPersons);
        }
        this.mPersonClickListener = personClickListener;
        
        this.isFirstTime = true;
        this.isNeedLoadMore = false;
    }
    
    public int getLayoutId() {
        return R.layout.item_person;
    }
    
    @Override
    public PersonAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return new PersonAdapterViewHolder(v);
    }
    
    @Override
    public int getItemCount() {
        return mPersons.size();
    }
    
    @Override
    public void onBindViewHolder(final PersonAdapterViewHolder holder, final int position) {
        final Person person = mPersons.get(position);
        
        final boolean isSelected = isSelectedPerson(person);
        
        int color = mPersons.get(position).getColor();
        
        if (color == 0) {
            color = Color.parseColor(ColorUtil.getRandomColor());
            mPersons.get(position).setColor(color);
        }
        if (color == Color.WHITE) {
            color = Color.BLACK;
        }
        holder.mLetterView.setBackgroundColor(color);
        
        final String startLetter = String.valueOf(person.getName().charAt(0)).toUpperCase();
        holder.mLetterView.setTitleText(startLetter);
        
        holder.mCheckBox.setChecked(isSelected);
        
        holder.mTextViewName.setText(person.getName());
        holder.mTextViewDescribe.setText(person.getDescribe());
        
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean shouldSelect = mPersonClickListener.onPersonClick(position, !isSelected);
                if (isSelected) {
                    removeSelected(person, position);
                } else if (shouldSelect) {
                    addSelected(person, position);
                }
            }
        });
        holder.mCheckBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean shouldSelect = mPersonClickListener.onPersonClick(position, !isSelected);
                if (isSelected) {
                    removeSelected(person, position);
                } else if (shouldSelect) {
                    addSelected(person, position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                
                PopupMenu popupMenu = new PopupMenu(mContext, holder.itemView);
                popupMenu.inflate(R.menu.menu_popup_person);
                
                popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_remove_person) {
                            mPersonClickListener.onLongPersonClick(position, person);
                            return true;
                        }
                        return false;
                    }
                });
                
                popupMenu.show();
                
                return false;
            }
        });
    }
    
    
    private boolean isSelectedPerson(Person person) {
        for (Person per : mSelectedPersons) {
            if (per.getPersonid().equals(person.getPersonid())) {
                return true;
            }
        }
        return false;
    }
    
    public List<Person> getData() {
        return mPersons;
    }
    
    public void setData(List<Person> persons) {
        mPersons.clear();
        mPersons.addAll(persons);
    }
    
    public boolean isEmpty() {
        return mPersons.isEmpty();
    }
    
    public boolean isNeedLoadMore() {
        return isNeedLoadMore;
    }
    
    public void setNeedLoadMore(boolean needLoadMore) {
        isNeedLoadMore = needLoadMore;
    }
    
    public List<Person> getSelectedPersons() {
        return mSelectedPersons;
    }
    
    public void remove(int position, Person person) {
        mPersons.remove(person);
        if (isSelectedPerson(person)) {
            mSelectedPersons.remove(person);
        }
        notifyItemChanged(position);
    }
    
    public void add(Person person) {
        mPersons.add(person);
        notifyItemChanged(mPersons.size() - 1);
    }
    
    public void addSelected(final Person person, final int position) {
        handlerSelection(new Runnable() {
            @Override
            public void run() {
                mSelectedPersons.add(person);
                notifyItemChanged(position);
            }
        });
    }
    
    public void removeSelected(final Person person, final int position) {
        handlerSelection(new Runnable() {
            @Override
            public void run() {
                mSelectedPersons.remove(person);
                notifyItemChanged(position);
            }
        });
    }
    
    public void removeAllSelected() {
        handlerSelection(new Runnable() {
            @Override
            public void run() {
                mSelectedPersons.clear();
                notifyDataSetChanged();
            }
        });
    }
    
    public void addAllSelected() {
        handlerSelection(new Runnable() {
            @Override
            public void run() {
                mSelectedPersons.clear();
                mSelectedPersons.addAll(mPersons);
                notifyDataSetChanged();
            }
        });
    }
    
    private void handlerSelection(Runnable runnable) {
        runnable.run();
    }
    
    
    public class PersonAdapterViewHolder extends RecyclerView.ViewHolder {
        
        @BindView(R.id.rounded_letter)
        RoundedLetterView mLetterView;
        
        @BindView(R.id.txt_name)
        TextView mTextViewName;
        
        @BindView(R.id.txt_describe)
        TextView mTextViewDescribe;
        
        @BindView(R.id.check_box)
        CheckBox mCheckBox;
        
        
        public PersonAdapterViewHolder(View root) {
            super(root);
            ButterKnife.bind(this, root);
        }
    }
}
