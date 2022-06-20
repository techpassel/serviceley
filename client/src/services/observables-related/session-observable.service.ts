import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import SessionUtil from 'src/utils/session.util';

@Injectable({
  providedIn: 'root'
})
export class SessionObservableService {
  isSession: boolean = this.sessionUtil.sessionExist();
  constructor(private sessionUtil: SessionUtil) { }

  private sessionEvent = new BehaviorSubject<boolean>(this.isSession);

  emitSessionEvent(val: boolean) {
    this.sessionEvent.next(val)
  }

  sessionEventListener() {
    return this.sessionEvent.asObservable();
  }
}
