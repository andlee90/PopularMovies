package com.example.android.popularmovies.async;
/*
I utilized HelmiB's answer from this StackOverflow question in order to arrive at the implementation
of this interface:

https://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
*/
public interface AsyncResult {
    void onTaskFinish(String output);
}
