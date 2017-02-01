package org.b3er.rxfirebase.auth;

import com.google.firebase.auth.FirebaseUser;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import org.b3er.rxfirebase.common.GmsTaskListeners;

final class UserUpdateEmailOnSubscribe implements CompletableOnSubscribe {

  private final FirebaseUser user;

  private final String email;

  UserUpdateEmailOnSubscribe(FirebaseUser user, String email) {
    this.user = user;
    this.email = email;
  }

  @Override public void subscribe(CompletableEmitter emitter) {
    user.updateEmail(email).addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
