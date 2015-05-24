package vandy.mooc.activities;

import vandy.mooc.operations.AcronymOps;
import vandy.mooc.operations.AcronymOpsImpl;
import vandy.mooc.utils.RetainedFragmentManager;
import vandy.mooc.utils.Utils;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * The main Activity that prompts the user for Acronyms to expand via
 * various implementations of AcronymServiceSync and
 * AcronymServiceAsync and view via the results.  Extends
 * LifecycleLoggingActivity so its lifecycle hook methods are logged
 * automatically.  This implementation uses the
 * RetainedFragmentManager class to handle runtime reconfigurations
 * robustly.  As a result, MainActivity plays the role of the
 * "Caretaker" in the Memento pattern.
 */
public class MainActivity extends LifecycleLoggingActivity {
    /**
     * Used to retain the AcronymOps state between runtime configuration
     * changes.
     */
    protected final RetainedFragmentManager mRetainedFragmentManager = 
        new RetainedFragmentManager(this.getFragmentManager(),
                                    TAG);

    /**
     * Provides acronym-related operations.
     */
    private AcronymOps mAcronymOps;

    /**
     * Hook method called when a new instance of Activity is created.
     * One time initialization code goes here, e.g., runtime
     * configuration changes.
     *
     * @param Bundle object that contains saved state information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Always call super class for necessary
        // initialization/implementation.
        super.onCreate(savedInstanceState);

        // Create the AcronymOps object one time.
        mAcronymOps = new AcronymOpsImpl(this);
    }

    /**
     * Hook method called after onCreate() or after onRestart() (when
     * the activity is being restarted from stopped state).  
     */	
    @Override
    protected void onStart(){
        // Always call super class for necessary
        // initialization/implementation.
        super.onStart();

        // Initiate the service binding protocol.
        mAcronymOps.bindService();
    }

    /**
     * Hook method called by Android when this Activity becomes
     * invisible.
     */
    @Override
    protected void onStop() {
        // Unbind from the Service.
        mAcronymOps.unbindService();

        // Always call super class for necessary operations when
        // stopping.
        super.onStop();
    }

    /**
     * Hook method invoked when the screen orientation changes.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mAcronymOps.onConfigurationChanged(newConfig);
    }

    /*
     * Initiate the synchronous acronym lookup when the user presses
     * the "Look Up Sync" button.
     */
    public void expandAcronymSync(View v) {
        mAcronymOps.expandAcronymSync(v);
    }

    /*
     * Initiate the asynchronous acronym lookup when the user presses
     * the "Look Up Async" button.
     */
    public void expandAcronymAsync(View v) {
        mAcronymOps.expandAcronymAsync(v);
    }
}
