package com.kickstarter.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.kickstarter.R;
import com.kickstarter.libs.BaseActivity;
import com.kickstarter.libs.qualifiers.RequiresPresenter;
import com.kickstarter.presenters.TwoFactorPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

@RequiresPresenter(TwoFactorPresenter.class)
public class TwoFactorActivity extends BaseActivity<TwoFactorPresenter> {
  public @Bind(R.id.code) EditText codeEditText;
  public @Bind(R.id.resend_button) Button resendButton;
  public @Bind(R.id.login_button) Button loginButton;

  @Override
  protected void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.two_factor_layout);
    ButterKnife.bind(this);
  }

  public void setLoginEnabled(final boolean enabled) {
    loginButton.setEnabled(enabled);
  }

  @OnTextChanged(R.id.code)
  public void codeEditTextOnTextChanged(@NonNull final CharSequence code) {
    presenter.inputs().code(code.toString());
  }

  @OnClick(R.id.resend_button)
  public void resendButtonOnClick(@NonNull final View view) {
    presenter.inputs().resendClick(view);
  }

  @OnClick(R.id.login_button)
  public void loginButtonOnClick(@NonNull final View view) {
    presenter.inputs().loginClick(view);
  }

  public void onSuccess() {
    if (forward()) {
      setResult(Activity.RESULT_OK);
      finish();
    } else {
      final Intent intent = new Intent(this, DiscoveryActivity.class)
        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      startActivity(intent);
    }
  }

  public String email () {
    return getIntent().getExtras().getString(getString(R.string.intent_email));
  }

  public String password () {
    return getIntent().getExtras().getString(getString(R.string.intent_password));
  }

  public boolean forward () {
    return getIntent().getBooleanExtra(getString(R.string.intent_forward), false);
  }
}
