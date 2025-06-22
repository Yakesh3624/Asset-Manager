import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceRequestDashboardComponent } from './service-request-dashboard.component';

describe('ServiceRequestDashboardComponent', () => {
  let component: ServiceRequestDashboardComponent;
  let fixture: ComponentFixture<ServiceRequestDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ServiceRequestDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiceRequestDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
