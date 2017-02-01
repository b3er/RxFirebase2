package org.b3er.rxfirebase.storage;

import com.google.firebase.storage.StorageReference;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import org.b3er.rxfirebase.common.GmsTaskListeners;

final class DeleteOnSubscribe implements CompletableOnSubscribe {

  private final StorageReference mRef;

  DeleteOnSubscribe(StorageReference reference) {
    this.mRef = reference;
  }

  @Override public void subscribe(CompletableEmitter emitter) throws Exception {
    mRef.delete().addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
