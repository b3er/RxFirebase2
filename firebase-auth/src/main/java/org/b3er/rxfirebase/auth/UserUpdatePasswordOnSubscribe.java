package org.b3er.rxfirebase.auth;

import com.google.firebase.auth.FirebaseUser;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import org.b3er.rxfirebase.common.GmsTaskListeners;

final class UserUpdatePasswordOnSubscribe implements CompletableOnSubscribe {

  private final FirebaseUser user;

  private final String password;

  UserUpdatePasswordOnSubscribe(FirebaseUser user, String password) {
    this.user = user;
    this.password = password;
  }

  @Override public void subscribe(CompletableEmitter emitter) {
    user.updatePassword(password).addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
