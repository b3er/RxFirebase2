package org.b3er.rxfirebase.storage;

import android.net.Uri;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import org.b3er.rxfirebase.common.GmsTaskListeners;

final class PutFileWithMetadataAndExistingUriOnSubscribe
    implements SingleOnSubscribe<UploadTask.TaskSnapshot> {

  private final StorageReference mRef;

  private final Uri mUri;

  private final Uri mExistingUploadUri;

  private StorageMetadata mStorageMetadata;

  PutFileWithMetadataAndExistingUriOnSubscribe(StorageReference ref, Uri uri,
      StorageMetadata storageMetadata, Uri existingUploadUri) {
    mRef = ref;
    mUri = uri;
    mStorageMetadata = storageMetadata;
    mExistingUploadUri = existingUploadUri;
  }

  @Override public void subscribe(SingleEmitter<UploadTask.TaskSnapshot> e) throws Exception {
    mRef.putFile(mUri, mStorageMetadata, mExistingUploadUri)
        .addOnCompleteListener(GmsTaskListeners.listener(e));
  }
}