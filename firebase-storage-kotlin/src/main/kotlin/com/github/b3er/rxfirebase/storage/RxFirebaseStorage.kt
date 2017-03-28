@file:Suppress("NOTHING_TO_INLINE")

package com.github.b3er.rxfirebase.storage

import android.net.Uri
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StreamDownloadTask
import com.google.firebase.storage.StreamDownloadTask.StreamProcessor
import com.google.firebase.storage.UploadTask
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import java.io.InputStream

inline fun StorageReference.rxGetBytes(maxDownloadSizeBytes: Long)
    : Single<ByteArray>
    = RxFirebaseStorage.getBytes(this, maxDownloadSizeBytes)

inline fun StorageReference.rxDelete()
    : Completable
    = RxFirebaseStorage.delete(this)

inline fun StorageReference.rxGetFile(uri: Uri)
    : Single<FileDownloadTask.TaskSnapshot>
    = RxFirebaseStorage.getFile(this, uri)

inline fun StorageReference.rxGetFile(file: File)
    : Single<FileDownloadTask.TaskSnapshot>
    = RxFirebaseStorage.getFile(this, file)

inline fun StorageReference.rxGetMetadata()
    : Single<StorageMetadata>
    = RxFirebaseStorage.getMetadata(this)

inline fun StorageReference.rxGetStream()
    : Single<StreamDownloadTask.TaskSnapshot>
    = RxFirebaseStorage.getStream(this)

inline fun StorageReference.rxGetStream(streamProcessor: StreamProcessor)
    : Single<StreamDownloadTask.TaskSnapshot>
    = RxFirebaseStorage.getStream(this, streamProcessor)

inline fun StorageReference.rxGetDownloadUrl()
    : Single<Uri>
    = RxFirebaseStorage.getDownloadUrl(this)

inline fun StorageReference.rxPutBytes(bytes: ByteArray)
    : Single<UploadTask.TaskSnapshot>
    = RxFirebaseStorage.putBytes(this, bytes)

inline fun StorageReference.rxPutBytes(bytes: ByteArray, storageMetadata: StorageMetadata)
    : Single<UploadTask.TaskSnapshot>
    = RxFirebaseStorage.putBytes(this, bytes, storageMetadata)

inline fun StorageReference.rxPutFile(uri: Uri)
    : Single<UploadTask.TaskSnapshot>
    = RxFirebaseStorage.putFile(this, uri)

inline fun StorageReference.rxPutFile(uri: Uri, storageMetadata: StorageMetadata)
    : Single<UploadTask.TaskSnapshot>
    = RxFirebaseStorage.putFile(this, uri, storageMetadata)

inline fun StorageReference.rxPutFile(uri: Uri, storageMetadata: StorageMetadata,
    existingUploadUri: Uri)
    : Single<UploadTask.TaskSnapshot>
    = RxFirebaseStorage.putFile(this, uri, storageMetadata, existingUploadUri)

inline fun StorageReference.rxPutStream(inputStream: InputStream)
    : Single<UploadTask.TaskSnapshot>
    = RxFirebaseStorage.putStream(this, inputStream)

inline fun StorageReference.rxPutStream(inputStream: InputStream, storageMetadata: StorageMetadata)
    : Single<UploadTask.TaskSnapshot>
    = RxFirebaseStorage.putStream(this, inputStream, storageMetadata)

inline fun StorageReference.rxUpdateMetadata(storageMetadata: StorageMetadata)
    : Single<StorageMetadata>
    = RxFirebaseStorage.updateMetadata(this, storageMetadata)