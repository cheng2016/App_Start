package com.huiyao.gamecenter.module.login;

import com.huiyao.gamecenter.data.entity.UserInfoEntity;

public abstract interface HY_LoginCallBack {
	public abstract void onLoginSuccess(UserInfoEntity user);
	public abstract void onLoginFailed(int code, String message);
	public abstract void onLogout();
}
