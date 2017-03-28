package com.github.b3er.rxfirebase.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;

final class GetCurrentUserOnSubscribe implements MaybeOnSubscribe<FirebaseUser> {

  private FirebaseAuth instance;

  GetCurrentUserOnSubscribe(FirebaseAuth instance) {
    this.instance = instance;
  }

  @Override public void subscribe(MaybeEmitter<FirebaseUser> emitter) {
    if (!emitter.isDisposed()) {
      FirebaseUser currentUser = instance.getCurrentUser();
      if (currentUser != null) {
        emitter.onSuccess(currentUser);
      } else {
        emitter.onComplete();
      }
    }
  }
}
