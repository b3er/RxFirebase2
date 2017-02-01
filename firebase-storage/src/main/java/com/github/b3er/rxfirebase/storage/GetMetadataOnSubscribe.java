package com.github.b3er.rxfirebase.storage;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

final class GetMetadataOnSubscribe implements SingleOnSubscribe<StorageMetadata> {

  private final StorageReference mRef;

  GetMetadataOnSubscribe(StorageReference ref) {
    this.mRef = ref;
  }

  @Override public void subscribe(SingleEmitter<StorageMetadata> e) throws Exception {
    mRef.getMetadata().addOnCompleteListener(GmsTaskListeners.listener(e));
  }
}
