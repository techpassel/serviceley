import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommonService {
  //isAuthRoute: Subject<boolean> = new Subject<boolean>();
  private authRouteEvent = new BehaviorSubject<boolean>(false);

  emitAuthRouteEvent(val: boolean) {
    this.authRouteEvent.next(val)
  }

  authRouteEventListner() {
    return this.authRouteEvent.asObservable();
  }

  constructor() { }
}
