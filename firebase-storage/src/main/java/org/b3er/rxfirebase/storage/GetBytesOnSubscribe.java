package org.b3er.rxfirebase.storage;

import com.google.firebase.storage.StorageReference;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import org.b3er.rxfirebase.common.GmsTaskListeners;

final class GetBytesOnSubscribe implements SingleOnSubscribe<byte[]> {

  private final StorageReference mRef;

  private final long mMaxDownloadSizeBytes;

  GetBytesOnSubscribe(StorageReference ref, long maxDownloadSizeBytes) {
    this.mRef = ref;
    this.mMaxDownloadSizeBytes = maxDownloadSizeBytes;
  }

  @Override public void subscribe(final SingleEmitter<byte[]> emitter) throws Exception {
    mRef.getBytes(mMaxDownloadSizeBytes).addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
