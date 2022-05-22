import { TestBed } from '@angular/core/testing';

import { AuthObservableService } from './auth-observable.service';

describe('AuthObservableService', () => {
  let service: AuthObservableService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthObservableService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
