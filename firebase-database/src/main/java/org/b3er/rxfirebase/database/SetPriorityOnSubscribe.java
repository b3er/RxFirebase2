package org.b3er.rxfirebase.database;

import com.google.firebase.database.DatabaseReference;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import org.b3er.rxfirebase.common.GmsTaskListeners;

final class SetPriorityOnSubscribe implements CompletableOnSubscribe {

  private final DatabaseReference ref;

  private final Object priority;

  SetPriorityOnSubscribe(DatabaseReference ref, Object priority) {
    this.ref = ref;
    this.priority = priority;
  }

  @Override public void subscribe(final CompletableEmitter emitter) {
    ref.setPriority(priority).addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
