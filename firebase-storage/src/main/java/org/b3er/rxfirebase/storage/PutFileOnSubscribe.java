package org.b3er.rxfirebase.storage;

import android.net.Uri;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import org.b3er.rxfirebase.common.GmsTaskListeners;

final class PutFileOnSubscribe implements SingleOnSubscribe<UploadTask.TaskSnapshot> {

  private final StorageReference mRef;

  private final Uri mUri;

  PutFileOnSubscribe(StorageReference ref, Uri uri) {
    mRef = ref;
    mUri = uri;
  }

  @Override public void subscribe(SingleEmitter<UploadTask.TaskSnapshot> e) throws Exception {
    mRef.putFile(mUri).addOnCompleteListener(GmsTaskListeners.listener(e));
  }
}
