package com.github.b3er.rxfirebase.auth;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;

final class UserReauthenticateOnSubscribe implements CompletableOnSubscribe {

  private final FirebaseUser user;

  private final AuthCredential credential;

  UserReauthenticateOnSubscribe(FirebaseUser user, AuthCredential credential) {
    this.user = user;
    this.credential = credential;
  }

  @Override public void subscribe(CompletableEmitter emitter) {
    user.reauthenticate(credential).addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
