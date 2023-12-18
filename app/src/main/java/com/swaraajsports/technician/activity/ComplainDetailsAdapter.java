package com.swaraajsports.technician.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.ajitmaurya.basicsetup.utility.BasicFunctions;
import com.ajitmaurya.basicsetup.utility.OnSingleClickListener;
import com.swaraajsports.technician.R;
import com.swaraajsports.technician.exoplayer.ExoPlayerView;
import com.swaraajsports.technician.response.ComplainResponse;
import com.google.android.exoplayer2.Player;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComplainDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<ComplainResponse.Track> complainResponse;
    private final Context context;
    private SetOnClickListener onClickListener;

    ComplainDetailsAdapter(Context context, List<ComplainResponse.Track> complainResponse) {

        this.context = context;
        this.complainResponse = complainResponse;
    }

    public void setOnClickListener(SetOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder vh = null;
        if (viewType == 0) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_complaint_resident_new, viewGroup, false);
            vh = new ResidentViewHolder(v);
        } else if (viewType == 1) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_complaint_society_new, viewGroup, false);
            vh = new SocietyViewHolder(v);
        } else if (viewType == 2) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_complaint_middle_date, viewGroup, false);
            vh = new DateViewHolder(v);
        }
        return vh;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        if (viewHolder instanceof DateViewHolder) {
            DateViewHolder holder = (DateViewHolder) viewHolder;
            holder.tvDate.setText(complainResponse.get(position).getMsg_date_view());
        }
        else if (viewHolder instanceof ResidentViewHolder) {
            ResidentViewHolder holder = (ResidentViewHolder) viewHolder;

            holder.tvComplainDetails.setText(complainResponse.get(position).getComplains_track_msg());
            holder.tvComplainDate.setText(complainResponse.get(position).getComplains_track_date_time());
            holder.tvComplainStatus.setVisibility(View.VISIBLE);
            if (complainResponse.get(position).getComplaint_status_int() != null && complainResponse.get(position).getComplaint_status_int().length() > 0) {

                if (complainResponse.get(position).getComplaint_status_int().equalsIgnoreCase("0")) {
                    holder.tvComplainStatus.setText("Reply by "+complainResponse.get(position).getAdminName()+"");
                    holder.tvComplainStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_complaint_open));
                    holder.tvComplainStatus.setTextColor(ContextCompat.getColor(context, R.color.complaint_open_text));
                } else if (complainResponse.get(position).getComplaint_status_int().equalsIgnoreCase("3")) {
                    holder.tvComplainStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_complaint_inprocess));
                    holder.tvComplainStatus.setTextColor(ContextCompat.getColor(context, R.color.complaint_inprocess_text));
                    holder.tvComplainStatus.setText("In-process by "+complainResponse.get(position).getAdminName()+"");

                } else if (complainResponse.get(position).getComplaint_status_int().equalsIgnoreCase("2")) {
                    holder.tvComplainStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_complaint_open));
                    holder.tvComplainStatus.setTextColor(ContextCompat.getColor(context, R.color.complaint_open_text));
                    holder.tvComplainStatus.setText("Re-open by "+complainResponse.get(position).getAdminName()+"");

                } else if (complainResponse.get(position).getComplaint_status_int().equalsIgnoreCase("4")) {
                    holder.tvComplainStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_complaint_inprocess));
                    holder.tvComplainStatus.setTextColor(ContextCompat.getColor(context, R.color.complaint_inprocess_text));
                    holder.tvComplainStatus.setText("Hold by "+complainResponse.get(position).getAdminName()+"");

                } else if (complainResponse.get(position).getComplaint_status_int().equalsIgnoreCase("1") ) {
                    holder.tvComplainStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_complaint_reopen));
                    holder.tvComplainStatus.setTextColor(ContextCompat.getColor(context, R.color.complaint_reopen_text));
                    holder.tvComplainStatus.setText("Close by "+complainResponse.get(position).getAdminName()+"");

                }
            } else {
                holder.tvComplainStatus.setVisibility(View.GONE);
            }

            if (complainResponse.get(position).getComplains_track_img() != null &&
                    complainResponse.get(position).getComplains_track_img().length() > 5) {
                holder.ivComplain.setVisibility(View.VISIBLE);


                setImageViewOpen(context, holder.ivComplain, complainResponse.get(position).getComplains_track_img(),holder.iv_paly );


            } else {
                holder.ivComplain.setVisibility(View.GONE);
            }

            if (complainResponse.get(position).getComplains_track_voice() != null &&
                    complainResponse.get(position).getComplains_track_voice().length() > 5) {
                holder.andExoPlayerView.setVisibility(View.VISIBLE);

                try {
                    holder.andExoPlayerView.setSource(complainResponse.get(position).getComplains_track_voice());

                    holder.andExoPlayerView.getPlayer().addListener(new Player.Listener() {
                        @Override
                        public void onPlaybackStateChanged(@Player.State int state) {
                            if (state == Player.EVENT_IS_PLAYING_CHANGED) {
                                if (onClickListener != null) {
                                    onClickListener.isPlaying(complainResponse.get(position), position, holder.andExoPlayerView);
                                }
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                holder.andExoPlayerView.setVisibility(View.GONE);
                //Tools.displayImage(context,holder.ivComplain,complainResponse.get(position).getComplainsTrackImg());
            }

            holder.ivComplain.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onImageClick(complainResponse.get(position).getComplains_track_img(), position, holder.andExoPlayerView);
                    }
                }
            });
        }
        else if (viewHolder instanceof SocietyViewHolder) {

            SocietyViewHolder holder = (SocietyViewHolder) viewHolder;
            holder.tvComplainSocietyMessage.setText(complainResponse.get(position).getComplains_track_msg());
            holder.tvComplainSocietyDate.setText(complainResponse.get(position).getComplains_track_date_time());
            if (complainResponse.get(position).getComplaint_status_int() != null && complainResponse.get(position).getComplaint_status_int().length() > 0) {

                if (complainResponse.get(position).getAdminName() != null && complainResponse.get(position).getAdminName().length() > 0) {
                    holder.tvComplainStatus.setText(complainResponse.get(position).getComplaintStatusView() + "  by " + complainResponse.get(position).getAdminName());

                } else {
                    holder.tvComplainStatus.setText(complainResponse.get(position).getComplaintStatusView()+"");

                }
                holder.tvComplainStatus.setVisibility(View.VISIBLE);

                if (complainResponse.get(position).getComplaint_status_int().equalsIgnoreCase("0")) {
                    holder.tvComplainStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_complaint_open));
                    holder.tvComplainStatus.setTextColor(ContextCompat.getColor(context, R.color.complaint_open_text));
                } else if (complainResponse.get(position).getComplaint_status_int().equalsIgnoreCase("3")) {
                    holder.tvComplainStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_complaint_inprocess));
                    holder.tvComplainStatus.setTextColor(ContextCompat.getColor(context, R.color.complaint_inprocess_text));
                } else if (complainResponse.get(position).getComplaint_status_int().equalsIgnoreCase("2")) {
                    holder.tvComplainStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_complaint_open));
                    holder.tvComplainStatus.setTextColor(ContextCompat.getColor(context, R.color.complaint_open_text));
                } else if (complainResponse.get(position).getComplaint_status_int().equalsIgnoreCase("4")) {
                    holder.tvComplainStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_complaint_reply));
                    holder.tvComplainStatus.setTextColor(ContextCompat.getColor(context, R.color.white));
                } else if (complainResponse.get(position).getComplaint_status_int().equalsIgnoreCase("1") ) {
                    holder.tvComplainStatus.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_corner_complaint_reopen));
                    holder.tvComplainStatus.setTextColor(ContextCompat.getColor(context, R.color.complaint_reopen_text));
                }
            } else {
                holder.tvComplainStatus.setVisibility(View.GONE);
            }
            if (complainResponse.get(position).getComplains_track_img() != null &&
                    complainResponse.get(position).getComplains_track_img().length() > 5) {
                holder.ivComplain.setVisibility(View.VISIBLE);
                setImageViewOpen(context, holder.ivComplain, complainResponse.get(position).getComplains_track_img(),holder.iv_paly);

            } else {
                holder.ivComplain.setVisibility(View.GONE);
                //Tools.displayImage(context,holder.ivComplain,complainResponse.get(position).getComplainsTrackImg());
            }

            if (complainResponse.get(position).getComplains_track_voice() != null &&
                    complainResponse.get(position).getComplains_track_voice().length() > 5) {
                holder.andExoPlayerView.setVisibility(View.VISIBLE);
                try {
                    holder.andExoPlayerView.setSource(complainResponse.get(position).getComplains_track_voice());

                    holder.andExoPlayerView.getPlayer().addListener(new Player.Listener() {
                        @Override
                        public void onPlaybackStateChanged(@Player.State int state) {
                            if (state == Player.EVENT_IS_PLAYING_CHANGED) {
                                if (onClickListener != null) {
                                    onClickListener.isPlaying(complainResponse.get(position), position, holder.andExoPlayerView);
                                }
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                holder.andExoPlayerView.setVisibility(View.GONE);
                //Tools.displayImage(context,holder.ivComplain,complainResponse.get(position).getComplainsTrackImg());
            }

            holder.ivComplain.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    if (onClickListener != null) {
                        onClickListener.onImageClick(complainResponse.get(position).getComplains_track_img(), position, holder.andExoPlayerView);
                    }
                }
            });
        }
    }

    private void setImageViewOpen(Context context, ImageView ivComplain, String uri,ImageView iv) {

        iv.setVisibility(View.GONE);
        if (uri != null && uri.length() > 0 && uri.contains(".")) {
            String extension = uri.substring(uri.lastIndexOf("."));

            if (extension.equalsIgnoreCase(".png") ||
                    extension.equalsIgnoreCase(".jpg") ||
                    extension.equalsIgnoreCase(".jpeg")) {

                BasicFunctions.displayImageBG(context, ivComplain, uri);

            } else if (extension.equalsIgnoreCase(".pdf")) {
                BasicFunctions.displayImageRound(context, ivComplain, R.drawable.doc);


            } else if (extension.equalsIgnoreCase(".mp4") || extension.equalsIgnoreCase(".3gp") || extension.equalsIgnoreCase(".mkv")|| extension.equalsIgnoreCase(".mov")) {
                iv.setVisibility(View.VISIBLE);
                BasicFunctions.displayImageBG(context, ivComplain, uri);

            } else if (extension.equalsIgnoreCase(".doc")) {
                BasicFunctions.displayImageRound(context, ivComplain, R.drawable.doc);


            } else if (extension.equalsIgnoreCase(".docx")) {
                BasicFunctions.displayImageRound(context, ivComplain, R.drawable.doc);


            } else {
                BasicFunctions.displayImageRound(context, ivComplain, R.drawable.doc);

            }

        }


    }

    @Override
    public int getItemViewType(int position) {
        int t = 0;
        if (complainResponse.get(position).isDate()) {
            t = 2;
        } else {
            if (complainResponse.get(position).getComplains_track_by() != null && complainResponse.get(position).getComplains_track_by().equalsIgnoreCase("0")) {
                t = 1;
            }
            if (complainResponse.get(position).getComplains_track_by() != null && complainResponse.get(position).getComplains_track_by().equalsIgnoreCase("1")) {
                t = 0;
            }
        }
        return t;
    }

    @Override
    public int getItemCount() {
        return complainResponse.size();
    }

    public interface SetOnClickListener {
        void onClick(boolean isUpdate);

        void onImageClick(String url, int pos, ExoPlayerView andExoPlayerView);

        void isPlaying(ComplainResponse.Track track, int pos, ExoPlayerView andExoPlayerView);
    }

    @SuppressLint("NonConstantResourceId")
    static class ResidentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvComplainDetails)
        TextView tvComplainDetails;
        @BindView(R.id.tvComplainDate)
        TextView tvComplainDate;
        @BindView(R.id.ivComplain)
        ImageView ivComplain;
        @BindView(R.id.andExoPlayerView)
        ExoPlayerView andExoPlayerView;
        @BindView(R.id.tvComplainStatus)
        TextView tvComplainStatus;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.iv_paly)
        ImageView iv_paly;

        ResidentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @SuppressLint("NonConstantResourceId")
    static class SocietyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvComplainSocietyMessage)
        TextView tvComplainSocietyMessage;
        @BindView(R.id.tvComplainSocietyDate)
        TextView tvComplainSocietyDate;
        @BindView(R.id.ivComplain)
        ImageView ivComplain;
        @BindView(R.id.andExoPlayerView)
        ExoPlayerView andExoPlayerView;
        @BindView(R.id.tvComplainStatus)
        TextView tvComplainStatus;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.iv_paly)
        ImageView iv_paly;

        SocietyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @SuppressLint("NonConstantResourceId")
    static class DateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDate)
        TextView tvDate;

        DateViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
