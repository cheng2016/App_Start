package com.huiyao.gamecenter.module.splash;

import android.app.Activity;

/*******************************************
 * 
 * @CLASS:HY_ISplash
 * @AUTHOR:smile
 *******************************************/

public abstract interface HY_ISplash {
	public abstract void play(Activity paramActivity,
                              HY_SplashListener listener, int index, int length);
}
