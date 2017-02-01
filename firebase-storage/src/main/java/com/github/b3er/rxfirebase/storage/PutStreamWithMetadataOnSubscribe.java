package com.github.b3er.rxfirebase.storage;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.io.InputStream;

final class PutStreamWithMetadataOnSubscribe implements SingleOnSubscribe<UploadTask.TaskSnapshot> {

  private final StorageReference mRef;

  private final InputStream mInputStream;

  private final StorageMetadata mStorageMetadata;

  PutStreamWithMetadataOnSubscribe(StorageReference ref, InputStream inputStream,
      StorageMetadata storageMetadata) {
    mRef = ref;
    mInputStream = inputStream;
    mStorageMetadata = storageMetadata;
  }

  @Override public void subscribe(SingleEmitter<UploadTask.TaskSnapshot> e) throws Exception {
    mRef.putStream(mInputStream, mStorageMetadata)
        .addOnCompleteListener(GmsTaskListeners.listener(e));
  }
}
