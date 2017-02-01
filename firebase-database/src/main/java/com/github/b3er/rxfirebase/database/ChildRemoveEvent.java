package com.github.b3er.rxfirebase.database;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;

public final class ChildRemoveEvent extends ChildEvent {

  private ChildRemoveEvent(DataSnapshot dataSnapshot) {
    super(dataSnapshot);
  }

  @CheckResult @NonNull public static ChildRemoveEvent create(DataSnapshot dataSnapshot) {
    return new ChildRemoveEvent(dataSnapshot);
  }

  @Override public String toString() {
    return "ChildRemoveEvent{" + "dataSnapshot=" + dataSnapshot + '}';
  }
}
