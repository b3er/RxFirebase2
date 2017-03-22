package com.github.b3er.rxfirebase.storage;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

final class UpdateMetadataOnSubscribe implements SingleOnSubscribe<StorageMetadata> {

  private final StorageReference mRef;

  private final StorageMetadata mMetadata;

  UpdateMetadataOnSubscribe(StorageReference ref, StorageMetadata metadata) {
    mRef = ref;
    mMetadata = metadata;
  }

  @Override public void subscribe(SingleEmitter<StorageMetadata> e) throws Exception {
    mRef.updateMetadata(mMetadata).addOnCompleteListener(GmsTaskListeners.listener(e));
  }
}
