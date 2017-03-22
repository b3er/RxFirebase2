package com.github.b3er.rxfirebase.auth;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.auth.FirebaseAuth;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;

final class ConfirmPasswordResetOnSubscribe implements CompletableOnSubscribe {
  private final FirebaseAuth instance;
  private final String code;
  private final String newPassword;

  ConfirmPasswordResetOnSubscribe(FirebaseAuth instance, String code, String newPassword) {
    this.instance = instance;
    this.code = code;
    this.newPassword = newPassword;
  }

  @Override public void subscribe(CompletableEmitter emitter) {
    if (!emitter.isDisposed()) {
      instance.confirmPasswordReset(code, newPassword)
          .addOnCompleteListener(GmsTaskListeners.listener(emitter));
    }
  }
}
