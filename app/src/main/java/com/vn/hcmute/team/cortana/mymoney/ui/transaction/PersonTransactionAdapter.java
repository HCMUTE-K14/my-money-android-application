package com.vn.hcmute.team.cortana.mymoney.ui.transaction;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vn.hcmute.team.cortana.mymoney.R;
import com.vn.hcmute.team.cortana.mymoney.model.Person;
import com.vn.hcmute.team.cortana.mymoney.ui.transaction.PersonTransactionAdapter.PersonTransactionViewHolder;
import com.vn.hcmute.team.cortana.mymoney.ui.view.RoundedLetterView;
import com.vn.hcmute.team.cortana.mymoney.utils.ColorUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by infamouSs on 11/8/17.
 */
public class PersonTransactionAdapter extends
                                      RecyclerView.Adapter<PersonTransactionViewHolder> {
    
    @BindView(R.id.rounded_letter)
    RoundedLetterView mRoundedLetterView;
    
    @BindView(R.id.txt_name_person)
    TextView mTextViewNamePerson;
    
    private List<Person> mData;
    
    public PersonTransactionAdapter() {
    
    }
    
    public void setData(List<Person> data) {
        if (this.mData == null) {
            mData = new ArrayList<>();
        }
        
        mData.addAll(data);
    }
    
    @Override
    public PersonTransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.item_recycler_view_list_person_trans, parent, false);
        
        return new PersonTransactionViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(PersonTransactionViewHolder holder, int position) {
        final Person person = mData.get(position);
        
        int color = person.getColor();
        
        if (color == 0) {
            color = Color.parseColor(ColorUtil.getRandomColor());
            mData.get(position).setColor(color);
        }
        if (color == Color.WHITE) {
            color = Color.BLACK;
        }
        holder.mLetterView.setBackgroundColor(color);
        
        if (!TextUtils.isEmpty(person.getName())) {
            final String startLetter = String.valueOf(person.getName().charAt(0)).toUpperCase();
            holder.mLetterView.setTitleText(startLetter);
            holder.mTextViewNamePerson.setText(person.getName());
        }
    }
    
    @Override
    public int getItemCount() {
        return mData.size();
    }
    
    static class PersonTransactionViewHolder extends RecyclerView.ViewHolder {
        
        @BindView(R.id.rounded_letter)
        RoundedLetterView mLetterView;
        
        @BindView(R.id.txt_name_person)
        TextView mTextViewNamePerson;
        
        PersonTransactionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
