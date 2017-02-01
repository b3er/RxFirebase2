package com.github.b3er.rxfirebase.database;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.database.DatabaseReference;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;

final class SetValueWithPriorityOnSubscribe<T> implements CompletableOnSubscribe {

  private final DatabaseReference ref;

  private final T value;

  private final Object priority;

  SetValueWithPriorityOnSubscribe(DatabaseReference ref, T value, Object priority) {
    this.ref = ref;
    this.value = value;
    this.priority = priority;
  }

  @Override public void subscribe(CompletableEmitter emitter) {
    ref.setValue(value, priority).addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
