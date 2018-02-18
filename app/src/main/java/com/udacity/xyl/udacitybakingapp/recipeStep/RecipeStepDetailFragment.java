package com.udacity.xyl.udacitybakingapp.recipeStep;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.xyl.udacitybakingapp.R;
import com.udacity.xyl.udacitybakingapp.RecipeUtils;
import com.udacity.xyl.udacitybakingapp.recipeList.RecipeBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.udacity.xyl.udacitybakingapp.RecipeUtils.steps;

/**
 * A fragment representing a single Recipe Step detail screen.
 * This fragment is either contained in a {@link RecipeStepListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeStepDetailActivity}
 * on handsets.
 */
public class RecipeStepDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */

    public static final String RECIPE_STEP = "recipe_step";

    private static final String TAG = "StepDetailFragment";

    private SimpleExoPlayer videoPlayer;
    @BindView(R.id.video_player_view)
    SimpleExoPlayerView playerView;//PlaybackControlView?

    @BindView(R.id.tv_step_instructions)
    TextView tv_step_instructions;
    @BindView(R.id.btn_step_next)
    Button btn_next;
    @BindView(R.id.btn_step_prev)
    Button btn_prev;

    private String videoUrl;
    private RecipeBean.StepsBean step;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeStepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(RECIPE_STEP)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            step = (RecipeBean.StepsBean) getArguments().getSerializable(RECIPE_STEP);
            Log.d(TAG, "onCreate: step=" + step.toString());
            Activity activity = this.getActivity();
            Toolbar appBarLayout = (Toolbar) activity.findViewById(R.id.toolbar);
            if (appBarLayout != null) {
                appBarLayout.setTitle("Step " + step.getId());
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipestep_detail, container, false);
        ButterKnife.bind(this, rootView);
        tv_step_instructions.setMovementMethod(new ScrollingMovementMethod());
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        videoUrl = step.getVideoURL();
        if (!TextUtils.isEmpty(videoUrl)) {
            playerView.setVisibility(View.VISIBLE);
            initVideoPlayer();
        } else {
            playerView.setVisibility(View.INVISIBLE);
        }
        if (step != null && !TextUtils.isEmpty(step.getDescription())) {
            tv_step_instructions.setText(step.getDescription());
        }
    }

    private void initVideoPlayer() {
//        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

// 2. Create a default LoadControl
//        LoadControl loadControl = new DefaultLoadControl();

// 3. Create the player
        videoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

        // Bind the player to the view.
        playerView.setPlayer(videoPlayer);

        // Measures bandwidth during playback. Can be null if not required.
// Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), getActivity().getPackageName()));
// Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
// This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(videoUrl),
                dataSourceFactory, extractorsFactory, null, null);
// Prepare the player with the source.
        videoPlayer.prepare(videoSource);
        videoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (videoPlayer != null) {
            videoPlayer.release();
        }

    }

    @OnClick(R.id.btn_step_next)
    void toNextStep() {
        int index = step.getId();
        int nextIndex = index + 1;
        if (nextIndex <= RecipeUtils.steps.size() - 1) {
            onStop();
            step = steps.get(nextIndex);
            onStart();
        }else{
            Toast.makeText(getActivity(),R.string.this_is_final_step,Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_step_prev)
    void toPreviousStep() {
        int index = step.getId();
        int nextIndex = index - 1;
        if (nextIndex >= 0) {
            onStop();
            step = steps.get(nextIndex);
            onStart();
        }else{
            Toast.makeText(getActivity(),R.string.this_is_first_step,Toast.LENGTH_SHORT).show();
        }
    }
}
