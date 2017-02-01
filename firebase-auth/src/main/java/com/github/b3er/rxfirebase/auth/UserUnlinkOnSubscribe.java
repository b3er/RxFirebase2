package com.github.b3er.rxfirebase.auth;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

final class UserUnlinkOnSubscribe implements SingleOnSubscribe<AuthResult> {

  private final FirebaseUser user;

  private final String provider;

  UserUnlinkOnSubscribe(FirebaseUser user, String provider) {
    this.user = user;
    this.provider = provider;
  }

  @Override public void subscribe(SingleEmitter<AuthResult> emitter) {
    user.unlink(provider).addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
