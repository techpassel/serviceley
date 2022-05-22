import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthObservableService {

  constructor() { }

  private authRouteEvent = new BehaviorSubject<boolean>(false);

  emitAuthRouteEvent(val: boolean) {
    this.authRouteEvent.next(val)
  }

  authRouteEventListner() {
    return this.authRouteEvent.asObservable();
  }
}
