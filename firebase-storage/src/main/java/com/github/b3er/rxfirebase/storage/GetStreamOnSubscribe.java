package com.github.b3er.rxfirebase.storage;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

final class GetStreamOnSubscribe implements SingleOnSubscribe<StreamDownloadTask.TaskSnapshot> {

  private final StorageReference mRef;

  GetStreamOnSubscribe(StorageReference ref) {
    this.mRef = ref;
  }

  @Override public void subscribe(SingleEmitter<StreamDownloadTask.TaskSnapshot> e)
      throws Exception {
    mRef.getStream().addOnCompleteListener(GmsTaskListeners.listener(e));
  }
}
