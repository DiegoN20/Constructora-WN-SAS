import { TestBed } from '@angular/core/testing';

import { AvancesService } from './avances.service';

describe('AvancesService', () => {
  let service: AvancesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AvancesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
