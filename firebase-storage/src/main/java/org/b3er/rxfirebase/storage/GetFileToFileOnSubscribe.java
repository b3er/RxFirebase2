package org.b3er.rxfirebase.storage;

import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import java.io.File;
import org.b3er.rxfirebase.common.GmsTaskListeners;

final class GetFileToFileOnSubscribe implements SingleOnSubscribe<FileDownloadTask.TaskSnapshot> {

  private final StorageReference mRef;

  private final File mFile;

  GetFileToFileOnSubscribe(StorageReference ref, File file) {
    this.mRef = ref;
    this.mFile = file;
  }

  @Override public void subscribe(SingleEmitter<FileDownloadTask.TaskSnapshot> e) throws Exception {
    mRef.getFile(mFile).addOnCompleteListener(GmsTaskListeners.listener(e));
  }
}
