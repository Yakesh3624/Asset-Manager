import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssetRequestDashboardComponent } from './asset-request-dashboard.component';

describe('AssetRequestDashboardComponent', () => {
  let component: AssetRequestDashboardComponent;
  let fixture: ComponentFixture<AssetRequestDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AssetRequestDashboardComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(AssetRequestDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
