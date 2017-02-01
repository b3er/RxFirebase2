package com.github.b3er.rxfirebase.storage;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.storage.StorageReference;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;

final class DeleteOnSubscribe implements CompletableOnSubscribe {

  private final StorageReference mRef;

  DeleteOnSubscribe(StorageReference reference) {
    this.mRef = reference;
  }

  @Override public void subscribe(CompletableEmitter emitter) throws Exception {
    mRef.delete().addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
