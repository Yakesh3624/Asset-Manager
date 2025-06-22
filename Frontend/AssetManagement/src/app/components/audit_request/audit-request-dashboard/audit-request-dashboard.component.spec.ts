import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuditRequestDashboardComponent } from './audit-request-dashboard.component';

describe('AuditRequestDashboardComponent', () => {
  let component: AuditRequestDashboardComponent;
  let fixture: ComponentFixture<AuditRequestDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuditRequestDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuditRequestDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
