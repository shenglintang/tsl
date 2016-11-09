package com.sample.drawpassword;

import com.sample.drawpassword.component.LocusPassWordView;
import com.sample.drawpassword.component.LocusPassWordView.OnCompleteListener;
import com.sample.drawpassword.util.Md5Utils;
import com.sample.drawpassword.util.SharedPreferencesHelper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	private LocusPassWordView mPwdView;
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.draw_pwd);
		mContext = getApplicationContext();
		mPwdView = (LocusPassWordView) this.findViewById(R.id.mPassWordView);
		
		mPwdView.setOnCompleteListener(new OnCompleteListener() {
			@Override
			public void onComplete(String mPassword) {
				SharedPreferencesHelper sph = SharedPreferencesHelper.getInstance(getApplicationContext());
				String pwd = sph.getString("password", "");
				Md5Utils md5 = new Md5Utils();
				boolean passed = false;
				if (pwd.length() == 0) {
					sph.putString("password", md5.toMd5(mPassword, ""));
					Toast.makeText(mContext, mContext.getString(R.string.pwd_setted), Toast.LENGTH_LONG).show();
					return;
				} else {
					String encodedPwd = md5.toMd5(mPassword, "");
					if (encodedPwd.equals(pwd)) {
						passed = true;
					} else {
						mPwdView.markError();
					}
				}
				
				if (passed) {
					Log.d("hcj", "password is correct!");
					Toast.makeText(mContext, mContext.getString(R.string.pwd_correct), Toast.LENGTH_LONG).show();
//					finish();
					mPwdView.clearPassword();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
