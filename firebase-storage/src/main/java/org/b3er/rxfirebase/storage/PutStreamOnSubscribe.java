package org.b3er.rxfirebase.storage;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.io.InputStream;
import org.b3er.rxfirebase.common.GmsTaskListeners;

final class PutStreamOnSubscribe implements SingleOnSubscribe<UploadTask.TaskSnapshot> {

  private final StorageReference mRef;

  private final InputStream mInputStream;

  PutStreamOnSubscribe(StorageReference ref, InputStream inputStream) {

    mRef = ref;
    mInputStream = inputStream;
  }

  @Override public void subscribe(SingleEmitter<UploadTask.TaskSnapshot> e) throws Exception {
    mRef.putStream(mInputStream).addOnCompleteListener(GmsTaskListeners.listener(e));
  }
}
