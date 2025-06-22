import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeViewAssetComponent } from './employee-view-asset.component';

describe('EmployeeViewAssetComponent', () => {
  let component: EmployeeViewAssetComponent;
  let fixture: ComponentFixture<EmployeeViewAssetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EmployeeViewAssetComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(EmployeeViewAssetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
