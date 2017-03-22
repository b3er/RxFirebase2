package com.github.b3er.rxfirebase.database;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.github.b3er.rxfirebase.database.transformers.ObsTransformerOfClazz;
import com.github.b3er.rxfirebase.database.transformers.ObsTransformerOfGenericTypeIndicator;
import com.github.b3er.rxfirebase.database.transformers.SingleTransformerOfClazz;
import com.github.b3er.rxfirebase.database.transformers.SingleTransformerOfGenericTypeIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.memoizrlabs.retrooptional.Optional;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import java.util.Map;

public final class RxFirebaseDatabase {
  private RxFirebaseDatabase() {
    throw new AssertionError("No instances");
  }

  /**
   * @see DatabaseReference#addChildEventListener(ChildEventListener)
   */
  @NonNull @CheckResult public static Observable<ChildEvent> childEvents(
      @NonNull DatabaseReference ref) {
    return Observable.create(new ChildEventsOnSubscribe(ref));
  }

  /**
   * @see DatabaseReference#addListenerForSingleValueEvent(ValueEventListener)
   */
  @NonNull @CheckResult public static Single<DataSnapshot> data(@NonNull DatabaseReference ref) {
    return Single.create(new DataOnSubscribe(ref));
  }

  /**
   * @see DatabaseReference#addValueEventListener(ValueEventListener)
   */
  @NonNull @CheckResult public static Observable<DataSnapshot> dataChanges(
      @NonNull DatabaseReference ref) {
    return Observable.create(new DataChangesOnSubscribe(ref));
  }

  /**
   * @see DatabaseReference#addValueEventListener(ValueEventListener)
   */
  @NonNull @CheckResult public static <T> Observable<Optional<T>> dataChangesOf(
      @NonNull DatabaseReference ref, @NonNull Class<T> clazz) {
    return dataChanges(ref).compose(new ObsTransformerOfClazz<>(clazz));
  }

  /**
   * @see DatabaseReference#addValueEventListener(ValueEventListener)
   */
  @NonNull @CheckResult public static <T> Observable<Optional<T>> dataChangesOf(
      @NonNull DatabaseReference ref, @NonNull GenericTypeIndicator<T> typeIndicator) {
    return dataChanges(ref).compose(new ObsTransformerOfGenericTypeIndicator<T>(typeIndicator));
  }

  /**
   * @see DatabaseReference#addListenerForSingleValueEvent(ValueEventListener)
   */
  @NonNull @CheckResult public static <T> Single<Optional<T>> dataOf(@NonNull DatabaseReference ref,
      @NonNull Class<T> clazz) {
    return data(ref).compose(new SingleTransformerOfClazz<>(clazz));
  }

  /**
   * @see DatabaseReference#addListenerForSingleValueEvent(ValueEventListener)
   */
  @NonNull @CheckResult public static <T> Single<Optional<T>> dataOf(@NonNull DatabaseReference ref,
      @NonNull GenericTypeIndicator<T> typeIndicator) {
    return data(ref).compose(new SingleTransformerOfGenericTypeIndicator<T>(typeIndicator));
  }

  /**
   * @see DatabaseReference#removeValue()
   */
  @NonNull @CheckResult public static Completable removeValue(@NonNull DatabaseReference ref) {
    return Completable.create(new RemoveValueOnSubscribe(ref));
  }

  /**
   * @see DatabaseReference#setPriority(Object)
   */
  @NonNull @CheckResult public static Completable setPriority(@NonNull DatabaseReference ref,
      @NonNull Object priority) {
    return Completable.create(new SetPriorityOnSubscribe(ref, priority));
  }

  /**
   * @see DatabaseReference#setValue(Object)
   */
  @NonNull @CheckResult public static <T> Completable setValue(@NonNull DatabaseReference ref,
      @Nullable T value) {
    return Completable.create(new SetValueOnSubscribe<T>(ref, value));
  }

  /**
   * @see DatabaseReference#setValue(Object, Object)
   */
  @NonNull @CheckResult public static <T> Completable setValue(@NonNull DatabaseReference ref,
      @Nullable T value, @NonNull Object priority) {
    return Completable.create(new SetValueWithPriorityOnSubscribe<T>(ref, value, priority));
  }

  /**
   * @see DatabaseReference#runTransaction(Transaction.Handler)
   */
  @NonNull @CheckResult public static Completable runTransaction(@NonNull DatabaseReference ref,
      @NonNull Function<MutableData, Transaction.Result> task) {
    return runTransaction(ref, true, task);
  }

  /**
   * @see DatabaseReference#runTransaction(Transaction.Handler, boolean)
   */
  @NonNull @CheckResult public static Completable runTransaction(@NonNull DatabaseReference ref,
      boolean fireLocalEvents, @NonNull Function<MutableData, Transaction.Result> task) {
    return Completable.create(new RunTransactionOnSubscribe(ref, fireLocalEvents, task));
  }

  /**
   * @see DatabaseReference#updateChildren(Map)
   */
  @NonNull @CheckResult public static Completable updateChildren(@NonNull DatabaseReference ref,
      @NonNull Map<String, Object> update) {
    return Completable.create(new UpdateChildrenOnSubscribe(ref, update));
  }
}
