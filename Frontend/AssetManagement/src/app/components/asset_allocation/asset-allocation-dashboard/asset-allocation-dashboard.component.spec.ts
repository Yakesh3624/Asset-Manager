import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssetAllocationDashboardComponent } from './asset-allocation-dashboard.component';

describe('AssetAllocationDashboardComponent', () => {
  let component: AssetAllocationDashboardComponent;
  let fixture: ComponentFixture<AssetAllocationDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssetAllocationDashboardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AssetAllocationDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
