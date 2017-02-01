package com.github.b3er.rxfirebase.auth;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;

final class UserUpdateProfileOnSubscribe implements CompletableOnSubscribe {

  private final FirebaseUser user;

  private final UserProfileChangeRequest request;

  UserUpdateProfileOnSubscribe(FirebaseUser user, UserProfileChangeRequest request) {
    this.user = user;
    this.request = request;
  }

  @Override public void subscribe(final CompletableEmitter emitter) {
    user.updateProfile(request).addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
