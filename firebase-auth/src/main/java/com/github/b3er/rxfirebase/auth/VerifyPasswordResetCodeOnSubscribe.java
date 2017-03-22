package com.github.b3er.rxfirebase.auth;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.auth.FirebaseAuth;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

final class VerifyPasswordResetCodeOnSubscribe implements SingleOnSubscribe<String> {
  private final FirebaseAuth instance;
  private final String code;

  VerifyPasswordResetCodeOnSubscribe(FirebaseAuth instance, String code) {
    this.instance = instance;
    this.code = code;
  }

  @Override public void subscribe(SingleEmitter<String> emitter) {
    if (!emitter.isDisposed()) {
      instance.verifyPasswordResetCode(code)
          .addOnCompleteListener(GmsTaskListeners.listener(emitter));
    }
  }
}
