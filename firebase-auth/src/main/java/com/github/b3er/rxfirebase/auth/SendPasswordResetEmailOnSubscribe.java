package com.github.b3er.rxfirebase.auth;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.auth.FirebaseAuth;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;

final class SendPasswordResetEmailOnSubscribe implements CompletableOnSubscribe {

  private final FirebaseAuth instance;

  private final String email;

  SendPasswordResetEmailOnSubscribe(FirebaseAuth instance, String email) {
    this.instance = instance;
    this.email = email;
  }

  @Override public void subscribe(final CompletableEmitter emitter) {
    instance.sendPasswordResetEmail(email)
        .addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
