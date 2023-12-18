package com.swaraajsports.technician.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.ajitmaurya.basicsetup.view.CustomButton;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.response.ComplainResponse;
import com.swaraajsports.technician.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComplainAdapter extends RecyclerView.Adapter<ComplainAdapter.ComplainViewHolder> {

    private List<ComplainResponse.Complain> complainsList;
    private List<ComplainResponse.Complain> complainListSearch;
    private final Context context;
    private SetOnClickListener onClickListener;
    PreferenceManager preferenceManager;

    public ComplainAdapter(Context context, List<ComplainResponse.Complain> complainResponse) {

        this.context = context;
        this.complainsList = complainResponse;
        this.complainListSearch = complainResponse;
        preferenceManager = new PreferenceManager(context);
    }


    public void setOnClickListener(SetOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ComplainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.complain_raw, parent, false);
        return new ComplainViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ComplainViewHolder h, @SuppressLint("RecyclerView") final int i) {

        h.setIsRecyclable(false);

        h.tvCategoryTitle.setText("Category : ");
        h.tvComplainTitle.setText("Title : ");
        h.tvComplainDecTitle.setText("Description : ");
        h.tvComplainTatTitle.setText("Date : ");

        BasicFunctions.displayImage(context,h.ivComplaintImage,complainListSearch.get(i).getComplainPhoto(),R.drawable.complaint_place,R.drawable.complaint_place);

        if (complainListSearch.get(i).getComplain_description() != null && complainListSearch.get(i).getComplain_description().trim().length() > 0) {
            h.tvComplainDec.setText("" + complainListSearch.get(i).getComplain_description());

        }

        h.tvComplainName.setText("" + complainListSearch.get(i).getCompalain_title() + "");
        h.tvComplaintCategory.setText("" + complainListSearch.get(i).getComplaint_category_view() + "");
        h.tvComplainTat.setText(complainListSearch.get(i).getComplain_date());


        h.tvComplainNo.setText(complainListSearch.get(i).getComplain_no());

        if (complainListSearch.get(i).getComplain_review_msg() != null
                && complainListSearch.get(i).getComplain_review_msg().length() > 1) {
            h.carAdmin.setVisibility(View.VISIBLE);
            h.tvAdminMsg.setText(complainListSearch.get(i).getComplain_review_msg());
            //h.complainRawTv_ComplainDateAdmin.setTextWithMarquee(complainListSearch.get(i).getComplain_review_msg());
        } else {
            h.carAdmin.setVisibility(View.GONE);
        }


        h.linClick.setOnClickListener(v -> onClickListener.onClick(i, complainListSearch.get(i)));
        h.carAdmin.setOnClickListener(v -> onClickListener.onClick(i, complainListSearch.get(i)));

        if (complainListSearch.get(i).getTechnician() != null && complainListSearch.get(i).getTechnician().trim().length() > 0) {
            h.linPersonAssign.setVisibility(View.VISIBLE);
            h.tvTechnicianNameTitle.setText("Raise By : ");
            h.tvTechnicianName.setText(complainListSearch.get(i).getTechnician());

        } else {
            h.linPersonAssign.setVisibility(View.GONE);
        }

        checkStatus(complainListSearch.get(i).getComplain_status(), h.tcComplentStatus, complainListSearch.get(i).getComplain_status_view(),h.linBtn,h.btnClose,h.btnInProgress,h.btn_hold);



        if (complainListSearch.get(i).isAutoClose()){

            h.btnClose.setText("Close Complain");

            h.btnClose.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    if (onClickListener!=null){
                        onClickListener.setClose(complainListSearch.get(i));
                    }
                }
            });


        }else {

            h.btnClose.setText(context.getString(R.string.request_for_close));

            h.btnClose.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    if (onClickListener!=null){
                        onClickListener.setReqClose(complainListSearch.get(i));
                    }
                }
            });

        }



        h.btnInProgress.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (onClickListener!=null){
                    onClickListener.setInProgress(complainListSearch.get(i));
                }
            }
        });

        h.btn_hold.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (onClickListener!=null){
                    onClickListener.setHold(complainListSearch.get(i));
                }
            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void checkStatus(String sat, TextView textView, String status,LinearLayout btn,CustomButton cls,CustomButton inP,CustomButton inH) {

        if (sat.equalsIgnoreCase("0")) {
            //open
            textView.setText(status);
            btn.setVisibility(View.VISIBLE);
            cls.setVisibility(View.GONE);
            inH.setVisibility(View.GONE);
            inP.setVisibility(View.VISIBLE);

            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_complaint_open));
            textView.setTextColor(ContextCompat.getColor(context, R.color.complaint_open_text));

        } else if (sat.equalsIgnoreCase("1")) {
            //close
            textView.setText(status);
            btn.setVisibility(View.GONE);
            cls.setVisibility(View.GONE);
            inH.setVisibility(View.GONE);
            inP.setVisibility(View.GONE);
            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_complaint_reopen));
            textView.setTextColor(ContextCompat.getColor(context, R.color.complaint_reopen_text));
        } else if (sat.equalsIgnoreCase("4")) {
            //reply
            textView.setText(status);
            btn.setVisibility(View.VISIBLE);
            cls.setVisibility(View.GONE);
            inH.setVisibility(View.GONE);
            inP.setVisibility(View.VISIBLE);
            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_complaint_reply));
            textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else if (sat.equalsIgnoreCase("2")) {
            //reopen
            textView.setText(status);
            btn.setVisibility(View.VISIBLE);
            cls.setVisibility(View.GONE);
            inH.setVisibility(View.GONE);
            inP.setVisibility(View.VISIBLE);
            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_complaint_open));
            textView.setTextColor(ContextCompat.getColor(context, R.color.complaint_open_text));
        } else if (sat.equalsIgnoreCase("3")) {
            //in progress
            textView.setText(status);
            btn.setVisibility(View.VISIBLE);
            cls.setVisibility(View.VISIBLE);
            inH.setVisibility(View.VISIBLE);
            inP.setVisibility(View.GONE);
            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_complaint_inprocess));
            textView.setTextColor(ContextCompat.getColor(context, R.color.complaint_inprocess_text));
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void upDateData(ComplainResponse complainResponse2) {
        complainsList = complainResponse2.getComplain();
        complainListSearch = complainResponse2.getComplain();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return complainListSearch.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void search(CharSequence charSequence, LinearLayout linearLayout, RecyclerView recyclerView) {
        try {
            String charString = charSequence.toString().trim();
            if (charString.isEmpty()) {
                complainListSearch = complainsList;
                recyclerView.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
            } else {
                int flag = 0;
                List<ComplainResponse.Complain> filteredList = new ArrayList<>();
                for (ComplainResponse.Complain row : complainsList) {

                    if (row.getCompalain_title().toLowerCase().contains(charString.toLowerCase()) ||
                            row.getComplain_no().toLowerCase().contains(charString.toLowerCase()) ||
                            row.getComplain_status().toLowerCase().contains(charString.toLowerCase())) {
                        filteredList.add(row);
                        flag = 1;
                    }
                }

                if (flag == 1) {
                    complainListSearch = filteredList;
                    recyclerView.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }

            }
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface SetOnClickListener {
        void onClick(int position, ComplainResponse.Complain complain);

        void onDelete(int position, ComplainResponse.Complain complain);

        void onRateClick(int position, ComplainResponse.Complain complain);

        void setHold(ComplainResponse.Complain complain);
        void setInProgress(ComplainResponse.Complain complain);
        void setClose(ComplainResponse.Complain complain);
        void setReqClose(ComplainResponse.Complain complain);
    }

    @SuppressLint("NonConstantResourceId")
    static class ComplainViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivStatus)
        ImageView ivStatus;
        @BindView(R.id.tcComplentStatus)
        TextView tcComplentStatus;
        @BindView(R.id.tvComplainNo)
        TextView tvComplainNo;
        @BindView(R.id.lin_click)
        LinearLayout linClick;
        @BindView(R.id.ivComplaintImage)
        ImageView ivComplaintImage;
        @BindView(R.id.tvCategoryTitle)
        TextView tvCategoryTitle;
        @BindView(R.id.tvComplaintCategory)
        TextView tvComplaintCategory;
        @BindView(R.id.tvComplainTitle)
        TextView tvComplainTitle;
        @BindView(R.id.tvComplainName)
        TextView tvComplainName;
        @BindView(R.id.tvComplainDecTitle)
        TextView tvComplainDecTitle;
        @BindView(R.id.tvComplainDec)
        TextView tvComplainDec;
        @BindView(R.id.tvComplainTatTitle)
        TextView tvComplainTatTitle;
        @BindView(R.id.tvComplainTat)
        TextView tvComplainTat;
        @BindView(R.id.lin_person_assign)
        LinearLayout linPersonAssign;
        @BindView(R.id.tvTechnicianNameTitle)
        TextView tvTechnicianNameTitle;
        @BindView(R.id.tvTechnicianName)
        TextView tvTechnicianName;
        @BindView(R.id.tvCreatedBy)
        TextView tvCreatedBy;
        @BindView(R.id.carAdmin)
        androidx.cardview.widget.CardView carAdmin;
        @BindView(R.id.cirAdminPic)
        de.hdodenhof.circleimageview.CircleImageView cirAdminPic;
        @BindView(R.id.tvAdminName)
        TextView tvAdminName;
        @BindView(R.id.tvAdminDate)
        TextView tvAdminDate;
        @BindView(R.id.tvAdminMsg)
        TextView tvAdminMsg;
        @BindView(R.id.lin_btn)
        LinearLayout linBtn;
        @BindView(R.id.btn_close)
        com.ajitmaurya.basicsetup.view.CustomButton btnClose;
        @BindView(R.id.btn_hold)
        com.ajitmaurya.basicsetup.view.CustomButton btn_hold;
        @BindView(R.id.btn_in_progress)
        com.ajitmaurya.basicsetup.view.CustomButton btnInProgress;


        ComplainViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
