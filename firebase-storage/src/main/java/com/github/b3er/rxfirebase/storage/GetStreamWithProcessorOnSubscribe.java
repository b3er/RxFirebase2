package com.github.b3er.rxfirebase.storage;

import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

final class GetStreamWithProcessorOnSubscribe
    implements SingleOnSubscribe<StreamDownloadTask.TaskSnapshot> {

  private final StorageReference mRef;

  private final StreamDownloadTask.StreamProcessor mStreamProcessor;

  GetStreamWithProcessorOnSubscribe(StorageReference ref,
      StreamDownloadTask.StreamProcessor streamProcessor) {
    this.mRef = ref;
    this.mStreamProcessor = streamProcessor;
  }

  @Override public void subscribe(SingleEmitter<StreamDownloadTask.TaskSnapshot> e)
      throws Exception {
    mRef.getStream(mStreamProcessor).addOnCompleteListener(GmsTaskListeners.listener(e));
  }
}
