package org.b3er.rxfirebase.database;

import com.google.firebase.database.DatabaseReference;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import org.b3er.rxfirebase.common.GmsTaskListeners;

final class RemoveValueOnSubscribe implements CompletableOnSubscribe {

  private final DatabaseReference ref;

  RemoveValueOnSubscribe(DatabaseReference ref) {
    this.ref = ref;
  }

  @Override public void subscribe(final CompletableEmitter emitter) {
    ref.removeValue().addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
