package com.github.b3er.rxfirebase.auth;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.FirebaseAuth;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

final class CheckActionCodeOnSubscribe implements SingleOnSubscribe<ActionCodeResult> {
  private final FirebaseAuth instance;
  private final String code;

  CheckActionCodeOnSubscribe(FirebaseAuth instance, String code) {
    this.instance = instance;
    this.code = code;
  }

  @Override public void subscribe(SingleEmitter<ActionCodeResult> emitter) {
    if (!emitter.isDisposed()) {
      instance.checkActionCode(code).addOnCompleteListener(GmsTaskListeners.listener(emitter));
    }
  }
}
