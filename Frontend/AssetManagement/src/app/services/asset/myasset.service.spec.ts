import { TestBed } from '@angular/core/testing';

import { MyassetService } from './myasset.service';

describe('MyassetService', () => {
  let service: MyassetService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MyassetService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
