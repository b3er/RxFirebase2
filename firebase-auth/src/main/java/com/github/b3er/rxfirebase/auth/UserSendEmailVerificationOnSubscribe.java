package com.github.b3er.rxfirebase.auth;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.auth.FirebaseUser;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;

final class UserSendEmailVerificationOnSubscribe implements CompletableOnSubscribe {

  private final FirebaseUser user;

  UserSendEmailVerificationOnSubscribe(FirebaseUser user) {
    this.user = user;
  }

  @Override public void subscribe(CompletableEmitter emitter) {
    user.sendEmailVerification().addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
