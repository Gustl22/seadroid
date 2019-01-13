package com.seafile.seadroid2.cameraupload;


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.seafile.seadroid2.SettingsManager;

/**
 * This service monitors the media provider content provider for new images/videos.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MediaSchedulerService extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        System.out.println("MediaSchedulerService.onStartJob");
        SettingsManager settingsManager = SettingsManager.instance();
        CameraUploadManager cameraManager = new CameraUploadManager(getApplicationContext());
        if (cameraManager.isCameraUploadEnabled() && settingsManager.isVideosUploadAllowed()) {
            // Log.i(DEBUG_TAG, "Doing a full resync of all media content.");
            cameraManager.performFullSync();
        }
        jobFinished(jobParameters, true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
