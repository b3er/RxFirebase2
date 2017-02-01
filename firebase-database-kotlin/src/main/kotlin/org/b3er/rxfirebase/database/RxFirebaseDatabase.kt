@file:Suppress("NOTHING_TO_INLINE")

package org.b3er.rxfirebase.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction.Result
import com.memoizrlabs.retrooptional.Optional
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

inline fun DatabaseReference.childEvents()
    : Observable<ChildEvent>
    = RxFirebaseDatabase.childEvents(this)

inline fun DatabaseReference.data()
    : Single<DataSnapshot>
    = RxFirebaseDatabase.data(this)

inline fun DatabaseReference.dataChanges()
    : Observable<DataSnapshot>
    = RxFirebaseDatabase.dataChanges(this)

inline fun <reified T : Any> DatabaseReference.dataChangesOf()
    : Observable<Optional<T>>
    = RxFirebaseDatabase.dataChangesOf(this, T::class.java)

inline fun <reified T : Any> DatabaseReference.dataChangesOf(typeIndicator: GenericTypeIndicator<T>)
    : Observable<Optional<T>>
    = RxFirebaseDatabase.dataChangesOf(this, typeIndicator)

inline fun <reified T : Any> DatabaseReference.dataOf()
    : Single<Optional<T>>
    = RxFirebaseDatabase.dataOf(this, T::class.java)

inline fun <reified T : Any> DatabaseReference.dataOf(typeIndicator: GenericTypeIndicator<T>)
    : Single<Optional<T>>
    = RxFirebaseDatabase.dataOf(this, typeIndicator)

inline fun DatabaseReference.rxSetPriority(priority: Any)
    : Completable
    = RxFirebaseDatabase.setPriority(this, priority)

inline fun <reified T : Any> DatabaseReference.rxSetValue(value: T)
    : Completable
    = RxFirebaseDatabase.setValue(this, value)

inline fun <reified T : Any> DatabaseReference.rxSetValue(value: T, priority: Any)
    : Completable
    = RxFirebaseDatabase.setValue(this, value, priority)

inline fun DatabaseReference.rxChildEvents()
    : Observable<ChildEvent>
    = RxFirebaseDatabase.childEvents(this)

inline fun DatabaseReference.rxRunTransaction(noinline task: (MutableData) -> Result)
    : Completable
    = RxFirebaseDatabase.runTransaction(this, task)

inline fun DatabaseReference.rxRunTransaction(
    fireLocalEvents: Boolean, noinline task: (MutableData) -> Result)
    : Completable
    = RxFirebaseDatabase.runTransaction(this, fireLocalEvents, task)

inline fun DatabaseReference.rxUpdateChildren(update: Map<String, Any?>)
    : Completable
    = RxFirebaseDatabase.updateChildren(this, update)