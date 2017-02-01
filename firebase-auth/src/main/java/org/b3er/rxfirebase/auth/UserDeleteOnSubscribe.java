package org.b3er.rxfirebase.auth;

import com.google.firebase.auth.FirebaseUser;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import org.b3er.rxfirebase.common.GmsTaskListeners;

final class UserDeleteOnSubscribe implements CompletableOnSubscribe {

  private final FirebaseUser user;

  UserDeleteOnSubscribe(FirebaseUser user) {
    this.user = user;
  }

  @Override public void subscribe(CompletableEmitter emitter) {
    user.delete().addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
