import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAuditRequestComponent } from './view-audit-request.component';

describe('ViewAuditRequestComponent', () => {
  let component: ViewAuditRequestComponent;
  let fixture: ComponentFixture<ViewAuditRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewAuditRequestComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewAuditRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
