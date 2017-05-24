package com.mengqi.dong.baseioclibrary;

import android.app.Activity;
import android.view.View;

/**
 * Created by dong on 2017/5/7.
 *
 * view的findViewById的辅助类
 */

public class ViewFinder {
    private Activity mActivity;
    private View mView;
    public ViewFinder(View view){
        this.mView = view;
    }

    public ViewFinder(Activity activity){
        this.mActivity = activity;
    }

    public View findViewById(int viewId){
        return mActivity!=null?mActivity.findViewById(viewId):mView.findViewById(viewId);
    }
}
