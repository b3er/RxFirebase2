package com.github.b3er.rxfirebase.storage;

import android.net.Uri;
import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

final class GetFileToUriOnSubscribe implements SingleOnSubscribe<FileDownloadTask.TaskSnapshot> {

  private final StorageReference reference;

  private final Uri uri;

  GetFileToUriOnSubscribe(StorageReference ref, Uri uri) {
    this.reference = ref;
    this.uri = uri;
  }

  @Override public void subscribe(SingleEmitter<FileDownloadTask.TaskSnapshot> e) throws Exception {
    reference.getFile(uri).addOnCompleteListener(GmsTaskListeners.listener(e));
  }
}
