package com.github.b3er.rxfirebase.storage;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

final class PutBytesOnSubscribe implements SingleOnSubscribe<UploadTask.TaskSnapshot> {

  private final StorageReference mRef;

  private final byte[] mBytes;

  PutBytesOnSubscribe(StorageReference ref, byte[] bytes) {
    mRef = ref;
    mBytes = bytes;
  }

  @Override public void subscribe(SingleEmitter<UploadTask.TaskSnapshot> e) throws Exception {
    mRef.putBytes(mBytes).addOnCompleteListener(GmsTaskListeners.listener(e));
  }
}
