package com.github.b3er.rxfirebase.auth;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.auth.FirebaseAuth;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;

final class ApplyActionCodeOnSubscribe implements CompletableOnSubscribe {
  private final FirebaseAuth instance;
  private final String code;

  ApplyActionCodeOnSubscribe(FirebaseAuth instance, String code) {
    this.instance = instance;
    this.code = code;
  }

  @Override public void subscribe(CompletableEmitter emitter) {
    if (!emitter.isDisposed()) {
      instance.applyActionCode(code).addOnCompleteListener(GmsTaskListeners.listener(emitter));
    }
  }
}
