package com.github.b3er.rxfirebase.storage;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.io.File;
import java.io.InputStream;

public final class RxFirebaseStorage {

  private RxFirebaseStorage() {
    throw new AssertionError("No instances");
  }

  /**
   * @see StorageReference#getBytes(long)
   */
  public static Single<byte[]> getBytes(@NonNull StorageReference ref,
      final long maxDownloadSizeBytes) {
    return Single.create(new GetBytesOnSubscribe(ref, maxDownloadSizeBytes));
  }

  /**
   * @see StorageReference#delete()
   */
  public static Completable delete(@NonNull StorageReference ref) {
    return Completable.create(new DeleteOnSubscribe(ref));
  }

  /**
   * @see StorageReference#getFile(Uri)
   */
  public static Single<FileDownloadTask.TaskSnapshot> getFile(@NonNull StorageReference ref,
      @NonNull Uri uri) {
    return Single.create(new GetFileToUriOnSubscribe(ref, uri));
  }

  /**
   * @see StorageReference#getFile(File)
   */
  public static Single<FileDownloadTask.TaskSnapshot> getFile(@NonNull StorageReference ref,
      @NonNull File file) {
    return Single.create(new GetFileToFileOnSubscribe(ref, file));
  }

  /**
   * @see StorageReference#getMetadata()
   */
  public static Single<StorageMetadata> getMetadata(@NonNull StorageReference ref) {
    return Single.create(new GetMetadataOnSubscribe(ref));
  }

  /**
   * @see StorageReference#getStream()
   */
  public static Single<StreamDownloadTask.TaskSnapshot> getStream(@NonNull StorageReference ref) {
    return Single.create(new GetStreamOnSubscribe(ref));
  }

  /**
   * @see StorageReference#getStream(StreamDownloadTask.StreamProcessor)
   */
  public static Single<StreamDownloadTask.TaskSnapshot> getStream(@NonNull StorageReference ref,
      StreamDownloadTask.StreamProcessor streamProcessor) {
    return Single.create(new GetStreamWithProcessorOnSubscribe(ref, streamProcessor));
  }

  /**
   * @see StorageReference#getDownloadUrl()
   */
  public static Single<Uri> getDownloadUrl(@NonNull StorageReference ref) {
    return Single.create(new GetDownloadUriOnSubscribe(ref));
  }

  /**
   * @see StorageReference#putBytes(byte[])
   */
  public static Single<UploadTask.TaskSnapshot> putBytes(@NonNull StorageReference ref,
      byte[] bytes) {
    return Single.create(new PutBytesOnSubscribe(ref, bytes));
  }

  /**
   * @see StorageReference#putBytes(byte[], StorageMetadata)
   */
  public static Single<UploadTask.TaskSnapshot> putBytes(@NonNull StorageReference ref,
      byte[] bytes, StorageMetadata storageMetadata) {
    return Single.create(new PutBytesWithMetadataOnSubscribe(ref, bytes, storageMetadata));
  }

  /**
   * @see StorageReference#putFile(Uri)
   */
  public static Single<UploadTask.TaskSnapshot> putFile(@NonNull StorageReference ref,
      @NonNull Uri uri) {
    return Single.create(new PutFileOnSubscribe(ref, uri));
  }

  /**
   * @see StorageReference#putFile(Uri, StorageMetadata)
   */
  public static Single<UploadTask.TaskSnapshot> putFile(@NonNull StorageReference ref,
      @NonNull Uri uri, @NonNull StorageMetadata storageMetadata) {
    return Single.create(new PutFileWithMetadataOnSubscribe(ref, uri, storageMetadata));
  }

  /**
   * @see StorageReference#putFile(Uri, StorageMetadata, Uri)
   */
  public static Single<UploadTask.TaskSnapshot> putFile(@NonNull StorageReference ref,
      @NonNull Uri uri, @Nullable StorageMetadata storageMetadata,
      @Nullable Uri existingUploadUri) {
    return Single.create(new PutFileWithMetadataAndExistingUriOnSubscribe(ref, uri, storageMetadata,
        existingUploadUri));
  }

  /**
   * @see StorageReference#putStream(InputStream)
   */
  public static Single<UploadTask.TaskSnapshot> putStream(@NonNull StorageReference ref,
      @NonNull InputStream inputStream) {
    return Single.create(new PutStreamOnSubscribe(ref, inputStream));
  }

  /**
   * @see StorageReference#putStream(InputStream, StorageMetadata)
   */
  public static Single<UploadTask.TaskSnapshot> putStream(@NonNull StorageReference ref,
      @NonNull InputStream inputStream, @NonNull StorageMetadata storageMetadata) {
    return Single.create(new PutStreamWithMetadataOnSubscribe(ref, inputStream, storageMetadata));
  }

  /**
   * @see StorageReference#updateMetadata(StorageMetadata)
   */
  public static Single<StorageMetadata> updateMetadata(@NonNull StorageReference ref,
      @NonNull StorageMetadata storageMetadata) {
    return Single.create(new UpdateMetadataOnSubscribe(ref, storageMetadata));
  }
}
