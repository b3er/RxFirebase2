package org.b3er.rxfirebase.database;

import com.google.firebase.database.DatabaseReference;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import java.util.Map;
import org.b3er.rxfirebase.common.GmsTaskListeners;

final class UpdateChildrenOnSubscribe implements CompletableOnSubscribe {

  private final DatabaseReference ref;

  private final Map<String, Object> update;

  UpdateChildrenOnSubscribe(DatabaseReference ref, Map<String, Object> update) {
    this.ref = ref;
    this.update = update;
  }

  @Override public void subscribe(CompletableEmitter emitter) {
    ref.updateChildren(update).addOnCompleteListener(GmsTaskListeners.listener(emitter));
  }
}
