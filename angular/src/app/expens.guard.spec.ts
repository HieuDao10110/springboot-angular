import { TestBed } from '@angular/core/testing';

import { ExpensGuard } from './expens.guard';

describe('ExpensGuard', () => {
  let guard: ExpensGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(ExpensGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
