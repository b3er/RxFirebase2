package org.b3er.rxfirebase.storage;

import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import org.b3er.rxfirebase.common.GmsTaskListeners;

final class GetMetadataOnSubscribe implements SingleOnSubscribe<StorageMetadata> {

  private final StorageReference mRef;

  GetMetadataOnSubscribe(StorageReference ref) {
    this.mRef = ref;
  }

  @Override public void subscribe(SingleEmitter<StorageMetadata> e) throws Exception {
    mRef.getMetadata().addOnCompleteListener(GmsTaskListeners.listener(e));
  }
}
