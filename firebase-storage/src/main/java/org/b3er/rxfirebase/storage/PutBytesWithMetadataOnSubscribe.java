package org.b3er.rxfirebase.storage;

import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import org.b3er.rxfirebase.common.GmsTaskListeners;

final class PutBytesWithMetadataOnSubscribe implements SingleOnSubscribe<UploadTask.TaskSnapshot> {

  private final StorageReference mRef;

  private final StorageMetadata mStorageMetadata;

  private byte[] mBytes;

  PutBytesWithMetadataOnSubscribe(StorageReference ref, byte[] bytes,
      StorageMetadata storageMetadata) {
    mRef = ref;
    mBytes = bytes;
    mStorageMetadata = storageMetadata;
  }

  @Override public void subscribe(SingleEmitter<UploadTask.TaskSnapshot> e) throws Exception {
    mRef.putBytes(mBytes, mStorageMetadata).addOnCompleteListener(GmsTaskListeners.listener(e));
  }
}
