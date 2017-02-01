package org.b3er.rxfirebase.storage;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import org.b3er.rxfirebase.common.GmsTaskListeners;

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
