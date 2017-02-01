package com.github.b3er.rxfirebase.auth;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

final class UserLinkWithCredentialOnSubscribe implements SingleOnSubscribe<AuthResult> {

  private final FirebaseUser user;

  private final AuthCredential credential;

  UserLinkWithCredentialOnSubscribe(FirebaseUser user, AuthCredential credential) {
    this.user = user;
    this.credential = credential;
  }

  @Override public void subscribe(SingleEmitter<AuthResult> emitter) {
    user.linkWithCredential(credential).addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
