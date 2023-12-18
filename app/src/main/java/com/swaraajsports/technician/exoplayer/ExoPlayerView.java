package com.swaraajsports.technician.exoplayer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import com.swaraajsports.technician.R;
import com.swaraajsports.technician.exoplayer.globalEnums.EnumAspectRatio;
import com.swaraajsports.technician.exoplayer.globalEnums.EnumResizeMode;
import com.swaraajsports.technician.exoplayer.globalInterfaces.ExoPlayerCallBack;
import com.swaraajsports.technician.exoplayer.utils.PublicFunctions;
import com.swaraajsports.technician.exoplayer.utils.PublicValues;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ExoPlayerView extends LinearLayout implements View.OnClickListener {

    setOnFullScreenListener setOnFullScreenListener;
    OnReleaseListener onReleaseListener;
    private Context context;
    private String currSource = "";
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private boolean isPreparing = false;
    private TypedArray typedArray = null;
    private boolean currPlayWhenReady = false;
    private EnumAspectRatio currAspectRatio = EnumAspectRatio.ASPECT_16_9;
    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView playerView;
    private ComponentListener componentListener;
    private LinearLayout linearLayoutRetry, linearLayoutLoading;
    private FrameLayout frameLayoutFullScreenContainer;
    private AppCompatImageButton imageViewEnterFullScreen, imageViewExitFullScreen;
    private ExoPlayerCallBack exoPlayerCallBack;

    public ExoPlayerView(Context context) {
        super(context);
        initializeView(context);
    }

    public ExoPlayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ExoPlayerView,
                0, 0);
        initializeView(context);
    }

    public ExoPlayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ExoPlayerView,
                0, 0);
        initializeView(context);
    }

    public void setOnFullScreenListener(setOnFullScreenListener setOnFullScreenListener) {
        this.setOnFullScreenListener = setOnFullScreenListener;
    }

    public void setOnReleaseListener(OnReleaseListener onReleaseListener) {
        this.onReleaseListener = onReleaseListener;
    }

    public interface OnReleaseListener {

        void onReleaseVideo(int position);

        void onReleaseVideo(SimpleExoPlayer simpleExoPlayer, PlayerView playerView);
    }

    private void initializeView(Context context) {
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_player_base, this, true);

        playerView = view.findViewById(R.id.simpleExoPlayerView);
        linearLayoutLoading = findViewById(R.id.linearLayoutLoading);
        linearLayoutRetry = findViewById(R.id.linearLayoutRetry);
        AppCompatButton buttonRetry = findViewById(R.id.appCompatButton_try_again);
        frameLayoutFullScreenContainer = playerView.findViewById(R.id.container_fullscreen);
        imageViewEnterFullScreen = playerView.findViewById(R.id.exo_enter_fullscreen);
        imageViewExitFullScreen = playerView.findViewById(R.id.exo_exit_fullscreen);

        componentListener = new ComponentListener();

        linearLayoutRetry.setOnClickListener(this);
        imageViewEnterFullScreen.setOnClickListener(this);
        imageViewExitFullScreen.setOnClickListener(this);
        buttonRetry.setOnClickListener(this);

        if (typedArray != null) {

            if (typedArray.hasValue(R.styleable.ExoPlayerView_exo_resize_mode)) {
                int resizeMode = typedArray.getInteger(R.styleable.ExoPlayerView_exo_resize_mode, EnumResizeMode.FILL.getValue());
                setResizeMode(EnumResizeMode.get(resizeMode));
            }

            if (typedArray.hasValue(R.styleable.ExoPlayerView_exo_aspect_ratio)) {
                int aspectRatio = typedArray.getInteger(R.styleable.ExoPlayerView_exo_aspect_ratio, EnumAspectRatio.ASPECT_16_9.getValue());
                setAspectRatio(EnumAspectRatio.get(aspectRatio));
            }

            if (typedArray.hasValue(R.styleable.ExoPlayerView_exo_full_screen)) {
                setShowFullScreen(typedArray.getBoolean(R.styleable.ExoPlayerView_exo_full_screen, false));
            }

            if (typedArray.hasValue(R.styleable.ExoPlayerView_exo_play_when_ready)) {
                setPlayWhenReady(typedArray.getBoolean(R.styleable.ExoPlayerView_exo_play_when_ready, false));
            }

            if (typedArray.hasValue(R.styleable.ExoPlayerView_exo_show_controller)) {
                setShowController(typedArray.getBoolean(R.styleable.ExoPlayerView_exo_show_controller, true));
            }

            typedArray.recycle();
        }

        initializePlayer();
    }

    public SimpleExoPlayer getPlayer() {
        return simpleExoPlayer;
    }

    public void initializePlayer() {

        if (simpleExoPlayer == null) {

            //Minimum Video you want to buffer while Playing
            int MIN_BUFFER_DURATION = 2000;
            //Max Video you want to buffer during PlayBack
            int MAX_BUFFER_DURATION = 3000;
            //Min Video you want to buffer before start Playing it
            int MIN_PLAYBACK_START_BUFFER = 1500;
            //Min video You want to buffer when user resumes video
            int MIN_PLAYBACK_RESUME_BUFFER = 2000;
            LoadControl loadControl = new DefaultLoadControl.Builder()
                    .setAllocator(new DefaultAllocator(true, 16))
                    .setBufferDurationsMs(MIN_BUFFER_DURATION,
                           +MAX_BUFFER_DURATION,
                            MIN_PLAYBACK_START_BUFFER,
                            MIN_PLAYBACK_RESUME_BUFFER)
                    .setTargetBufferBytes(-1)
                    .setPrioritizeTimeOverSizeThresholds(true)
                    .createDefaultLoadControl();

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(context);
            simpleExoPlayer = new SimpleExoPlayer.Builder(context)
                    .setTrackSelector(trackSelector)
                    .setBandwidthMeter(bandwidthMeter)
                    .setLoadControl(loadControl)
                    .build();

            playerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.setPlayWhenReady(currPlayWhenReady);
            simpleExoPlayer.seekTo(currentWindow, playbackPosition);
            simpleExoPlayer.addListener(componentListener);
        }
    }

    public void setSource(String source) {
        MediaSource mediaSource = buildMediaSource(source, null);
        if (mediaSource != null) {
            if (simpleExoPlayer != null) {
                showProgress();
                simpleExoPlayer.prepare(mediaSource, true, false);
            }
        }
    }

    public void setSource(String source, HashMap<String, String> extraHeaders) {
        MediaSource mediaSource = buildMediaSource(source, extraHeaders);
        if (mediaSource != null) {
            if (simpleExoPlayer != null) {
                showProgress();
                simpleExoPlayer.prepare(mediaSource, true, false);
            }
        }
    }

    private MediaSource buildMediaSource(String source, HashMap<String, String> extraHeaders) {

        if (source == null) {
            Toast.makeText(context, "Input Is Invalid.", Toast.LENGTH_SHORT).show();
            return null;
        }

        this.currSource = source;

        boolean validUrl = URLUtil.isValidUrl(source);

        Uri uri = Uri.parse(source);
        if (uri == null || uri.getLastPathSegment() == null) {
            Toast.makeText(context, "Uri Converter Failed, Input Is Invalid.", Toast.LENGTH_SHORT).show();
            return null;
        }

        if (validUrl /*&& uri.getLastPathSegment().contains(PublicValues.KEY_MP4)*/) {

            DefaultHttpDataSourceFactory sourceFactory = new DefaultHttpDataSourceFactory(PublicValues.KEY_USER_AGENT);
            if (extraHeaders != null) {
                for (Map.Entry<String, String> entry : extraHeaders.entrySet())
                    sourceFactory.getDefaultRequestProperties().set(entry.getKey(), entry.getValue());
            }

            return new ProgressiveMediaSource.Factory(sourceFactory)
                    .createMediaSource(uri);

        } else {
            return new ProgressiveMediaSource.Factory(new DefaultDataSourceFactory(context, PublicValues.KEY_USER_AGENT))
                    .createMediaSource(uri);

        }
    }

    public void releasePlayer() {
        if (simpleExoPlayer != null) {
            playbackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            currPlayWhenReady = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.removeListener(componentListener);
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    public void releasePlayer(SimpleExoPlayer simpleExoPlayer) {
        if (simpleExoPlayer != null) {
            playbackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            currPlayWhenReady = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.removeListener(componentListener);
            simpleExoPlayer.release();
        }
    }

    public void setPlayWhenReady(boolean playWhenReady) {
        this.currPlayWhenReady = playWhenReady;
        if (simpleExoPlayer != null)
            simpleExoPlayer.setPlayWhenReady(playWhenReady);
    }

    public void stopPlayer() {
        if (simpleExoPlayer != null)
            simpleExoPlayer.stop();
    }

    public void pausePlayer() {
        if (simpleExoPlayer != null)
            simpleExoPlayer.setPlayWhenReady(false);
    }

    public void setShowController(boolean showController) {
        if (playerView == null)
            return;

        if (showController) {
            playerView.showController();
            playerView.setUseController(true);
        } else {
            playerView.hideController();
            playerView.setUseController(false);
        }
    }

    public void setResizeMode(EnumResizeMode resizeMode) {
        switch (resizeMode) {

            case FIT:
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                break;

            case FILL:
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                break;

            case ZOOM:
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
                break;

            default:
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        }
    }

    public void setShowFullScreen(boolean showFullScreen) {
        if (showFullScreen)
            frameLayoutFullScreenContainer.setVisibility(VISIBLE);
        else
            frameLayoutFullScreenContainer.setVisibility(GONE);
    }

    public void setAspectRatio(EnumAspectRatio aspectRatio) {
        this.currAspectRatio = aspectRatio;
        int value = PublicFunctions.getScreenWidth();

        switch (aspectRatio) {

            case ASPECT_1_1:
                playerView.setLayoutParams(new FrameLayout.LayoutParams(value, value));
                break;

            case ASPECT_4_3:
                playerView.setLayoutParams(new FrameLayout.LayoutParams(value, (3 * value) / 4));
                break;

            case ASPECT_16_9:
                playerView.setLayoutParams(new FrameLayout.LayoutParams(value, (9 * value) / 16));
                break;

            case ASPECT_MATCH:
                playerView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                break;

            case ASPECT_MP3:
                playerView.setControllerShowTimeoutMs(0);
                playerView.setControllerHideOnTouch(false);
                int mp3Height = getContext().getResources().getDimensionPixelSize(R.dimen.player_controller_base_height);
                playerView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mp3Height));
                break;

            case UNDEFINE:
            default:
                int baseHeight = (int) getResources().getDimension(R.dimen.player_base_height);
                playerView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, baseHeight));
                break;
        }
    }

    private Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    @Override
    protected void onAttachedToWindow() {
        initializePlayer();
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        pausePlayer();
        initializePlayer();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checking the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // First Hide other objects (listview or recyclerview), better hide them using Gone.
            hideSystemUi();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) playerView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            playerView.setLayoutParams(params);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // unhide your objects here.
            showSystemUi();
            setAspectRatio(currAspectRatio);
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        if (playerView == null)
            return;

        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @SuppressLint("InlinedApi")
    private void showSystemUi() {
        if (playerView == null)
            return;

        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    @Override
    public void onClick(View v) {

        int targetViewId = v.getId();
        if (targetViewId == R.id.appCompatButton_try_again) {
            hideRetry();
            setSource(currSource);
        } else if (targetViewId == R.id.exo_enter_fullscreen) {
            //enterFullScreen();
            setOnFullScreenListener.onFullScreen();
        } else if (targetViewId == R.id.exo_exit_fullscreen) {
            exitFullScreen();
        }
    }

    public void setExoPlayerCallBack(ExoPlayerCallBack exoPlayerCallBack) {
        this.exoPlayerCallBack = exoPlayerCallBack;
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void enterFullScreen() {
        imageViewExitFullScreen.setVisibility(VISIBLE);
        imageViewEnterFullScreen.setVisibility(GONE);

        if (getActivity() != null)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void exitFullScreen() {
        imageViewExitFullScreen.setVisibility(GONE);
        imageViewEnterFullScreen.setVisibility(VISIBLE);

        if (getActivity() != null)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void showProgress() {
        hideAll();
        if (linearLayoutLoading != null)
            linearLayoutLoading.setVisibility(VISIBLE);
    }

    private void hideProgress() {
        if (linearLayoutLoading != null)
            linearLayoutLoading.setVisibility(GONE);
    }

    private void showRetry() {
        hideAll();
        if (linearLayoutRetry != null)
            linearLayoutRetry.setVisibility(VISIBLE);
    }

    private void hideRetry() {
        if (linearLayoutRetry != null)
            linearLayoutRetry.setVisibility(GONE);
    }

    private void hideAll() {
        if (linearLayoutRetry != null)
            linearLayoutRetry.setVisibility(GONE);
        if (linearLayoutLoading != null)
            linearLayoutLoading.setVisibility(GONE);
    }

    public interface setOnFullScreenListener {

        void onFullScreen();

    }

    public class ComponentListener implements Player.EventListener {

        String TAG = ComponentListener.class.getSimpleName();

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            String stateString;
            switch (playbackState) {
                case Player.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    break;
                case Player.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    break;
                case Player.STATE_READY:
                    if (isPreparing) {
                        // this is accurate
                        isPreparing = false;
                    }
                    hideProgress();
                    stateString = "ExoPlayer.STATE_READY     -";
                    break;
                case Player.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }

            Log.d(TAG, "changed state to " + stateString
                    + " playWhenReady: " + playWhenReady);
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {

        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

        }

        @Override
        public void onTracksChanged(@NotNull TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        public void onPlayerError(@NotNull ExoPlaybackException error) {
            showRetry();

            if (exoPlayerCallBack != null) {
                exoPlayerCallBack.onError(error);
                //initializePlayer();
            }
        }

        @Override
        public void onPositionDiscontinuity(int reason) {

        }

        @Override
        public void onPlaybackParametersChanged(@NotNull PlaybackParameters playbackParameters) {

        }


    }
}
