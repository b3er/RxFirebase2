package com.github.b3er.rxfirebase.database;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.database.DatabaseReference;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;

final class SetValueOnSubscribe<T> implements CompletableOnSubscribe {

  private final DatabaseReference ref;

  private final T value;

  SetValueOnSubscribe(DatabaseReference ref, T value) {
    this.ref = ref;
    this.value = value;
  }

  @Override public void subscribe(CompletableEmitter emitter) {
    ref.setValue(value).addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
