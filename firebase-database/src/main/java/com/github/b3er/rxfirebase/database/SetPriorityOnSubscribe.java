package com.github.b3er.rxfirebase.database;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.database.DatabaseReference;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;

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
