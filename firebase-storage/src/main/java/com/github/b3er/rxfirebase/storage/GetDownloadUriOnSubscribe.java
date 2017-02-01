package com.github.b3er.rxfirebase.storage;

import android.net.Uri;
import com.github.b3er.rxfirebase.common.GmsTaskListeners;
import com.google.firebase.storage.StorageReference;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

final class GetDownloadUriOnSubscribe implements SingleOnSubscribe<Uri> {

  private final StorageReference mRef;

  GetDownloadUriOnSubscribe(StorageReference ref) {
    this.mRef = ref;
  }

  @Override public void subscribe(SingleEmitter<Uri> e) throws Exception {
    mRef.getDownloadUrl().addOnCompleteListener(GmsTaskListeners.listener(e));
  }
}
